package br.fabrisio.catraca.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendExchangeDirectMessageByServiceTwo(RabbitMessage rabbitMessage) {
        rabbitTemplate.convertAndSend("direct.exchange", "api", rabbitMessage);
    }

}