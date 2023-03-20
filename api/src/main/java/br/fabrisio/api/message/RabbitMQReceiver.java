package br.fabrisio.api.message;

import br.fabrisio.api.worker.WorkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
@Slf4j
public class RabbitMQReceiver {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private RabbitMQSender rabbitMQSender;


    @RabbitListener(queues = "service.one.queue.direct")
    public void receivedMessageDirect(RabbitMessage rabbitMessage) {
        logginInfo(rabbitMessage);
    }

    private void logginInfo(RabbitMessage rabbitMessage) {
        log.info("Recebendo dados de integração" +
                " - Quantidade: " +
                rabbitMessage.getAmount() + "/" + rabbitMessage.getTotal());
        workerRepository.saveAll(rabbitMessage.getWorkers());

        rabbitMQSender.sendExchangeDirectMessageByServiceTwo(RabbitMessage
                .builder()
                .service("catraca")
                .code(UUID.randomUUID())
                .amount(rabbitMessage.getAmount())
                .total(rabbitMessage.getTotal())
                .description("Sincronizando trabalhadores")
                .workers(rabbitMessage.getWorkers())
                .build());
    }

}