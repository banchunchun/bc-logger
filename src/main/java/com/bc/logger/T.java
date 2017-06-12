package com.bc.logger;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-12
 * Time:  下午 7:56.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class T {
   static ILog log = LogFactory.getLog(T.class,LogBusinessModule.ORDER_LOG);
    public static void main(String[] args) {
        for (int i = 0 ; i < 100 ; i ++){
           log.info(i);
        }
    }
}
