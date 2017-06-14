package com.bc.logger.format;

import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.google.gson.Gson;

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
public class JsonFormatter implements Formatter {

    private static final String QUOTE = "\"";
    private static final String COLON = ":";
    private static final String COMMA = ",";
    private boolean expectJson = false;

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

    public JsonFormatter() {
    }

    public String format(ILoggingEvent le) {

        LogObject log = new LogObject();
        log.setBusiness(businessName);
        log.setIp(localIp);
        log.setHostName(hostName);
        log.setTime(getTime(le));
        log.setLeave(le.getLevel().toString());
        log.setClassName(getFullyQualifiedName(le));
        log.setMethodName(getMethodName(le));
        log.setLine(getLineNumber(le));
        log.setMessage(le.getFormattedMessage());
        return new Gson().toJson(log);
    }

    private static void fieldName(String name, StringBuilder sb) {
        quote(name, sb);
        sb.append(":");
    }

    private static void quote(String value, StringBuilder sb) {
        sb.append("\"");
        sb.append(value);
        sb.append("\"");
    }

    public boolean isExpectJson() {
        return this.expectJson;
    }

    public void setExpectJson(boolean expectJson) {
        this.expectJson = expectJson;

    }

    public void start() {

        businessName = businessName == null ? "未设置产品线" : businessName;
//        String datePattern = "yyyy-MM-dd HH:mm:ss";
//        try {
//            simpleFormat = new SimpleDateFormat(datePattern);
//            // maximumCacheValidity =
//            // CachedDateFormat.getMaximumCacheValidity(pattern);
//        } catch (IllegalArgumentException e) {
//            addWarn("Could not instantiate SimpleDateFormat with pattern " + datePattern, e);
//            // default to the ISO8601 format
//            simpleFormat = new SimpleDateFormat(CoreConstants.ISO8601_PATTERN);
//        }
//        List optionList = getOptionList();
//        // if the option list contains a TZ option, then set it.
//        if (optionList != null && optionList.size() > 1) {
//            TimeZone tz = TimeZone.getTimeZone((String) optionList.get(1));
//            simpleFormat.setTimeZone(tz);
//        }
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
