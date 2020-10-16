package cn.hctech2006.softcup.isomerase3.service.impl;


import cn.hctech2006.softcup.isomerase3.bean.NlField;
import cn.hctech2006.softcup.isomerase3.common.ServerResponse;
import cn.hctech2006.softcup.isomerase3.mapper.NlFieldMapper;
import cn.hctech2006.softcup.isomerase3.vo.ChartBeanVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FieldServiceImpl {
    @Autowired
    private NlFieldMapper fieldMapper;
    @Autowired
    private DynamicServiceImpl dynamicService;
    /**
     * 字段上传
     * @param fieldName
     * @param fieldValue
     * @param tableName
     * @return
     */
    public ServerResponse uploadFiled(String fieldName, String fieldValue, String tableName, String  fieldId
            , String mainId, String chartType, String chartOption){

        NlField field = new NlField();
        field.setFieldName(fieldName);
        field.setFieldValue(fieldValue);
        field.setTableName(tableName);
        field.setDelFlag("1");
        field.setFiledId(fieldId);
        field.setMainId(mainId);
        field.setChartType(chartType);
        field.setChartOption(chartOption);

        int result = fieldMapper.insert(field);
        if (result > 0) return ServerResponse.createBySuccess("上传成功");
        return ServerResponse.createByError("上传失败");
    }

    public ServerResponse findByMainId(String mainId){

        List<String> fieldNames = fieldMapper.selectFieldNameByMainId(mainId);
        if (fieldNames == null) return ServerResponse.createByError("该列不存在");
        Map<String, ChartBeanVo> fieldMap = new HashMap<>();

        for (String fieldName : fieldNames){
            String chartType = fieldMapper.selectChartTypeByMainIdAndFieldName(mainId, fieldName);
            String chartOption = fieldMapper.selectChartOptionByMainIdAndFieldName(mainId, fieldName);
            List<String> fieldValues = fieldMapper.selectFieldValueByMainIdAndFieldName(mainId ,fieldName);
            ChartBeanVo chartBeanVo = new ChartBeanVo();
            chartBeanVo.setChartType(chartType);
            chartBeanVo.setChartOption(chartOption);
            chartBeanVo.setFieldValues(fieldValues);
            fieldMap.put(fieldName, chartBeanVo);
        }
        return ServerResponse.createBySuccess(fieldMap);
    }

    public ServerResponse findFieldNameByMainId(String mainId){
        if (mainId == null || mainId.equalsIgnoreCase("")) return ServerResponse.createByError("请填写正确的列表id");
        List<String> fieldNames = fieldMapper.selectFieldNameByMainId(mainId);
        return ServerResponse.createBySuccess(fieldNames);
    }

    /**
     * EXCEL导出接口
     * @param request
     * @param response
     * @throws Exception
     */

    public void excelExport(HttpServletRequest request, HttpServletResponse response,
                            String mainId) throws Exception {

        ServerResponse serverResponse = findByMainId(mainId);
        if (!serverResponse.isSuccess()) return;
        Map<String, List<String>> fieldMap = (Map<String, List<String>>) serverResponse.getData();
        //开始模拟获取数据，实际应该在数据库查出来
        //EXCEL表导出核心代码
        //声明一个Excel
        HSSFWorkbook wb =null;
        //title代表的是你的excel表开头每列的名字
//        String[] title =new String[];
        //新设置开头字段的姓名
        ServerResponse srFieldNames = findFieldNameByMainId(mainId);
        List<String> fieldNames  = (List<String>) srFieldNames.getData();
        String[] title =new String[fieldNames.size()];
        fieldNames.toArray(title);

        String name="导出excel";
        //excel文件名
        String fileName = name+".xls";
        //sheet名
        String sheetName = name+"表";
        //二维数组铺满整个Excel
        int rowLength = 0;
        for (Map.Entry<String, List<String>> term : fieldMap.entrySet()){
            List<String> fieldParam = term.getValue();
            if (fieldParam.size() > rowLength) rowLength=fieldParam.size();
        }
        String[][] content = new String[rowLength][title.length];
        //--------------------------------------------------------------------------------------------
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();

        //设置背景色
        //style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //设置边框

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
        //font.setFontName("黑体");
        //font.setFontHeightInPoints((short) 16);//设置字体大小
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
        for (Map.Entry<String, List<String>> term : fieldMap.entrySet()){

//            boolean res = Arrays.asList(title).contains(term.getKey()); // true
//            if (res) title[i] = term.getKey();
//            content[i] = new String[title.length];
//            for (int j = 0; j < title.length; j ++){
//
//
//            }
//            content[i] = new String[title.length];
//            for (int j = 0; j < title.length; j ++){
//                content[i][j] = term
//            }
            List<String> fieldValue = term.getValue();
            int j = 0;

            for (int i = 0; i < title.length ; i++){
                if (term.getKey().equalsIgnoreCase(title[i])){
                    cloumn = i;
                }
            }
            for (String param : fieldValue){
                content[j++][cloumn] = param;
            }

        }

        //添加数据进入excel

        for(int i=0;i<content.length;i++){

            row = sheet.createRow(i + 1);

            for(int j=0;j<content[i].length;j++){

                //将内容按顺序赋给对应的列对象
                HSSFCell cel = row.createCell(j);
                cel.setCellValue(content[i][j]);

            } }


        //响应到客户端
        try {
            try {
                try {
                    fileName = new String(fileName.getBytes(),"ISO8859-1");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                response.setContentType("application/pdf;charset=ISO8859-1");
                response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //response.setContentType();
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
