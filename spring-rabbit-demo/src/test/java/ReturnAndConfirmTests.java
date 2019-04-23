import com.alibaba.fastjson.JSONObject;
import mq.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Author:Created by wx on 2019/4/17
 * Desc:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-rabbitmq.xml"})
public class ReturnAndConfirmTests {

    @Autowired
    private Producer producer;

    // 交换机
    private static String exChange = "DIRECT_EX";

    /**
     * 消息发送和消息消费都正常
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        String message = "currentTime:" + System.currentTimeMillis();
        System.out.println("test1---message:" + message);
        //exchange,queue 都正确,confirm被回调, ack=true
        producer.send(exChange, "CONFIRM_TEST", message);
        Thread.sleep(1000);
    }

    /**
     * producer--F-->exChange
     * ConfirmCallBack被回调
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        String message = "currentTime:" + System.currentTimeMillis();
        System.out.println("test2---message:" + message);
        //exchange 错误,queue 正确,confirm被回调, ack=false
        producer.send(exChange + "NO", "CONFIRM_TEST", message);
        Thread.sleep(1000);
    }

    /**
     * producer--T-->exChange--F-->queue
     * 触发ConfirmCallBack
     * 触发ReturnCallBack
     * @throws InterruptedException
     */
    @Test
    public void test3() throws InterruptedException {
        String message = "currentTime:" + System.currentTimeMillis();
        System.out.println("test3---message:" + message);
        //exchange 正确,queue 错误 ,confirm被回调, ack=true; return被回调 replyText:NO_ROUTE
        producer.send(exChange, "", message);
//        Thread.sleep(1000);
    }

    /**
     * 全错，只有Confirm被回调
     * @throws InterruptedException
     */
    @Test
    public void test4() throws InterruptedException {
        String message = "currentTime:" + System.currentTimeMillis();
        System.out.println("test4---message:" + message);
        //exchange 错误,queue 错误,confirm被回调, ack=false
        producer.send(exChange + "NO", "", message);
        Thread.sleep(1000);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test5() throws UnsupportedEncodingException {
        Map map = new HashMap();

        map.put("id",3);
        map.put("name","咖妃噶");
        String json = JSONObject.toJSONString(map);
        String msgId = UUID.randomUUID().toString();
        // 设置消息
        Message message = MessageBuilder.withBody(json.getBytes(StandardCharsets.UTF_8)).setContentType(MessageProperties.CONTENT_TYPE_JSON).setCorrelationId(msgId).build();
        // 绑定msgId
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(exChange,"CONFIRM_TEST",message,correlationData);
    }
}

