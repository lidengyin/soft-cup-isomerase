package cn.hctech2006.softcup.isomerase3.controller;//package cn.hctech2006.softcup.isomerase.controller;
//
//import io.swagger.annotations.Api;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Api(tags = "WebSocket控制器")
//@RequestMapping("/websocket")
//@RestController
//public class WebSocketController {
//
//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public Message greeting(Message message){
//        return message;
//    }
//    class Message{
//        private String name;
//        private String content;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public void setContent(String content) {
//            this.content = content;
//        }
//    }
//}
