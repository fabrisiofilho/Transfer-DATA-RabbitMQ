package br.fabrisio.api.message;

import br.fabrisio.api.worker.WorkerEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = RabbitMessage.class)
public class RabbitMessage implements Serializable {
    private UUID code;
    private String service;
    private String description;
    private Integer amount;
    private Long total;
    private List<WorkerEntity> workers;
}