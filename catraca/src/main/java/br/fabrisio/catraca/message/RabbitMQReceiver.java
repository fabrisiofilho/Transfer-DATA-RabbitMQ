package br.fabrisio.catraca.message;

import br.fabrisio.catraca.population.PopulationService;
import br.fabrisio.catraca.worker.WorkerEntity;
import br.fabrisio.catraca.worker.WorkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQReceiver {

    @Autowired
    private PopulationService populationService;

    @Autowired
    private WorkerRepository workerRepository;


    @RabbitListener(queues = "service.one.queue.direct")
    public void receivedMessageDirect(RabbitMessage rabbitMessage) {
        logginInfo(rabbitMessage);
    }

    private void logginInfo(RabbitMessage rabbitMessage) {
        log.info("Enviando dados de integração" +
                " - Quantidade: " +
                rabbitMessage.getAmount() + "/" + rabbitMessage.getTotal());

        workerRepository.setIntegration(rabbitMessage.getWorkers().stream().map(WorkerEntity::getId).toList());
        populationService.integration(rabbitMessage);
    }

}