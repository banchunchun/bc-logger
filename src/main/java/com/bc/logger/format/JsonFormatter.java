package com.bc.logger.format;

import ch.qos.logback.classic.spi.ILoggingEvent;

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

    public JsonFormatter() {
    }

    public String format(ILoggingEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        fieldName("level", sb);
        quote(event.getLevel().levelStr, sb);
        sb.append(",");
        fieldName("logger", sb);
        quote(event.getLoggerName(), sb);
        sb.append(",");
        fieldName("timestamp", sb);
        sb.append(event.getTimeStamp());
        sb.append(",");
        fieldName("message", sb);
        if (this.expectJson) {
            sb.append(event.getFormattedMessage());
        } else {
            quote(event.getFormattedMessage(), sb);
        }

        sb.append("}");
        return sb.toString();
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
}
