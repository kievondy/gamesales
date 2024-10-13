package com.company.gamesales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.gamesales.model.ImportTracking;

public interface ImportTrackingRepository extends JpaRepository<ImportTracking, Long> {

}
