package com.bc.logger;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 重写Converter类，打印自己想要的格式
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-13
 * Time:  下午 3:36.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class LogConvert extends ClassicConverter {

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

    @Override
    public String convert(ILoggingEvent le) {
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

    public void start() {
        businessName = getFirstOption();
        businessName = businessName == null ? "未设置产品线" : businessName;
        String datePattern = "yyyy-MM-dd HH:mm:ss";
        try {
            simpleFormat = new SimpleDateFormat(datePattern);
            // maximumCacheValidity =
            // CachedDateFormat.getMaximumCacheValidity(pattern);
        } catch (IllegalArgumentException e) {
            addWarn("Could not instantiate SimpleDateFormat with pattern " + datePattern, e);
            // default to the ISO8601 format
            simpleFormat = new SimpleDateFormat(CoreConstants.ISO8601_PATTERN);
        }
        List optionList = getOptionList();
        // if the option list contains a TZ option, then set it.
        if (optionList != null && optionList.size() > 1) {
            TimeZone tz = TimeZone.getTimeZone((String) optionList.get(1));
            simpleFormat.setTimeZone(tz);
        }
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
