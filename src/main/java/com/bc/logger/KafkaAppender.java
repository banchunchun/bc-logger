package com.bc.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;


import ch.qos.logback.core.Layout;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import com.bc.logger.format.Formatter;
import com.bc.logger.format.JsonFormatter;
import com.bc.logger.format.MessageFormatter;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;


import java.util.Properties;

import static ch.qos.logback.core.CoreConstants.CODES_URL;


/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-12
 * Time:  下午 7:43.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class KafkaAppender extends AppenderBase<ILoggingEvent> {
    private String topic;
    private String brokerList;
    private Producer<String, String> producer;
    private Formatter formatter;

    private Layout layout;

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public KafkaAppender() {
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBrokerList() {
        return brokerList;
    }

    public void setBrokerList(String brokerList) {
        this.brokerList = brokerList;
    }

    public void start() {
        if (formatter == null)
            this.formatter = new MessageFormatter();
        super.start();
        Properties props = new Properties();
        props.put("metadata.broker.list", this.brokerList);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        ProducerConfig config = new ProducerConfig(props);
        LayoutWrappingEncoder<?> lwe = new LayoutWrappingEncoder<>();
        lwe.setLayout(this.layout);
        lwe.setContext(context);
        this.producer = new Producer(config);
    }



    public void stop() {
        super.stop();
        this.producer.close();
    }

    protected void append(ILoggingEvent event) {
        String payLoad = this.formatter.format(event);
        KeyedMessage<String, String> data = new KeyedMessage<>(topic, payLoad);
        this.producer.send(data);
    }


}
