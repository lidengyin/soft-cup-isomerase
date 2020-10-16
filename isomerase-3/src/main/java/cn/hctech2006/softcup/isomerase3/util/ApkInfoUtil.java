package cn.hctech2006.softcup.isomerase3.util;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.IconFace;
import net.dongliu.apk.parser.bean.UseFeature;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApkInfoUtil {
    public static Map<String, Object> readAPK(File apkUrl) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try (ApkFile apkFile = new ApkFile(apkUrl)) {
            ApkMeta apkMeta = apkFile.getApkMeta();
            resMap.put("name", apkMeta.getName());
            resMap.put("pkName", apkMeta.getPackageName());
            resMap.put("vName", apkMeta.getVersionName());
            resMap.put("vCode", apkMeta.getVersionCode());
            List<IconFace> iconFaces = apkFile.getAllIcons();
            //iconFaces.get(1).
            System.out.println("名称:"+apkMeta.getName());
            System.out.println("包名:"+apkMeta.getPackageName());
            System.out.println("版本名:"+apkMeta.getVersionName());
            System.out.println("版本号:"+apkMeta.getVersionCode());


            for (UseFeature feature : apkMeta.getUsesFeatures()) {
                System.out.println(feature.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resMap;

    }
}

