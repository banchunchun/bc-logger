package com.bc.logger;

import java.lang.reflect.Constructor;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-12
 * Time:  下午 7:42.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class LogFactory {

    private static Constructor<? extends ILog> logConstructor;
    private static String IP;

    static {
        tryImplementation(new Runnable() {
            public void run() {
                try {
                    useSlf4jLogging();
                } catch (Exception e) {
                }
            }
        });
//        try {
//            IP = (InetAddress.getLocalHost()).getHostName();
//        } catch (UnknownHostException uhe) {
//            String host = uhe.getMessage(); // host = "hostname: hostname"
//            if (host != null) {
//                int colon = host.indexOf(':');
//                if (colon > 0) {
//                    IP = host.substring(0, colon);
//                }
//            }
//            IP = "UnknownHost";
//        }
//        MDC.put("node", IP);
    }

    private static void tryImplementation(Runnable runnable) {
        if (logConstructor == null) {
            try {
                runnable.run();
            } catch (Exception e) {
            }
        }
    }

    public static synchronized void useSlf4jLogging() throws Exception {
        setImplementation(Slf4jLogImpl.class);
    }


    private static void setImplementation(Class<? extends ILog> implClass) throws Exception {
        try {
            Constructor<? extends ILog> candidate = implClass.getConstructor(new Class[] { String.class, String.class });
            ILog logger = candidate.newInstance(new Object[] { LogFactory.class.getName(), null });
            logger.info("Logging initialized using '" + implClass + "' adapter.\n");
            logConstructor = candidate;
        } catch (Throwable t) {
            throw new LogException("Error setting Log implementation.  Cause: " + t, t);
        }
    }

    // disable construction
    private LogFactory() {
    }

    public static ILog getLog(Class<?> aClass) {
        return getLog(aClass.getName());
    }


    public static ILog getLog(String logger) {
        try {
            return logConstructor.newInstance(new Object[] { logger});
        } catch (Exception e) {
            throw new LogException("Error creating logger for logger " + logger + ".  Cause: " + e, e);
        }
    }
}
