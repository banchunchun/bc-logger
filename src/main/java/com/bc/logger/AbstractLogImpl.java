package com.bc.logger;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-12
 * Time:  下午 7:47.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractLogImpl implements ILog{

    private String flag;

    protected String warpMessage(Object message) {
        if (null == message) {
            return "";
        }
        String flag = getFlag();
        if (flag == null || flag.isEmpty()) {
            return message.toString();
        }
        return message + ",Flag=" + flag;
    }

    /**
     * Setter method for property <tt>flag</tt>.
     *
     * @param value value to be assigned to property flag
     */
    public void setFlag(String value) {
        this.flag = value;
    }

    /**
     * get method for property <tt>flag</tt>.
     */
    public String getFlag() {
        return flag;
    }
}
