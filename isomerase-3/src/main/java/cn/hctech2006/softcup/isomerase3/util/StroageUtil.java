package cn.hctech2006.softcup.isomerase3.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class StroageUtil {
    public static String  store(MultipartFile file) throws IOException {
        String url = "/usr/local/spring/"+ file.getOriginalFilename();
        File folder = new File(url);
        if (!folder.isDirectory()){
            folder.mkdirs();
        }
        file.transferTo(folder);
        return url;
    }
}
