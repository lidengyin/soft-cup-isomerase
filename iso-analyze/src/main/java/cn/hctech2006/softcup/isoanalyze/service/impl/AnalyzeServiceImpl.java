package cn.hctech2006.softcup.isoanalyze.service.impl;

import cn.hctech2006.softcup.isoanalyze.bean.NlAnalyze;
import cn.hctech2006.softcup.isoanalyze.bean.NlAnalyzeSmall;
import cn.hctech2006.softcup.isoanalyze.common.ServerResponse;
import cn.hctech2006.softcup.isoanalyze.dto.AeToBigDataDTO;
import cn.hctech2006.softcup.isoanalyze.dto.AnalyzeDTO;
import cn.hctech2006.softcup.isoanalyze.dto.AnalyzeSmallDTO;
import cn.hctech2006.softcup.isoanalyze.mapper.NlAnalyzeMapper;
import cn.hctech2006.softcup.isoanalyze.mapper.NlAnalyzeSmallMapper;
import cn.hctech2006.softcup.isoanalyze.server.BgToBigDataSocketServer;
import cn.hctech2006.softcup.isoanalyze.server.BgToFgSocketServer;
import cn.hctech2006.softcup.isoanalyze.service.DataQueryService;
import cn.hctech2006.softcup.isoanalyze.service.ModelService;
import cn.hctech2006.softcup.isoanalyze.util.ExcelToCsv;
import cn.hctech2006.softcup.isoanalyze.util.OBSUtils;
import cn.hctech2006.softcup.isoanalyze.vo.*;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;


