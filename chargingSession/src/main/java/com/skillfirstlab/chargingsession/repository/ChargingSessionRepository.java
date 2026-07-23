package com.skillfirstlab.chargingsession.repository;



import com.skillfirstlab.chargingsession.entity.ChargingSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargingSessionRepository extends CrudRepository<ChargingSession, Long> {
}
