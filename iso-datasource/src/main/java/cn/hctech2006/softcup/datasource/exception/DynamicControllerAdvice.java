package cn.hctech2006.softcup.datasource.exception;

/**
 * 定义控制器通知来处理异常
 */
//@ControllerAdvice(
//        //指定拦截的控制器
//        basePackages = {"cn.hctech2006.softcup.isomerase.controller.*"}
//        //限定被标注为@Controller和@RestController的类才会被拦截
//        ,annotations = {Controller.class, RestController.class}
//)
//public class DynamicControllerAdvice {
//    private Logger logger = LoggerFactory.getLogger(DynamicControllerAdvice.class);
//    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    //异常处理可以定义异常类型来进行拦截处理
//    @ExceptionHandler(value = Exception.class)
//    //以JSON方式响应
//    @ResponseBody
//    //定义为服务器错误状态码
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ServerResponse exception(HttpServletRequest request
//            , Exception ex){
//        //获取异常信息
//        return ServerResponse.createByError(sdf.format(new Date())+"--"+ex.getLocalizedMessage());
//    }
//
//}
