package com.bc.logger;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-12
 * Time:  下午 8:00.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public enum  LogBusinessModule {

    /** 订单 */
    ORDER_LOG(0),
    /** trace 日志 */
    TRACE_LOG(100)
    ;
    public Integer no;
    private LogBusinessModule(Integer code) {
        this.no = no;
    }
}
