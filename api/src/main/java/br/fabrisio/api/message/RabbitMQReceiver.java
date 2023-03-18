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
        log.info("Service One - Recieved Message From RabbitMQ: By "
                + rabbitMessage.getService() + " Exchange type: "
                + rabbitMessage.getDescription() + " { CODE: "+ rabbitMessage.getCode() + " }");
        workerRepository.saveAll(rabbitMessage.getWorkers());

        rabbitMQSender.sendExchangeDirectMessageByServiceTwo(RabbitMessage
                .builder()
                .service("catraca")
                .code(UUID.randomUUID())
                .description("Sincronizando trabalhadores")
                .workers(new ArrayList<>())
                .build());
    }

}