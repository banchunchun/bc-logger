package com.bc.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-12
 * Time:  下午 7:48.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class Slf4jLogImpl extends AbstractLogImpl{

    private Logger logger;

    /** 错误日志 */
    private Logger errorLogger = LoggerFactory.getLogger("errorLogger");

    public Slf4jLogImpl(String clazz, String businessModule) {
        logger = LoggerFactory.getLogger(clazz);
        setFlag(businessModule);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void error(Object message, Throwable e) {
        String msg = warpMessage(message);
        logger.error(msg, e);
        errorLogger.error(msg, e);
    }

    @Override
    public void error(Object message) {
        String msg = warpMessage(message);
        if (message instanceof Throwable) {
            logger.error(msg, (Throwable) message);
            errorLogger.error(msg, (Throwable) message);
        } else {
            logger.error(msg);
            errorLogger.error(msg);
        }
    }

    @Override
    public void debug(Object message) {
        logger.debug(warpMessage(message));
    }


    @Override
    public void info(Object message) {
        logger.info(warpMessage(message));
    }

    @Override
    public void warn(Object message) {
        logger.warn(warpMessage(message));
    }

    @Override
    public void warn(Object message, Throwable e) {
        logger.warn(warpMessage(message), e);
    }

    @Override
    public void debug(Object message, Throwable e) {
        logger.debug(warpMessage(message), e);
    }


    @Override
    public void info(Object message, Throwable e) {
        logger.info(warpMessage(message), e);
    }


    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void debug(String var1, Object... var2) {
        logger.debug(warpMessage(var1), var2);
    }

    @Override
    public void info(String var1, Object... var2) {
        logger.info(warpMessage(var1), var2);
    }

    @Override
    public void warn(String var1, Object... var2) {
        logger.warn(warpMessage(var1), var2);
    }

    @Override
    public void error(String var1, Object... var2) {
        logger.error(warpMessage(var1), var2);
    }
}
