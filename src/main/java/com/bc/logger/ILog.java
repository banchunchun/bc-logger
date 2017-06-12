package com.bc.logger;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-12
 * Time:  下午 7:42.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public interface ILog {

    /**
     * 日志级别判断
     *
     * @return
     */
    boolean isDebugEnabled();

    /**
     * 日志级别判断
     *
     * @return
     */
    boolean isInfoEnabled();


    boolean isWarnEnabled();
    boolean isErrorEnabled();

    /**
     * 错误日志
     *
     * @param message
     * @param e
     */
    void error(Object message, Throwable e);

    /**
     * 错误日志
     *
     * @param message
     */
    void error(Object message);

    /**
     * debug日志
     *
     * @param message
     */
    void debug(Object message);

    /**
     * debug日志
     *
     * @param message
     * @param e
     */
    void debug(Object message, Throwable e);

    /**
     * info级别的日志
     *
     * @param message
     */
    void info(Object message);

    /**
     * info级别的日志
     *
     * @param message
     * @param e
     */
    void info(Object message, Throwable e);

    /**
     * warn 级别日志
     *
     * @param message
     */
    void warn(Object message);

    /**
     * warn 级别日志
     *
     * @param message
     * @param e
     */
    void warn(Object message, Throwable e);

    void debug(String var1, Object... var2);
    void info(String var1, Object... var2);
    void warn(String var1, Object... var2);
    void error(String var1, Object... var2);
}
