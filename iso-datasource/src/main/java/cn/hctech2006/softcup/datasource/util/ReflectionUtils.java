package cn.hctech2006.softcup.datasource.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射相关辅助方法
 * @author Louis
 * @date Aug 19, 2018
 */
public class ReflectionUtils {


    /**
     * 根据方法名调用指定对象的方法
     * @param object 要调用方法的对象
     * @param method 要调用的方法名
     * @param args 参数对象数组
     * @return
     */
    public static Object invoke(Object object, String method, Object... args) {
        Object result = null;
        Class<? extends Object> clazz = object.getClass();
        System.out.println("class:"+clazz.getName());
        System.out.println("method:"+method);
        System.out.println("args:"+args.toString());
        Method queryMethod = getMethod(clazz, method, args);
        if(queryMethod != null) {
            try {
                result = queryMethod.invoke(object, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("method is null");
            try {
                throw new NoSuchMethodException(clazz.getName() + " 类中没有找到 " + method + " 方法。");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据方法名和参数对象查找方法
     * @param clazz
     * @param name
     * @param args 参数实例数据
     * @return
     */
    public static Method getMethod(Class<? extends Object> clazz, String name, Object[] args) {
        Method queryMethod = null;
        System.out.println("the method we are find");
        System.out.println("see all method");
        Method[] methods = clazz.getMethods();
        for(Method method:methods) {
            System.out.println("method:"+method.getName());
            if(method.getName().equals(name)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                System.out.println("the ards length we are find is:"+args.length);
                System.out.println("the ards length will be compared:"+parameterTypes.length);

                if(parameterTypes.length == args.length) {
                    boolean isSameMethod = true;
                    for(int i=0; i<parameterTypes.length; i++) {
                        Object arg = args[i];
                        if(arg == null) {
                            arg = "";
                        }
                        System.out.println("type of the method we are find:"+args[i].getClass().toString());
                        System.out.println("type of the method will be compared:"+parameterTypes[i].toString());
                        if(!parameterTypes[i].equals(args[i].getClass())) {
                            isSameMethod = false;
                            System.out.println("类型不一样");
                        }
                    }
                    if(isSameMethod) {
                        queryMethod = method;
                        break ;
                    }
                }
            }
        }
        return queryMethod;
    }
}