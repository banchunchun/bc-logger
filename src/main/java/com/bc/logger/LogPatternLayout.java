package com.bc.logger;

import ch.qos.logback.classic.PatternLayout;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-13
 * Time:  下午 3:44.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class LogPatternLayout extends PatternLayout {
    static {
        defaultConverterMap.put("pattern",LogConvert.class.getName());
    }
}
