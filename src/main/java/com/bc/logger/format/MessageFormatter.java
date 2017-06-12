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
public class MessageFormatter implements Formatter {

    @Override
    public String format(ILoggingEvent event) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append(event.getThreadName());
        stringBuffer.append("] ");
        stringBuffer.append(event.getLoggerName());
        stringBuffer.append(" - ");
        stringBuffer.append("[");
        stringBuffer.append(event.getLevel());
        stringBuffer.append("] ");
        stringBuffer.append(event.getFormattedMessage());
        return stringBuffer.toString();
    }

}
