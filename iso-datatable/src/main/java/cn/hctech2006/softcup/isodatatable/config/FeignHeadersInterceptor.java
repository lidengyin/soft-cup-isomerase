package cn.hctech2006.softcup.isodatatable.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Configuration
public class FeignHeadersInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = getHttpServletRequest();
        if(Objects.isNull(request)){
            return;
        }
        Map<String,String> headers = getHeaders(request);
        if(headers.size() > 0){
            Iterator<Map.Entry<String,String>> iterator = headers.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, String> entry = iterator.next();
                //把原来的head请求头,原样设置到feign请求头中
                //包括token
                requestTemplate.header(entry.getKey(), entry.getValue());
            }
        }
    }
    private HttpServletRequest getHttpServletRequest(){
        try{
            return ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
        }catch (Exception e){
            return null;
        }
    }
    private Map<String, String> getHeaders(HttpServletRequest request){
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enums = request.getHeaderNames();
        while(enums.hasMoreElements()){
            String key = enums.nextElement();
            String value = request.getHeader(key);
            map.put(key,value);
        }
        return map;
    }
}
