package com.skillfirstlab.chargingstationservice.repository;

import com.skillfirstlab.chargingstationservice.entity.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ChargingStationRepository
        extends CrudRepository<ChargingStation, Long> {

}