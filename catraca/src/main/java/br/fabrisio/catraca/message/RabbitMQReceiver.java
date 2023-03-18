package br.fabrisio.catraca.message;

import br.fabrisio.catraca.population.PopulationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQReceiver {

    @Autowired
    private PopulationService populationService;

    @RabbitListener(queues = "service.one.queue.direct")
    public void receivedMessageDirect(RabbitMessage rabbitMessage) {
        logginInfo(rabbitMessage);
    }

    private void logginInfo(RabbitMessage rabbitMessage) {
        log.info("Service One - Recieved Message From RabbitMQ: By "
                + rabbitMessage.getService() + " Exchange type: "
                + rabbitMessage.getDescription() + " { CODE: "+ rabbitMessage.getCode() + " }");
        populationService.integration();
    }

}