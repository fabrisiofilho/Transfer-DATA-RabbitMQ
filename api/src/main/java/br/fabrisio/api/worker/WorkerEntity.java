package br.fabrisio.api.worker;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

	
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "worker")
public class WorkerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    private UUID id;
    	
    @Column(name = "name")
    private String name;
    	
    @Column(name = "last_name")
    private String lastName;
    	
    @Column(name = "cpf")
    private String cpf;
    	
    @Column(name = "active")
    private boolean active;
    	
}
