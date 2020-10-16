package cn.hctech2006.softcup.isomerase3.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * OSS文件上传
 */
public class OSSUtils {
    @Autowired
    //private  static OSSConstant ossConstant;
    static String bucketName = "hcnet2006-file-apk";

    static String accessKeyId = "LTAI4GKqcxyRWHyhj8rAjHGz";

    static String accessKeySecret = "Sd9auXA9vvZLnVcrUBbF7MYp7JOXf2";

    static String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    //private static OSS ossClient ;
    private  static  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    public static String upload(File file,String apkName){
         OSS ossClient =  new OSSClientBuilder().build(endpoint,accessKeyId,
                accessKeySecret);

        System.out.println("进入OSS工具");

        if(file == null){
            return null;
        }
        String dateStr = sdf.format(new Date());


        try{
            //容器不存在就创建
            if(!ossClient.doesBucketExist(bucketName)){
                ossClient.createBucket(bucketName);
                CreateBucketRequest cbr = new
                        CreateBucketRequest(bucketName);
                cbr.setCannedACL(
                        CannedAccessControlList.Private
                );
                ossClient.createBucket(cbr);
            }
            //上传文件

            InputStream is = new FileInputStream(file);
            PutObjectResult result = ossClient.putObject(bucketName,apkName,file);
            //System.out.println(result.getRequestId());
           // String uri = result.getResponse().getUri();
            // 设置这个文件地址的有效时间
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);

            String url = ossClient.generatePresignedUrl(bucketName, apkName, expiration).toString();

            System.out.println("object:"+apkName+"存入成功");
            System.out.println("上传路径："+url);
            url = url.substring(0,url.indexOf("?"));
            return url;
        }catch (OSSException oe){
            oe.printStackTrace();
        }catch (ClientException | FileNotFoundException ce){
            ce.printStackTrace();
        }finally{
            ossClient.shutdown();
        }
        return null;
    }
//    public static void download(String apkName, HttpServletRequest req, HttpServletResponse resp) throws IOException {
//                    //设置文件内容
//            resp.setContentType("application/vnd.android.package-archive");
//            //下载显示的文件名
//            String apk_name = apkName;
//            //下载显示文件名解决中文乱码问题
//            //在下载之前首先判断是否是微软浏览器，如果是，用UTF-8编码,
//            //如果不是，用万能解码
//            //这样就可以在IE8-11，EDGE,FIXBO一级Chrome浏览器下载文件时，不会中文乱码了
//            boolean isMSIE = HttpUtils.isMSBrowser(req);
//            //如果是IE浏览器
//            if(isMSIE){
//                apk_name = URLEncoder.encode(apk_name,"UTF-8");
//            }else{
//                //其他浏览器
//                apk_name = new String(apk_name.getBytes("UTF-8"),"ISO-8859-1");
//            }
//            //设置response的Content-disposition时，apk_name的值要加上双引号如果不佳双引号
//            //在火狐下载数据时，如果文件名是英文加中文的组合，那么火狐在下载时，文件名只有英文部分
//            //只有加了双引号之后，文件名和代码设置的文件名一致，因为这个双引号是在字符串中的，所以需
//            //要加反斜杠来转义
//
//            resp.setHeader("Content-disposition","attachment;filename:"+apk_name);
//            //设置文件大小，这样就可以在下载时，显示文件的大小
//            //resp.setContentLength(Integer.parseInt("7629672"));
//            //读取要下载的文件保存到文件输入流
//            OSS ossClient =  new OSSClientBuilder().build("oss-cn-shenzhen.aliyuncs.com","LTAI4Fn8YhRW2FkbpucSR5AX","yuYPFquHRK3UHHKq3YlV0MBUaWjLdC");
//            OSSObject ossObject = ossClient.getObject("hcnet2006-file-apk",apkName);
//
//            System.out.println("Object content:");
//            InputStream is = ossObject.getObjectContent();
//        BufferedInputStream in = new BufferedInputStream(is);
//            //InputStream in = new FileInputStream(apk_url);
//            //创建文件输出流
//            OutputStream out = resp.getOutputStream();
//            //创建缓冲区
//            byte buffer[] = new byte[1024];
//            int len = 0;
//            while((len = in.read(buffer)) > 0){
//                out.write(buffer,0,len);
//            }
//            //关闭输入流
//            in.close();
//            //关闭输出流
//            out.close();
//            //关闭OSSClient;
//            ossClient.shutdown();
//    }

}
