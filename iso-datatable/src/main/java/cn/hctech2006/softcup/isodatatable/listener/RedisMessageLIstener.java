package cn.hctech2006.softcup.isodatatable.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageLIstener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        //消息体
        String body = new String(message.getBody());
        //渠道名称
        String topic = new String(pattern);
        System.out.println(body);
        System.out.println(topic);
    }
    @Autowired
    private RedisConnectionFactory connectionFactory = null;
    @Autowired
    private MessageListener messageListener = null;

    //任务池
    private ThreadPoolTaskScheduler taskScheduler = null;
    /**
     * 创建线程池, 运行线程等待处理Redis消息
     */
    @Bean
    public ThreadPoolTaskScheduler initTashScheduler(){
        if (taskScheduler != null){
            return taskScheduler;
        }
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        return taskScheduler;
    }

    /**
     * 定义redis的监听容器
     * @return
     */
    @Bean
    public RedisMessageListenerContainer initRedisContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        //redis连接工厂
        container.setConnectionFactory(connectionFactory);
        //设置运行任务池
        container.setTaskExecutor(taskScheduler);
        //自定义监听渠道, 名称为
        Topic topic = new ChannelTopic("bgToFg");
        //使用监听器监听Redis的消息
        container.addMessageListener(messageListener, topic);
        return container;
    }

}
