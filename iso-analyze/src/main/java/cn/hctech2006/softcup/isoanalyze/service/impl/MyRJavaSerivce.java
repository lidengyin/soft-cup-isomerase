package cn.hctech2006.softcup.isoanalyze.service.impl;

import net.sf.jsqlparser.statement.select.OrderByElement;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.stereotype.Service;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MyRJavaSerivce extends ImportExcelBaseService {
    public Map<String, String> rAlarm(String urlCsv, String urlElx) throws RserveException, REXPMismatchException, IOException {
        RConnection rConnection = new RConnection();
        rConnection.eval("source('/home/lidengyin/Downloads/code/soft-cup/iso-analyze/src/main/resources/11_Java_R.R')");
        rConnection.assign("url",urlCsv);
        String str=rConnection.eval("paste(capture.output(print(Arima(url))),collapse='\\n')").asString();
        //System.out.println(str);
        //匹配回车键
        String [] strArray = str.split("\n");
        for (int i = 1; i < strArray.length; i ++){
            strArray[i] = strArray[i].replaceAll("\\s{2,}", " ").trim();
            //System.out.println(strArray[i]);
        }
        Map<String, String> firstAlgo = new HashMap<>();
        for (int i = 1; i < strArray.length; i ++){
            String [] resultArray = strArray[i].split(" ");
            firstAlgo.put(resultArray[0], resultArray[1]);
        }
        //
        double sum = 0.0;
        double avg = 0.0;
        double standardDiviation = 0.0;
        double [] resultDou = new double[firstAlgo.size()];
        int i = 0;
        for (Map.Entry<String,String> term : firstAlgo.entrySet()){
            double temp = Double.parseDouble(term.getValue());
            sum+=temp;
            //System.out.println(i+": " +temp);
            resultDou[i++] = temp;
        }
        avg = sum/firstAlgo.size();
        standardDiviation=StandardDiviation(resultDou);
        int orderIndex = 0;
        boolean alramFlag = false;
        double Zresult = 0.0;
        for (Map.Entry<String, String> term : firstAlgo.entrySet()){
             Zresult = 1.0*(Double.parseDouble(term.getValue())-avg)/standardDiviation;
            if (Zresult > 3){
                orderIndex = Integer.parseInt(term.getKey());
                alramFlag = true;
                break;
            }
        }

        String date = importExcelWithSimple(urlElx, orderIndex);
        date = (date==null) ? "运行正常":date;
        System.out.println("orderIndex: "+orderIndex);
        System.out.println("平均值: "+avg);
        System.out.println("标准差: "+standardDiviation);
        System.out.println("结果: "+date);
        System.out.println("是否预警: "+alramFlag);
        Map<String, String> result = new HashMap<>();
        if (alramFlag){
            result.put("alarmFlag", "1");
        }else {
            result.put("alarmFlag", "0");
        }
        result.put("aeResult", date);
        return result;
    }
    //方差s^2=[(x1-x)^2 +...(xn-x)^2]/n 或者s^2=[(x1-x)^2 +...(xn-x)^2]/(n-1)

    public static double Variance(double[] x) {
        int m=x.length;
        double sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x[i];
        }
        double dAve=sum/m;//求平均值
        double dVar=0;
        for(int i=0;i<m;i++){//求方差
            dVar+=(x[i]-dAve)*(x[i]-dAve);
        }
        return dVar/m;
    }

    //标准差σ=sqrt(s^2)
    public static double StandardDiviation(double[] x) {
        int m=x.length;
        double sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x[i];
        }
        double dAve=sum/m;//求平均值
        double dVar=0;
        for(int i=0;i<m;i++){//求方差
            dVar+=(x[i]-dAve)*(x[i]-dAve);
        }
        return Math.sqrt(dVar/m);
    }
    public String importExcelWithSimple(String url , int orderIndex) {
        int rowNum = 0;//已取值的行数
        int colNum = 0;//列号
        int realRowCount = 0;//真正有数据的行数

        //得到工作空间
        Workbook workbook = null;
        try {
            File file = new File(url);
            workbook = super.getWorkbookByInputStream(new FileInputStream(file), url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //得到工作表
        Sheet sheet = super.getSheetByWorkbook(workbook, 0);
        if (sheet.getRow(1112000) != null){
            throw new RuntimeException("系统已限制单批次导入必须小于或等于2000笔！");
        }

        realRowCount = sheet.getPhysicalNumberOfRows();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<OrderByElement.NullOrdering> list = new ArrayList<>();


        for(Row row:sheet) {

            if(realRowCount == rowNum) {
                break;
            }

            if(super.isBlankRow(row)) {//空行跳过
                continue;
            }

            if(row.getRowNum() == -1) {
                continue;
            }else {
                if(row.getRowNum() == 0) {//第一行表头跳过
                    continue;
                }
            }
            if (orderIndex == rowNum && orderIndex != 0) {
                return super.getCellValue(sheet, row, 1);
            }
            rowNum ++;

        }
        return null;
    }
}
