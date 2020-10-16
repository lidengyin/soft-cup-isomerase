package cn.hctech2006.softcup.isodataquery.service.impl;

import cn.hctech2006.softcup.isodataquery.bean.NlField;
import cn.hctech2006.softcup.isodataquery.bean.NlQuery;
import cn.hctech2006.softcup.isodataquery.bean.NlQuerySmall;
import cn.hctech2006.softcup.isodataquery.common.ServerResponse;
import cn.hctech2006.softcup.isodataquery.dto.QueryDTO;
import cn.hctech2006.softcup.isodataquery.mapper.NlQueryMapper;
import cn.hctech2006.softcup.isodataquery.mapper.NlQuerySmallMapper;
import cn.hctech2006.softcup.isodataquery.service.DataFieldService;
import cn.hctech2006.softcup.isodataquery.service.DataTableService;
import cn.hctech2006.softcup.isodataquery.service.DatasourceService;
import cn.hctech2006.softcup.isodataquery.util.OBSUtils;
import cn.hctech2006.softcup.isodataquery.vo.*;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class QueryServiceImpl {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+'ssss");
    private Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);
    @Autowired
    private NlQueryMapper queryMapper;
    @Autowired
    private NlQuerySmallMapper querySmallMapper;
    @Autowired
    private DataFieldService dataFieldService;
    @Autowired
    private DataTableService datatableService;
    @Autowired
    private DatasourceService datasourceService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询记录上传, 事物全部失败类型
     * @param queryName
     * @param queryDTOS
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ServerResponse upload(String queryName, List<QueryDTO> queryDTOS) throws Exception {


        if (queryDTOS == null) return ServerResponse.createByError("参数为空");
        //设置查询唯一标识
        String queryId = UUID.randomUUID().toString();
        //设置查询数据源编号
        for (QueryDTO queryDTO : queryDTOS){
            ServerResponse response =  dataFieldService.getOne(queryDTO.getFdId());
            NlField field = objectToSerilizable(response);
            if (field == null) System.out.println("field是null");
            logger.info("dbId: "+field.getDbId());
            String dbId = field.getDbId();
            String dtId = field.getDtId();
            String fdName = field.getFdName();
            String chartType = queryDTO.getChartType();
            String chartOption = queryDTO.getChartOption();
            //获取比较参数列表
            List<ParamDTO> params = queryDTO.getParams();
            Map<String, String> result = (Map<String, String>) datatableService.getOne(dtId).getData();
            String dtName = result.get("dtName");
            result = (Map<String, String>) datasourceService.getDbNameAndDbType(dbId).getData();
            String dbType = result.get("dbType");
            String dbDatabase = result.get("dbName");
            String fdId = field.getFdId();

            response = queryDynamicRedis(fdId, dbId, fdName, dtName, params, queryId, chartType, chartOption, dbType, dbDatabase);
            if (!response.isSuccess()) return ServerResponse.createByError("项目子项存储失败, 中断");
        }
        NlQuery query = new NlQuery();
        query.setQuId(queryId);
        query.setQuName(queryName);
        query.setQuTime(new Date());
        query.setShowFlag("1");
        int result = queryMapper.insert(query);
        String url = redisExcelExport(queryId).getMsg();
        query.setQuUrl(url);
        result = queryMapper.updateQuUrlByQuId(queryId, url);
        if (result > 0) return ServerResponse.createBySuccess("查询记录保存成功, 编号为: ", queryId);
        return ServerResponse.createByError("查询记录保存失败");
    }

    /**
     * 通过判断类型导出完整的sql语句
     * @param paramDTOS
     * @param fdName
     * @param dtName
     * @return
     */
    private String fromJudgeTypeToSql(List<ParamDTO> paramDTOS, String fdName, String dtName, String dbType){
        String sql = "select ";
        sql= sql + fdName +" from "+dtName+ " where 1=1";
        List<Map<String, Object>> resultList;
        for (ParamDTO paramDTO : paramDTOS){
            String pmType = paramDTO.getPmType();
            String judgeType = paramDTO.getJudgeType();
            String pmName = paramDTO.getPmName();
            String pmValue = paramDTO.getPmValue();
            if (pmType.equalsIgnoreCase("DATE")){
                if (dbType.equalsIgnoreCase("sqlserver")){
                    if (judgeType.equalsIgnoreCase("equal")){
                        sql+=" and "+ pmName +"= "+pmValue;
                    }
                    else if (judgeType.equalsIgnoreCase("bigger")){
                        sql+=" and (SELECT DATEDIFF(S,'1970-01-01 08:00:00', "+pmName+")) > (SELECT DATEDIFF(S,'1970-01-01 08:00:00', "+"'"+pmValue+"'"+"))";
                    }else if(judgeType.equalsIgnoreCase("smaller")){
                        sql+=" and (SELECT DATEDIFF(S,'1970-01-01 08:00:00', "+pmName+")) < (SELECT DATEDIFF(S,'1970-01-01 08:00:00', "+"'"+pmValue+"'"+"))";
                    }
                }else if(dbType.equalsIgnoreCase("mysql")){
                    if (judgeType.equalsIgnoreCase("equal")){
                        sql+=" and "+ pmName +"= "+pmValue;
                    }
                    else if (judgeType.equalsIgnoreCase("bigger")){
                        sql+=" and TO_DAYS("+pmName+") > TO_DAYS("+"'"+pmValue+"'"+")";
                    }else if(judgeType.equalsIgnoreCase("smaller")){
                        sql+=" and TO_DAYS("+pmName+") < TO_DAYS("+"'"+pmValue+"'"+")";
                    }
                }

            }
            else if (judgeType.equalsIgnoreCase("STR")){
                if (judgeType.equalsIgnoreCase("equal")){
                    sql+=" and "+ pmName +"= "+"'"+pmValue+"'";
                }
                else if (judgeType.equalsIgnoreCase("dim")){
                    sql+=" and "+pmName+"like concat('%',"+"'"+pmValue+"'"+",'%')";
                }
            }else if (judgeType.equalsIgnoreCase("NUM")){
                if (judgeType.equalsIgnoreCase("equal")){
                    sql+=" and "+ pmName +"= "+"'"+pmValue+"'";
                }
                else if (judgeType.equalsIgnoreCase("bigger")){
                    sql+=" and "+pmName+" > "+"'"+pmValue+"'";
                }else if(judgeType.equalsIgnoreCase("smaller")){
                    sql+=" and "+pmName+" > "+"'"+pmValue+"'";
                }
            }else if (judgeType.equalsIgnoreCase("BOOL")){
                if (judgeType.equalsIgnoreCase("等于")){
                    sql+=" and "+ pmName +"= "+"'"+pmValue+"'";
                }
            }
        }
        System.out.println("sql: "+sql);
        return sql;
    }
    /**
     * 读取一条查询记录的值
     * @param quId
     * @return
     * @throws Exception
     */
    public ServerResponse queryOneResult(String quId) throws Exception {

        NlQuery query = queryMapper.selectByQuId(quId);
        List<NlQuerySmall> querySmalls = querySmallMapper.selectByQuId(quId);
        Map<String, QuerySmallVO> fieldMap = new HashMap<>();
        for (NlQuerySmall querySmall : querySmalls){
            String chartType = querySmall.getQsChartType();
            String chartOption = querySmall.getQsChartOption();
            String fdName = querySmall.getQsField();
            String qsParams = querySmall.getQsParams();
            List<ParamDTO> paramDTOS = (List<ParamDTO>) JSONObject.parseArray(qsParams, ParamDTO.class);

            List<Map<String, Object>> resultList = (List<Map<String, Object>>) redisTemplate.opsForValue().get(querySmall.getQsId());
            if (resultList == null){
                logger.info("---------------------redis缓存为空--------------");
                logger.info("---------------------开始读取mysql数据--------------");
                ServerResponse response = dataFieldService.getOne(querySmall.getFdId());
                NlField field = objectToSerilizable(response);
                String dbId = field.getDbId();
                String dtName = querySmall.getDtName();
                resultList = queryDynamicMysql(dbId, fdName,dtName, paramDTOS);
            }else{
                logger.info("---------------------redis缓存存在--------------");
                logger.info("---------------------开始读取redis数据--------------");
            }
            List<String> fieldValues = new ArrayList<>();
            for (Map<String, Object> map : resultList){
                for (Map.Entry<String, Object> term : map.entrySet()){
                    String value = term.getValue().toString();
                    Boolean is = value.matches("^\\d{4}-(?:0[1-9]|1[0-2])-(?:0[1-9]|[1-2]\\d|3[0-1])T(?:[0-1]\\d|2[0-3]):[0-5]\\d:[0-5]\\d.\\d{3}\\+\\d{4}$");
                    if (is){
                        Date date = sdf1.parse(term.getValue().toString());
                        value = sdf.format(date);
                        System.out.println("value: "+value);
                    }else{
                        value=term.getValue().toString();
                    }
                    //System.out.println(value);
                    fieldValues.add(value);
                }
            }
            QuerySmallVO querySmallVO = new QuerySmallVO();
            querySmallVO.setChartType(chartType);
            querySmallVO.setChartOption(chartOption);
            querySmallVO.setFieldValues(listToArray(fieldValues));
            querySmallVO.setParamDTOS(paramDTOS);

            fieldMap.put(fdName, querySmallVO);
        }
        QueryDetailsVO queryDetailsVO = queryPOJOToquDetailsVO(query, fieldMap);
        return ServerResponse.createBySuccess(queryDetailsVO);
    }


    /**
     * 列表展示
     * @param showFlag
     * @return
     */
    public ServerResponse list(int pageNum, int pageSize, String showFlag){
        PageHelper.startPage(pageNum, pageSize);
        List<QueryListVO> queryListVOS = new ArrayList<>();
        List<NlQuery> queries = queryMapper.selectByShowFlag(showFlag);
        for (NlQuery query : queries){
            QueryListVO queryListVO = queryPOJOTOQueryListVO(query);
            queryListVOS.add(queryListVO);
        }
        PageInfo pageInfo = new PageInfo(queries);
        pageInfo.setList(queryListVOS);
        QueryPageInfoVO queryPageInfoVO = pageInfoToModelPageInfoVO(pageInfo);
        return ServerResponse.createBySuccess(queryPageInfoVO);
    }
    private QueryPageInfoVO pageInfoToModelPageInfoVO(PageInfo pageInfo){
        QueryPageInfoVO queryPageInfoVO = new QueryPageInfoVO();
        List<QueryListVO> queryListVOS = pageInfo.getList();
        queryPageInfoVO.setQueryListVOS(queryListVOS);
        queryPageInfoVO.setPageNum(pageInfo.getPageNum());
        queryPageInfoVO.setPageSize(pageInfo.getPageSize());
        queryPageInfoVO.setPages(pageInfo.getPages());
        queryPageInfoVO.setTotal(pageInfo.getTotal());
        queryPageInfoVO.setNavigateFirstPage(pageInfo.getNavigateFirstPage());
        queryPageInfoVO.setNavigateLastPage(pageInfo.getNavigateLastPage());
        queryPageInfoVO.setNavigatepageNums(pageInfo.getNavigatepageNums());
        return queryPageInfoVO;
    }
    /**
     * 根据查询实例编号获取参数列表
     * @param quId
     * @return
     */
    public ServerResponse getQuerySmallByQuId(String quId){
        List<NlQuerySmall> querySmalls = querySmallMapper.selectByQuId(quId);
        List<QuerySmallListVO> querySmallListVOS = new ArrayList<>();
        for (NlQuerySmall querySmall : querySmalls){
            QuerySmallListVO querySmallListVO = querySmallPOJOTOQuerySmallListVO(querySmall);
            querySmallListVOS.add(querySmallListVO);
        }
        return ServerResponse.createBySuccess(querySmallListVOS);
    }

    /**
     * 服务间调用, 根据查询实例编号获取查询过实例名
     * @param quId
     * @return
     */
    public ServerResponse getQueryName(String quId){
        NlQuery query = queryMapper.selectByQuId(quId);
        if (query == null) return ServerResponse.createByError();
        String quName = query.getQuName();
        Map<String, String> result = new HashMap<>();
        result.put("quName",quName);
        return ServerResponse.createBySuccess(result);
    }


    private NlField objectToSerilizable(ServerResponse response){
        ObjectMapper mapper=new ObjectMapper();
        NlField field = mapper.convertValue(response.getData(), NlField.class);
        return field;
    }
    private  List<Map<String, Object>> objectToListSerilizable(ServerResponse response){
        ObjectMapper mapper=new ObjectMapper();
        List<Map<String, Object>> resultList = mapper.convertValue(response.getData(), List.class);
        return resultList;
    }
    private QuerySmallListVO querySmallPOJOTOQuerySmallListVO(NlQuerySmall querySmall){
        QuerySmallListVO querySmallListVO = new QuerySmallListVO();
        querySmallListVO.setField(querySmall.getQsField());
        return querySmallListVO;
    }

    /**
     * POJO对象获取查询列表显示层参数
     * @param query
     * @return
     */
    private QueryListVO queryPOJOTOQueryListVO(NlQuery query){
        QueryListVO queryListVO = new QueryListVO();
        queryListVO.setQuId(query.getQuId());
        queryListVO.setQuName(query.getQuName());
        queryListVO.setQuTime(dateToStr(query.getQuTime()));
        queryListVO.setQuParams((query.getQuParams()!=null) ? query.getQuParams() : "没有参数");
        return queryListVO;
    }
    private String dateToStr(Date date){
        return sdf.format(date);
    }
    /**
     * 将POJO对象转换成前段显示层对象
     * @param query
     * @param fieldMap
     * @return
     */

    private QueryDetailsVO queryPOJOToquDetailsVO(NlQuery query, Map<String, QuerySmallVO> fieldMap){
        System.out.println("query: "+query.toString());
        QueryDetailsVO queryDetailsVO = new QueryDetailsVO();
        queryDetailsVO.setQsVOMap(fieldMap);
        queryDetailsVO.setQuName(query.getQuName());
        queryDetailsVO.setQuParams(query.getQuParams());
        queryDetailsVO.setQuTime(sdf.format(query.getQuTime()));
        queryDetailsVO.setQuUrl(query.getQuUrl());
        return queryDetailsVO;
    }

    /**
     * 从Mysql数据库读取查询集合子集
     * @param dbId
     * @param fdName
     * @param dtName
     * @param params
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryDynamicMysql(String dbId, String fdName , String dtName, List<ParamDTO> params) {
        Map<String, String>result = (Map<String, String>) datasourceService.getDbNameAndDbType(dbId).getData();
        String dbType = result.get("dbType");
        String dbDatabase = result.get("dbName");
        String sql = fromJudgeTypeToSql(params,fdName,dtName, dbType);
        ServerResponse response =  datasourceService.getFields(dbId,sql);
        List<Map<String, Object>> resultList =objectToListSerilizable(response);
        return resultList;
    }

    /**
     * 列表转数组私有方法
     * @param lists
     * @return
     */
    private String[] listToArray(List<String> lists){
        String []array = new String[lists.size()];
        array = lists.toArray(array);
        return array;
    }
    /**
     * 存储查询记录到缓存, 同时存储查询记录子项
     * @param dbId
     * @param fdName
     * @param dtName
     * @param params
     * @param queryId
     * @param chartType
     * @param chartOption
     * @return
     * @throws Exception
     */
    public ServerResponse queryDynamicRedis(String fdId, String dbId, String fdName , String dtName, List<ParamDTO> params,
                                            String queryId, String chartType, String chartOption, String dbType, String dbDatabase) throws Exception {
        if (fdName == null || fdName.equalsIgnoreCase("")) return ServerResponse.createByError("字段不能为空");

        String sql = fromJudgeTypeToSql(params, fdName, dtName, dbType);
        ///List<Map<String, Object>> resultList = (List<Map<String, Object>>) datasourceService.getFields(dbId, fdName, dtName, mapToStr(params)).getData();
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) datasourceService.getFieldsBySQL(dbId, sql).getData();
        //字段对数据类型进行判断与转换
        NlQuerySmall qySmall = new NlQuerySmall();
        qySmall.setQsField(fdName);
        String paramsJson = JSONObject.toJSONString(params);
        qySmall.setQsParams(paramsJson);
        qySmall.setQuId(queryId);
        qySmall.setQsChartType(chartType);
        qySmall.setQsChartOption(chartOption);
        String qsId = queryId+UUID.randomUUID().toString();
        qySmall.setFdId(fdId);
        qySmall.setQsId(qsId);
        qySmall.setDtName(dtName);
        qySmall.setDbType(dbType);
        qySmall.setDbDatabase(dbDatabase);
        int result = querySmallMapper.insert(qySmall);
        if (result <= 0) return ServerResponse.createByError("数据集存储失败");
        redisTemplate.opsForValue().set(qsId, resultList,1, TimeUnit.HOURS);
        return ServerResponse.createBySuccess(qsId);
    }


    private static Map<String, String> strToMap(String str){
        Map<String, String> map = JSONObject.parseObject(str,Map.class);
        return map;
    }
    private static String mapToStr(Map<String, String> map){
        String str = JSONObject.toJSONString(map);
        return str;
    }




    /**
     * 得到查询实例数据集的在线excel地址
     * @param queryId
     * @return
     * @throws Exception
     */
    public ServerResponse redisExcelExport(
            String queryId) throws Exception {

        ServerResponse serverResponse = queryOneResult(queryId);
        if (!serverResponse.isSuccess()) return ServerResponse.createByError("获取失败");
        QueryDetailsVO queryDetailsVO = (QueryDetailsVO) serverResponse.getData();
        Map<String, QuerySmallVO> querySmallVOMap = queryDetailsVO.getQsVOMap();

        //开始模拟获取数据，实际应该在数据库查出来
        //EXCEL表导出核心代码
        //声明一个Excel
        HSSFWorkbook wb =null;
        //title代表的是你的excel表开头每列的名字
//        String[] title =new String[];
        //新设置开头字段的姓名
        List<NlQuerySmall> querySmalls = querySmallMapper.selectByQuId(queryId);
        List<String> fieldNames  = new ArrayList<>();
        for (NlQuerySmall querySmall : querySmalls){
            fieldNames.add(querySmall.getQsField());
        }
        String[] title =new String[fieldNames.size()];
        fieldNames.toArray(title);
//        for (String param : title){
//            //System.out.println(param);
//        }
        String name="导出excel";
        //excel文件名
        String fileName = queryId+".xls";
        //sheet名
        String sheetName = name+"表";
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
            for (Map.Entry<String, QuerySmallVO> term : querySmallVOMap.entrySet()){

                QuerySmallVO querySmallVO = term.getValue();
                String[] fieldValueList = querySmallVO.getFieldValues();
                //int j = 0;
                for (int i = 0; i < title.length ; i++){
                    if (term.getKey().equalsIgnoreCase(title[i])){

                        cloumn = i;
                        System.out.println(term.getKey() + ",column: "+cloumn);
                    }
                }
                //todo 首先把列表转成数组
                String[] fieldValueStr;
                if (rowLength < 50000) {

                    fieldValueStr = new String[rowLength];
                }else{
                    fieldValueStr = new String[50000];
                }


                for (j = 0; j < 50000 && j+k < rowLength-1; ){
                    content[j++][cloumn] = fieldValueList[j+k];
                }
            }
            k +=j-1;
            //添加数据进入excel
            //logger.info("k: "+k);
            for(int i=0; i<content.length; i++){

                row = sheet.createRow(i + 1);

                for(int z=0;z<content[i].length;z++){

                    //将内容按顺序赋给对应的列对象
                    HSSFCell cel = row.createCell(z);
                    //System.out.println(content[i][z]);
                    cel.setCellValue(content[i][z]);

                } }
        }

        //响应到客户端
        try {
            try {
                try {
                    fileName = new String(fileName.getBytes(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            String url  = ResourceUtils.getURL("").getPath()+fileName;
            File fold = new File(url);
            wb.write(fold);
            url = OBSUtils.completeMultipart(url);
            return ServerResponse.createBySuccess(url);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError("获取失败");
        }
    }
}