import javax.mail.MessagingException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class AnalyzeServiceImpl{
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Logger logger = LoggerFactory.getLogger(AnalyzeServiceImpl.class);
    @Autowired
    private DataQueryService queryService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private NlAnalyzeMapper analyzeMapper;
    @Autowired
    private NlAnalyzeSmallMapper analyzeSmallMapper;
    @Autowired
    private MyRJavaSerivce myRJavaSerivce;
    @Autowired
    private MailServiceImpl mailService;
    @Transactional(propagation = Propagation.REQUIRED)
    public ServerResponse upload(AnalyzeDTO analyzeDTO) throws Exception {
        String mlId = analyzeDTO.getMlId();
        String quId = analyzeDTO.getQuId();
        ServerResponse response = modelService.getMlNameAndMlAlgo(mlId);
        Map<String, String> ModelMap = (Map<String, String>) response.getData();
        response = queryService.getQueryName(quId);
        Map<String, String> qyMap = (Map<String, String>) response.getData();
        String quName = qyMap.get("quName");
        String mlName = ModelMap.get("mlName");
        String mlAlgo = ModelMap.get("mlAlgo");
        Map<String,String> result = getAnalyzeUrl(analyzeDTO);
        String localUrlXls = result.get("urlXls");
        String localUrlCsv = result.get("urlCsv");
        String aeUrl = result.get("aeUrl");
        String aeId = UUID.randomUUID().toString();

       if (mlAlgo.equalsIgnoreCase("python")){
           Map<String, Object> putMap = new HashMap<>();
           putMap.put("flag", "1");
           putMap.put("aeUrl", aeUrl);
           putMap.put("mlName", mlName);
           putMap.put("aeId", aeId);
           String str = JSONObject.toJSONString(putMap);
           System.out.println(str);
           BgToBigDataSocketServer.sendAll(putMap);
           return uploadAnalyze(analyzeDTO, aeId, mlName, quName, aeUrl);
       }else {
           Map<String, String> result1 = myRJavaSerivce.rAlarm(localUrlCsv, localUrlXls);
           ServerResponse response1 = uploadAnalyze(analyzeDTO, aeId, mlName, quName, aeUrl);
           if(!response1.isSuccess()) return response;
           ServerResponse response2 = updateByBigData(aeId, result1.get("aeResult"),result1.get("alarmFlag"));
           if (!response2.isSuccess()) {
               System.out.println("失败: "+response2.getMsg());
               return ServerResponse.createByError();
           }else{
               System.out.println("成功");
               return response1;
           }
       }
       // return uploadAnalyze(analyzeDTO, aeId, mlName, quName, aeUrl);
    }

    /**
     * 大数据传回数据进行更新
     * @param aeId
     * @param aeResultm
     * @param alarmFlag
     * @return
     * @throws MessagingException
     */
    public ServerResponse updateByBigData(String aeId, String aeResultm, String alarmFlag ) throws MessagingException {
        if (StringUtils.isEmpty(aeId)) return ServerResponse.createByError("参数不全");
        NlAnalyze param = analyzeMapper.selectByAeId(aeId);
        if (param == null) return ServerResponse.createByError("数据分析实例不存在");
        NlAnalyze analyze = new NlAnalyze();
        analyze.setAeId(aeId);
        analyze.setAeResult(aeResultm);
        analyze.setAlramFlag(alarmFlag);
        int result = analyzeMapper.updateByAeId(analyze);
        //if (result < 0) return ServerResponse.createByError("修改失败");
        String alramFlag = analyze.getAlramFlag();
        String aeResult =analyze.getAeResult();
        AnalyzeEmailVO analyzeEmailVO = new AnalyzeEmailVO();
        analyzeEmailVO.setAeName(analyze.getAeName());
        analyzeEmailVO.setAeResult(aeResultm);
        Context context = new Context();
        Map<String, Object> resultMap = new HashMap<>();
        if (alramFlag.equalsIgnoreCase("0")){
            resultMap.put("alarmFlag", "0");
            resultMap.put("aeResult", aeResult);
            analyzeEmailVO.setAlramFlag("运行正常");
        }else if (alramFlag.equalsIgnoreCase("1")){
            resultMap.put("alarmFlag", "1");
            resultMap.put("aeResult", aeResult);
            analyzeEmailVO.setAlramFlag("系统报警");

            param.setAeResult(aeResult);
            param.setAlramFlag(alramFlag);
            context.setVariable("analyze", analyzeEmailVO);

        }
        mailService.sendSimpleMail("haichuang04014@163.com", "haichuang04012@163.com", "haichuang04014@163.com","邮件预警", "邮件报警");
        System.out.println("即将发送ws");
        BgToFgSocketServer.sendAll(resultMap);
        System.out.println("分析实例修改成功");
        return ServerResponse.createBySuccess("修改成功");
    }


    public ServerResponse list(int pageNum, int pageSize, String showFlag){
        PageHelper.startPage(pageNum, pageSize);
        List<NlAnalyze> analyzes = analyzeMapper.selectByShowFlag(showFlag);
        PageInfo pageInfo = new PageInfo(analyzes);
        List<AnalyzeListVO> analyzeListVOS = new ArrayList<>();
        for (NlAnalyze analyze : analyzes){
            String mlId = analyze.getMlId();
            String quId = analyze.getQuId();
            ServerResponse response = modelService.getMlNameAndMlAlgo(mlId);
            Map<String, String> modelMap = (Map<String, String>) response.getData();
            ServerResponse qyResponse = queryService.getQueryName(quId);
            Map<String, String> qyMap = (Map<String, String>) qyResponse.getData();
            String quName = "";
            String mlName = "";
            if ( qyMap.containsKey("quName")) {
                quName = qyMap.get("quName");
            }
            if (response.isSuccess()) {
                mlName = modelMap.get("mlName");
            }

            AnalyzeListVO analyzeListVO = analyzePOJOTOAnalyzeListVO(analyze, mlName,quName, quId, mlId);
            analyzeListVOS.add(analyzeListVO);
        }
        pageInfo.setList(analyzeListVOS);
        AnalyzePageInfoVO analyzePageInfoVO = pageInfoToAnalyzePageInfoVO(pageInfo);
        return ServerResponse.createBySuccess(analyzePageInfoVO);
    }

    private  QueryDetailsVO objectToQueryDetailsVOSerilizable(ServerResponse response){
        ObjectMapper mapper=new ObjectMapper();
        QueryDetailsVO field = mapper.convertValue(response.getData(), QueryDetailsVO.class);
        return field;
    }
    /**
     * 分析实例上传
     * @param analyzeDTO
     * @return
     */

    private ServerResponse uploadAnalyze(AnalyzeDTO analyzeDTO, String aeId, String mlName, String quName, String aeUrl){
        NlAnalyze analyze = new NlAnalyze();
        List<NlAnalyzeSmall> analyzeSmalls = new ArrayList<>();
        List<AnalyzeSmallDTO> analyzeSmallDTOS  =analyzeDTO.getAnalyzeSmallDTOS();
        for (AnalyzeSmallDTO analyzeSmallDTO : analyzeSmallDTOS){
            NlAnalyzeSmall analyzeSmall = new NlAnalyzeSmall();
            analyzeSmall.setAeId(aeId);
            analyzeSmall.setAsId(UUID.randomUUID().toString());
            analyzeSmall.setMsName(analyzeSmallDTO.getMsName());
            analyzeSmall.setQsField(analyzeSmallDTO.getQsField());
            analyzeSmalls.add(analyzeSmall);

            int result = analyzeSmallMapper.insert(analyzeSmall);
            if (result < 0) return ServerResponse.createByError("存储失败");
        }
        analyze.setAeId(aeId);
        analyze.setAeName(analyzeDTO.getAeName());
        analyze.setAeTime(new Date());
        analyze.setAlramFlag("0");
        analyze.setShowFlag("1");
        analyze.setQuId(analyzeDTO.getQuId());
        analyze.setMlId(analyzeDTO.getMlId());
        analyze.setAeUrl(aeUrl);
        analyze.setAeResult("未测试");
        int result = analyzeMapper.insert(analyze);
        AnalyzeDetailsVO analyzeDetailsVO = analyzePOJOTOAnalyzedetailsVO(analyze, analyzeSmalls, mlName, quName);
        if (result > 0) return ServerResponse.createBySuccess(analyzeDetailsVO);
        return ServerResponse.createByError("上传失败");
    }
    private AnalyzePageInfoVO pageInfoToAnalyzePageInfoVO(PageInfo pageInfo){
        AnalyzePageInfoVO analyzePageInfoVO = new AnalyzePageInfoVO();
        List<AnalyzeListVO> analyzeListVOS = pageInfo.getList();
        analyzePageInfoVO.setAnalyzeListVOS(pageInfo.getList());
        analyzePageInfoVO.setPageNum(pageInfo.getPageNum());
        analyzePageInfoVO.setPageSize(pageInfo.getPageSize());
        analyzePageInfoVO.setPages(pageInfo.getPages());
        analyzePageInfoVO.setTotal(pageInfo.getTotal());
        analyzePageInfoVO.setNavigateFirstPage(pageInfo.getNavigateFirstPage());
        analyzePageInfoVO.setNavigateLastPage(pageInfo.getNavigateLastPage());
        analyzePageInfoVO.setNavigatepageNums(pageInfo.getNavigatepageNums());
        return analyzePageInfoVO;
    }
    /**
     * POJO转显示层实例对象
     * @param analyze
     * @param analyzeSmalls
     * @param mlName
     * @param quName
     * @return
     */
    private AnalyzeDetailsVO analyzePOJOTOAnalyzedetailsVO(NlAnalyze analyze, List<NlAnalyzeSmall> analyzeSmalls, String mlName, String quName){
        AnalyzeDetailsVO analyzeDetailsVO = new AnalyzeDetailsVO();
        List<AnalyzeSmallVO> analyzeSmallVOS = new ArrayList<>();
        for (NlAnalyzeSmall analyzeSmall : analyzeSmalls){
            AnalyzeSmallVO analyzeSmallVO = analyzeSmallPOJOTOAnalyzeSmallVO(analyzeSmall);
            analyzeSmallVOS.add(analyzeSmallVO);
        }
        analyzeDetailsVO.setAeName(analyze.getAeName());
        analyzeDetailsVO.setAeResult(analyze.getAeResult());
        analyzeDetailsVO.setAeTime(sdf.format(analyze.getAeTime()));
        analyzeDetailsVO.setAlramFlag(analyze.getAlramFlag());
        analyzeDetailsVO.setAnalyzeSmallVOS(analyzeSmallVOS);
        analyzeDetailsVO.setMlName(mlName);
        analyzeDetailsVO.setQuName(quName);
        return analyzeDetailsVO;
    }

    /**
     * POJO对象转换为分析实例列表
     * @param analyze
     * @param mlName
     * @param quName
     * @return
     */

    private AnalyzeListVO analyzePOJOTOAnalyzeListVO(NlAnalyze analyze,  String mlName, String quName, String quId, String mlId){
        AnalyzeListVO analyzeListVO = new AnalyzeListVO();
        analyzeListVO.setAeName(analyze.getAeName());
        analyzeListVO.setAeResult(analyze.getAeResult());
        analyzeListVO.setAeTime(sdf.format(analyze.getAeTime()));
        analyzeListVO.setAlramFlag(analyze.getAlramFlag());
        analyzeListVO.setMlName(mlName);
        analyzeListVO.setQuName(quName);
        analyzeListVO.setAeId(analyze.getAeId());
        analyzeListVO.setQuId(quId);
        analyzeListVO.setMlId(mlId);
        return analyzeListVO;
    }
    private AnalyzeSmallVO analyzeSmallPOJOTOAnalyzeSmallVO(NlAnalyzeSmall analyzeSmall){
        AnalyzeSmallVO analyzeSmallVO = new AnalyzeSmallVO();
        analyzeSmallVO.setMsName(analyzeSmall.getMsName());
        analyzeSmallVO.setQsField(analyzeSmall.getQsField());
        return analyzeSmallVO;
    }
    /**
     * 传递给大数据的数据分析模型
     * @param aeUrl
     * @param mlName
     * @param aeId
     * @return
     */
    private AeToBigDataDTO analyzeToBigDataDTO(String aeUrl, String mlName,String aeId) {

        AeToBigDataDTO aeToBigDataDTO = new AeToBigDataDTO();
        aeToBigDataDTO.setAeUrl(aeUrl);
        aeToBigDataDTO.setMlName(mlName);
        aeToBigDataDTO.setAeId(aeId);
        return aeToBigDataDTO;
    }
    private Map<String, String> getAnalyzeUrl(AnalyzeDTO analyzeDTO) throws Exception {
        List<AnalyzeSmallDTO> analyzeSmallDTOS  =analyzeDTO.getAnalyzeSmallDTOS();
        Map<String,String> aeKeyAndquValue = new HashMap<>();
        for (AnalyzeSmallDTO analyzeSmallDTO : analyzeSmallDTOS){
            aeKeyAndquValue.put(analyzeSmallDTO.getMsName(), analyzeSmallDTO.getQsField());

        }
        Map<String, String> result = (Map<String, String>) redisExcelExport(analyzeDTO.getQuId(), aeKeyAndquValue).getData();
        return result;
    }
    /**
     * 得到查询实例数据集的在线excel地址
     * @param queryId
     * @return
     * @throws Exception
     */
    public ServerResponse redisExcelExport(
            String queryId, Map<String, String> aeKeyAndquValue) throws Exception {

        ServerResponse serverResponse = queryService.queryOneToFg(queryId);
        if (!serverResponse.isSuccess()) return ServerResponse.createByError("获取失败");
        QueryDetailsVO queryDetailsVO = objectToQueryDetailsVOSerilizable(serverResponse);
        Map<String, QuerySmallVO> querySmallVOMap = queryDetailsVO.getQsVOMap();

        //开始模拟获取数据，实际应该在数据库查出来
        //EXCEL表导出核心代码
        //声明一个Excel
        HSSFWorkbook wb =null;
        //title代表的是你的excel表开头每列的名字
//        String[] title =new String[];
        //新设置开头字段的姓名
        //List<NlQuerySmall> querySmalls = querySmallMapper.selectByQuId(queryId);
        List<String> fieldNames  = new ArrayList<>();
        for (Map.Entry<String, String> term : aeKeyAndquValue.entrySet()){
            fieldNames.add(term.getKey());
        }

        String[] title =new String[fieldNames.size()];
        fieldNames.toArray(title);
        String name="sheet";
        //excel文件名
        String fileName = queryId+".xlsx";
        String fileNameExcel = queryId+".xlsx";
        //sheet名
        String sheetName = name;
        //二维数组铺满整个Excel
        int rowLength = 0;
        for (Map.Entry<String, QuerySmallVO> term : querySmallVOMap.entrySet()){
            QuerySmallVO querySmallVO = term.getValue();
            String[] fieldParamStr = querySmallVO.getFieldValues();
            logger.info("行数: "+fieldParamStr.length);
            if (fieldParamStr.length <= rowLength || rowLength == 0) rowLength=fieldParamStr.length;
        }

        int num = rowLength % 50000;
        int num1 = 0;
        if (num == 0){
            num1 = rowLength / 50000;
        }else{
            num1=rowLength / 50000 + 1;
        }
        logger.info("行数: "+rowLength+", 表单数: "+num1+", 列数: "+title.length);
        int k = 0;
        for (int w = 0; w < num1; w ++){
            String[][] content = new String[50000][title.length];
            //--------------------------------------------------------------------------------------------
            // 第一步，创建一个HSSFWorkbook，对应一个Excel文件

            if(wb == null){
                wb = new HSSFWorkbook();
            }

            // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(sheetName+w);

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            HSSFRow row = sheet.createRow(0);

            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();

            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            //设置居右
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居右
            style.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
            //设置字体
            HSSFFont font=wb.createFont();
            HSSFFont font2=wb.createFont();
            font2.setFontName("仿宋_GB2312");
            //font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
            font2.setFontHeightInPoints((short) 12);

            style.setFont(font);//选择需要用到的字体格式

            //设置列宽
            sheet.setColumnWidth(0, 1500);//第一个参数代表列id（从0开始），第二个参数代表宽度值
            sheet.setColumnWidth(1, 1500);//第一个参数代表列id（从1开始），第二个参数代表宽度值
            sheet.setColumnWidth(2, 1500);//第一个参数代表列id（从2开始），第二个参数代表宽度值
            sheet.setColumnWidth(3, 1500);//第一个参数代表列id（从3开始），第二个参数代表宽度值
            sheet.setColumnWidth(4, 2200);//第一个参数代表列id（从4开始），第二个参数代表宽度值
            style.setWrapText(true);//设置自动换行

            //加边框
            HSSFCellStyle cellStyle=wb.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
            //声明列对象
            HSSFCell cell = null;

            //创建标题
            for(int i=0;i<title.length;i++){
                cell = row.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style);
            }


            //把list放进content里
            //String[] title =new String[]{"订单","酒店名称","预定日期","渠道","入住日期","离店日期","入住姓名","底价", "卖价", "毛利", "订单状态", "销售经理", "运营", "操作"};
            int cloumn = 0;
            int j = 0;
            for (int i = 0; i < title.length ; i++){
                if (querySmallVOMap.containsKey(title[i])){
                    cloumn = i;
                    System.out.println("当前列: "+title[i]+", 所属列column: "+cloumn);
                    QuerySmallVO querySmallVO = querySmallVOMap.get(title[i]);
                    if (querySmallVO == null)
                        System.out.println("当前列存储为空");
                    String[] fieldValueList = querySmallVO.getFieldValues();

                    for (j = 0; j < 50000 && j < rowLength-1; ){
//                    /logger.info("fieldValueStr[j+k]: "+fieldValueList[j+k]);
                        content[j++][cloumn] = fieldValueList[j];
                    }

                }
                k +=j-1;
                //添加数据进入excel
                //logger.info("k: "+k);
                System.out.println("row: "+content.length);
                for(int e=0; e<content.length; e++){

                    row = sheet.createRow(e + 1);

                    for(int z=0;z<content[e].length;z++){
                        //System.out.println("改行宽度: "+content[e].length+content[e][z]);
                        //将内容按顺序赋给对应的列对象
                        HSSFCell cel = row.createCell(z);
                        //System.out.println(content[i][z]);
                        cel.setCellValue(content[e][z]);

                    } }

                }
            }


        //响应到客户端
        try {
            try {
                try {
                    fileName = new String(fileName.getBytes(),"UTF-8");
                    System.out.println("fileName: "+fileName);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            String urlA  = ResourceUtils.getURL("").getPath()+fileName;
            File fold = new File(urlA);
            wb.write(fold);
            String url = OBSUtils.completeMultipart(urlA);
            String urlB  = ResourceUtils.getURL("").getPath()+UUID.randomUUID().toString()+".csv";
            ExcelToCsv.excelToCsv(fold.getPath(),urlB);
            Map<String, String> result = new HashMap<>();
            System.out.println("localPathXls: "+urlA);
            System.out.println("localPathCsv: "+urlB);
            result.put("urlXls", urlA);
            result.put("urlCsv", urlB);
            result.put("aeUrl", url);
            return ServerResponse.createBySuccess(result);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError("获取失败");
        }
    }
}
