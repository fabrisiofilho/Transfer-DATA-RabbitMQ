package br.fabrisio.catraca.worker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkerRepository extends JpaRepository<WorkerEntity, UUID>  { 

    @Query(value = "SELECT * FROM worker as w WHERE w.integrated = false LIMIT 1000", nativeQuery = true)
    List<WorkerEntity> findByWorkerToIntegration();

    @Transactional
    @Modifying
    @Query("UPDATE WorkerEntity w SET w.integrated = true WHERE w.id IN (:workerEntities)")
    void setIntegration(List<UUID> workerEntities);

}
