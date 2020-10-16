package cn.hctech2006.softcup.datasource.datasource;

/**
 * 数据源切换类
 */
public class DBContextHolder {
    /**
     * 对当前线程的操作, 线程安全的
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 调用此方法切换数据源
     * @param dataSource
     */
    public static void setDataSource(String dataSource){

        contextHolder.set(dataSource);
    }

    /**
     * 获取数据源
     * @return
     */
    public static String getDataSource(){
        return contextHolder.get();
    }

    /**
     * 删除数据源
     */
    public static void clearDataSource(){
        contextHolder.remove();
    }
}
