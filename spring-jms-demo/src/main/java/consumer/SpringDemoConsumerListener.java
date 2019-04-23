package consumer;

import com.alibaba.fastjson.JSONObject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SpringDemoConsumerListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            Object parse = JSONObject.parse(textMessage.getText());
            System.out.println(parse.toString());
            System.out.println("接收到消息");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
