package com.bc.logger.format;

import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-12
 * Time:  下午 7:45.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class MessageFormatter implements Formatter {
    private static final String QUOTE = "|";
    static String hostName;
    static String localIp;
    long lastTimestamp = -1;
    String timestampStrCache = null;
    SimpleDateFormat simpleFormat = null;

    String businessName = null;

    static {
        InetAddress ia = null;
        try {
            ia = ia.getLocalHost();
            hostName = ia.getHostName();
            localIp = ia.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public String format(ILoggingEvent event) {
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("[");
//        stringBuffer.append(event.getThreadName());
//        stringBuffer.append("] ");
//        stringBuffer.append(event.getLoggerName());
//        stringBuffer.append(" - ");
//        stringBuffer.append("[");
//        stringBuffer.append(event.getLevel());
//        stringBuffer.append("] ");
//        stringBuffer.append(event.getFormattedMessage());
//        return stringBuffer.toString();
//    }


    @Override
    public String format(ILoggingEvent le) {
//        LogObject log = new LogObject();
        StringBuilder builder = new StringBuilder();
        builder.append(le.getMdc().get("requestId"));
        builder.append(QUOTE);
        builder.append(localIp);
        builder.append(QUOTE);
        builder.append(hostName);
        builder.append(QUOTE);
        builder.append(le.getThreadName());
        builder.append(QUOTE);
        builder.append(le.getLoggerName());
        builder.append(QUOTE);
        builder.append(getTime(le));
        builder.append(QUOTE);
        builder.append(le.getLevel().toString());
        builder.append(QUOTE);
        builder.append(getFullyQualifiedName(le));
        builder.append(QUOTE);
        builder.append(getMethodName(le));
        builder.append(QUOTE);
        builder.append(getLineNumber(le));
        builder.append(QUOTE);
        builder.append(le.getFormattedMessage());

//        log.setBusiness(businessName);
//        log.setIp(localIp);
//        log.setHostName(hostName);
//        log.setTime(getTime(le));
//        log.setLeave(le.getLevel().toString());
//        log.setClassName(getFullyQualifiedName(le));
//        log.setMethodName(getMethodName(le));
//        log.setLine(getLineNumber(le));
//        log.setMessage(le.getFormattedMessage());
        return builder.toString();
    }

    private String getTime(ILoggingEvent le) {
        long timestamp = le.getTimeStamp();
        synchronized (this) {
            // if called multiple times within the same millisecond
            // return cache value
            if (timestamp == lastTimestamp) {
                return timestampStrCache;
            } else {
                lastTimestamp = timestamp;
                // SimpleDateFormat is not thread safe.
                // See also http://jira.qos.ch/browse/LBCLASSIC-36
                timestampStrCache = simpleFormat.format(new Date(timestamp));
                return timestampStrCache;
            }
        }
    }

    private String getFullyQualifiedName(ILoggingEvent le) {

        StackTraceElement[] cda = le.getCallerData();
        if (cda != null && cda.length > 0) {
            return cda[0].getClassName();
        } else {
            return CallerData.NA;
        }
    }

    private String getLineNumber(ILoggingEvent le) {
        StackTraceElement[] cda = le.getCallerData();
        if (cda != null && cda.length > 0) {
            return Integer.toString(cda[0].getLineNumber());
        } else {
            return CallerData.NA;
        }
    }

    private String getMethodName(ILoggingEvent le) {
        StackTraceElement[] cda = le.getCallerData();
        if (cda != null && cda.length > 0) {
            return cda[0].getMethodName();
        } else {
            return CallerData.NA;
        }
    }

    public class LogObject {
        /**
         * 产品线
         */
        private String business;
        /**
         * 主机名
         */
        private String hostName;
        /**
         * IP
         */
        private String ip;
        /**
         * 时间
         */
        private String time;
        /**
         * 日志级别
         */
        private String leave;
        /**
         * 类名
         */
        private String className;
        /**
         * 方法名
         */
        private String methodName;
        /**
         * 行数
         */
        private String line;
        /**
         * 日志内容
         */
        private String message;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getLeave() {
            return leave;
        }

        public void setLeave(String leave) {
            this.leave = leave;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getHostName() {
            return hostName;
        }

        public void setHostName(String hostName) {
            this.hostName = hostName;
        }

    }

}
