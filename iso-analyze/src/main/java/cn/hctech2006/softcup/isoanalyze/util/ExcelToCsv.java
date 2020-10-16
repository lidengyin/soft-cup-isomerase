package cn.hctech2006.softcup.isoanalyze.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class ExcelToCsv {
    /**
     * 将excel表格转成csv格式
     * @param oldFilePath
     * @param newFilePath
     */
    public static void excelToCsv(String oldFilePath,String newFilePath){
        String buffer = "";
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        Row rowHead = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        String filePath =oldFilePath ;

        wb = readExcel(filePath);
        if(wb != null){
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                sheet = wb.getSheetAt(i);
                // 标题总列数
                rowHead = sheet.getRow(i);
                if (rowHead == null) {
                    continue;
                }
                //总列数colNum
                int colNum = rowHead.getPhysicalNumberOfCells();
                String[] keyArray = new String[colNum];
                Map<String, Object> map = new LinkedHashMap<>();

                //用来存放表中数据
                list = new ArrayList<Map<String,String>>();
                //获取第一个sheet
                sheet = wb.getSheetAt(i);
                //获取最大行数
                int rownum = sheet.getPhysicalNumberOfRows();
                //获取第一行
                row = sheet.getRow(0);
                //获取最大列数
                int colnum = row.getPhysicalNumberOfCells();
                for (int n = 0; n<rownum; n++) {
                    row = sheet.getRow(n);
                    cellData =  getCellFormatValue(row.getCell(0)).toString();
                    if (cellData.equalsIgnoreCase("null,"))
                        continue;
                    for (int m = 0; m < colnum; m++) {

                        cellData =  getCellFormatValue(row.getCell(m)).toString();
                        buffer +=cellData;
                    }
                    buffer = buffer.substring(0, buffer.lastIndexOf(","));
                    buffer += "\n";

                }

                String savePath = newFilePath;
                File saveCSV = new File(savePath);
                try {
                    if(!saveCSV.exists())
                        saveCSV.createNewFile();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(saveCSV));
                    writer.write(buffer);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }


    //读取excel
    public static Workbook readExcel(String filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else{
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null && !cell.getStringCellValue().equalsIgnoreCase("null")){
            //判断cell类型
            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:{
                    String cellva = getValue(cell);
                    cellValue = cellva.replaceAll("\n", " ") + ",";
                    break;
                }
                case Cell.CELL_TYPE_FORMULA:{
                    //判断cell是否为日期格式
                    if(DateUtil.isCellDateFormatted(cell)){
                        //转换为日期格式YYYY-mm-dd
                        cellValue = String.valueOf(cell.getDateCellValue()).replaceAll("\n", " ") + ",";
                    }else{
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue()).replaceAll("\n", " ") + ",";
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:{
                    cellValue = String.valueOf(cell.getRichStringCellValue()).replaceAll("\n", " ") + ",";
                    break;
                }
                default:
                    cellValue = "null"+ ",";
            }
        }else{
            cellValue = "null"+ ",";
        }
        return cellValue;
    }

    /**
     * 此方法为去掉转csv时数字等默认加上的小数点
     * 如果不需要刻意不调用此方法
     */
    public static  String getValue(Cell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            Object inputValue = null;// 单元格值
            Long longVal = Math.round(hssfCell.getNumericCellValue());
            Double doubleVal = hssfCell.getNumericCellValue();
            if(Double.parseDouble(longVal + ".0") == doubleVal){   //判断是否含有小数位.0
                inputValue = longVal;
            }
            else{
                inputValue = doubleVal;
            }
            DecimalFormat df = new DecimalFormat("#");    //在此处更改小数点及位数，按自己需求选择；
            return String.valueOf(df.format(inputValue));      //返回String类型
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }


    public static void main(String[] args) throws Exception {
        String srcPath= "/home/lidengyin/Downloads/code/soft-cup/3f80e3e7-3d5f-46c5-ac09-891f9541c65f.xls";
        String orderPath = ResourceUtils.getURL("").getPath()+ UUID.randomUUID().toString()+".csv";
        System.out.println(orderPath);
        ExcelToCsv.excelToCsv(srcPath,orderPath);

    }

}

