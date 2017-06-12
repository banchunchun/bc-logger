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
public interface Formatter {

    String format(ILoggingEvent var1);
}
