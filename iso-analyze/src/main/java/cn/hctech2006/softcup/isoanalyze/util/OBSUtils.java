package cn.hctech2006.softcup.isoanalyze.util;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.CompleteMultipartUploadResult;
import com.obs.services.model.UploadFileRequest;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * OBS工具类
 */
public class OBSUtils {
    private static final String endPoint = "https://obs.cn-south-1.myhuaweicloud.com";
    private static final String ak = "IRAHTNBNOXBG03KWFSYH";
    private static final String sk = "DOLg3OQzzPf5Oe6w4a9ViUjWWmKjq9kq7ECFyFWL";
    private static final String bucketName = "lidengyin02";

    public static String completeMultipart(MultipartFile file) throws IOException {
        String url = ResourceUtils.getURL("").getPath();
        String fileName = file.getOriginalFilename();
        url = url+fileName;
        File fold = new File(url);
        file.transferTo(fold);

        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        UploadFileRequest request = new UploadFileRequest(bucketName, fileName);
        request.setUploadFile(fold.getAbsolutePath());
        request.setTaskNum(12);
        request.setPartSize(30*1024*1024);
        request.setEnableCheckpoint(true);
        try{
            CompleteMultipartUploadResult result = obsClient.uploadFile(request);
            String resultUrl = result.getObjectUrl();
            fold.delete();
            System.out.println(result.getObjectUrl());
            return resultUrl;
        }catch (ObsException e){
            e.printStackTrace();

        }finally {
            //关闭obs
            obsClient.close();
        }
        //使用访问obs
        return "上传失败";

    }
    public static String completeMultipart(String url) throws IOException {


        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        UploadFileRequest request = new UploadFileRequest(bucketName, UUID.randomUUID().toString()+".xlsx");
        request.setUploadFile(url);
        request.setTaskNum(12);
        request.setPartSize(30*1024*1024);
        request.setEnableCheckpoint(true);
        try{
            CompleteMultipartUploadResult result = obsClient.uploadFile(request);
            String resultUrl = result.getObjectUrl();
            System.out.println(result.getObjectUrl());
            return resultUrl;
        }catch (ObsException e){
            e.printStackTrace();

        }finally {
            //关闭obs
            obsClient.close();
        }
        //使用访问obs
        return "上传失败";
    }   public static String completeMultipartCSV(String url) throws IOException {


        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        UploadFileRequest request = new UploadFileRequest(bucketName, UUID.randomUUID().toString()+".csv");
        request.setUploadFile(url);
        request.setTaskNum(12);
        request.setPartSize(30*1024*1024);
        request.setEnableCheckpoint(true);
        try{
            CompleteMultipartUploadResult result = obsClient.uploadFile(request);
            String resultUrl = result.getObjectUrl();
            System.out.println(result.getObjectUrl());
            return resultUrl;
        }catch (ObsException e){
            e.printStackTrace();

        }finally {
            //关闭obs
            obsClient.close();
        }
        //使用访问obs
        return "上传失败";
    }
    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8211/umbrella/field/export_excel.do?mainId=7bde6ae2-d9b6-4d95-b0b9-2cd94dcc693f";
        completeMultipart(url);
    }
}
