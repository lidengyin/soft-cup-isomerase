package cn.hctech2006.softcup.isomerase.rjava;

import net.sf.jsqlparser.statement.select.OrderByElement;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.rosuda.JRI.RList;
import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REXPString;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyRJava extends ImportExcelBaseService{
    public static void main(String[] args) throws RserveException, REXPMismatchException, FileNotFoundException {
        MyRJava myRJava = new MyRJava();
        String urla = "https://lidengyin02.obs.cn-south-1.myhuaweicloud.com/2f25d710-9935-44cb-b9d5-1a4cbe556825.xlsx";

        RConnection rConnection = new RConnection();
        rConnection.eval("source('/home/lidengyin/Downloads/code/soft-cup/isomerase/src/main/resources/11_Java_R.R')");
        String url = "/home/lidengyin/Downloads/code/soft-cup/isomerase/src/main/resources/R_ruanjian(1).csv";
        url = "https://lidengyin02.obs.cn-south-1.myhuaweicloud.com:443/f33b192b-a066-4e09-bf39-de862eb14427.csv";
        System.out.println(url);
        URLEncoderUtils.encode("/home/lidengyin/Downloads/code/soft-cup/iso-analyze/src/main/resources/c1909f73-36ce-41da-93c3-3b0485ff3d36.csv");
        rConnection.assign("url", urla);
        //rConnection.assign("len", "100");
        String str=rConnection.eval("paste(capture.output(print(Arima())),collapse='\\n')").asString();
        //rConnection.eval("Arima(url,len)");
        System.out.println(str);
        //匹配回车键
        String [] strArray = str.split("\n");
        for (int i = 1; i < strArray.length; i ++){
            strArray[i] = strArray[i].replaceAll("\\s{2,}", " ").trim();
            System.out.println(strArray[i]);
        }
        Map<String, String> firstAlgo = new HashMap<>();
        for (int i = 1; i < strArray.length; i ++){
            String [] resultArray = strArray[i].split(" ");
            firstAlgo.put(resultArray[0], resultArray[1]);
        }

        double sum = 0.0;
        double avg = 0.0;
        double standardDiviation = 0.0;
        double [] resultDou = new double[firstAlgo.size()];
        int i = 0;
        for (Map.Entry<String,String> term : firstAlgo.entrySet()){
            double temp = Double.parseDouble(term.getValue());
            sum+=temp;
            System.out.println(i+": " +temp);
            resultDou[i++] = temp;
        }
        avg = sum/firstAlgo.size();
        standardDiviation=StandardDiviation(resultDou);
        int orderIndex = 0;
        boolean alramFlag = false;
        double Zresult = 0.0;
        for (Map.Entry<String, String> term : firstAlgo.entrySet()){
             Zresult = 1.0*(Double.parseDouble(term.getValue())-avg)/standardDiviation;
            if (Zresult > 0){
                orderIndex = Integer.parseInt(term.getKey());
                alramFlag = true;
                break;
            }
        }

        String date = myRJava.importExcelWithSimple(urla, orderIndex);
        System.out.println("orderIndex: "+orderIndex);
        System.out.println("平均值: "+avg);
        System.out.println("标准差: "+standardDiviation);
        System.out.println("结果: "+date);
        System.out.println("是否预警: "+alramFlag);


    }
    //https://lidengyin02.obs.cn-south-1.myhuaweicloud.com:443/f33b192b-a066-4e09-bf39-de862eb14427.csv
    //方差s^2=[(x1-x)^2 +...(xn-x)^2]/n 或者s^2=[(x1-x)^2 +...(xn-x)^2]/(n-1)
    /**
     * 获取远程文件
     *
     * @param remoteFilePath 远程文件路径
     *
     */
    public static String downloadFile(String remoteFilePath) throws FileNotFoundException {
        String localFilePath = ResourceUtils.getURL("").getPath()+UUID.randomUUID().toString()+".csv";
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        File f = new File(localFilePath);
        try {
            urlfile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection) urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
            return localFilePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
            URL url1 = new URL(url);
            URLConnection connection = url1.openConnection();
            workbook = super.getWorkbookByInputStream(connection.getInputStream(), url);
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
            System.out.println(rowNum+":"+super.getCellValue(sheet, row, 1));
            if (orderIndex == rowNum) {
                return super.getCellValue(sheet, row, 1);
            }
            rowNum ++;

        }
        return null;
    }
}
