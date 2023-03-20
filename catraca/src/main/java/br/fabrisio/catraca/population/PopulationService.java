package br.fabrisio.catraca.population;

import br.fabrisio.catraca.message.RabbitMQSender;
import br.fabrisio.catraca.message.RabbitMessage;
import br.fabrisio.catraca.worker.WorkerEntity;
import br.fabrisio.catraca.worker.WorkerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PopulationService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @PostConstruct
    public void pupulation() {
        for (int i = 0; i < 10000; i++) {
            workerRepository
                    .save(WorkerEntity.builder()
                    .id(UUID.randomUUID())
                    .name("Worker")
                    .lastName(UUID.randomUUID().toString())
                    .cpf("11111111")
                    .active(true).integrated(false)
                    .build());
        }
    }

    public void integration(RabbitMessage rabbitMessage) {
        var workers = workerRepository.findByWorkerToIntegration();

        Long totalWorkers = null;

        if (rabbitMessage.getTotal() == null) {
            totalWorkers = workerRepository.findCountTotalWorkers();
        } else {
            totalWorkers = rabbitMessage.getTotal();
        }

        if (!workers.isEmpty()) {
            rabbitMQSender.sendExchangeDirectMessageByServiceTwo(RabbitMessage
                    .builder()
                    .service("catraca")
                    .code(UUID.randomUUID())
                    .amount(workers.size() + rabbitMessage.getAmount())
                    .total(totalWorkers)
                    .description("Sincronizando trabalhadores")
                    .workers(workers)
                    .build());
        }
    }

}
