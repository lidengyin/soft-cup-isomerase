package cn.hctech2006.softcup.isomerase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import javax.annotation.PostConstruct;

//开启服务熔断
@EnableHystrix
//开启声明式服务消费客户端
@EnableFeignClients
//开启服务发现
@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@MapperScan("cn.hctech2006.softcup.isomerase.mapper")
public class IsomeraseApplication {

    public static void main(String[] args) {

        SpringApplication.run(IsomeraseApplication.class, args);

    }

    @Autowired
    private RedisTemplate redisTemplate = null;

    @PostConstruct
    public void init(){
        initRedisTemplate();
    }

    //设置redisTemplate的序列化器
    private void initRedisTemplate(){
        //redis默认定义了一个stringSerializer对象
        RedisSerializer stringRedisSerializer = redisTemplate.getStringSerializer();
        //吧关于键和其散列数据类型的field修改为使用StringRedisSerializer进行序列化
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //redisTemplate.setValueSerializer(stringRedisSerializer);

    }

}
