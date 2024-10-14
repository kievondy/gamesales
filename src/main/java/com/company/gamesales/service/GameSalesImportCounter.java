package com.company.gamesales.service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.gamesales.model.ImportStatus;
import com.company.gamesales.model.ImportTracking;
import com.company.gamesales.repository.ImportTrackingRepository;

public class GameSalesImportCounter {

	private static final Logger logger = LoggerFactory.getLogger(GameSalesImportCounter.class);

	private final ImportTrackingRepository importTrackingRepository;
	private final ImportTracking importTracking;
	private final int target;
	private final AtomicInteger currentCount = new AtomicInteger(0);
	private final AtomicBoolean started = new AtomicBoolean(false);

	public GameSalesImportCounter(ImportTrackingRepository importTrackingRepository, ImportTracking importTracking,
			int target) {
		this.importTrackingRepository = importTrackingRepository;
		this.importTracking = importTracking;
		this.target = target;
	}

	public void addCount(int count) {
		this.currentCount.getAndAdd(count);
		logger.info("Record imported {}", currentCount.get());
		if (!started.get()) {
			started.getAndSet(true);
			updateTrackingIncomplete();
		}
		if (currentCount.get() >= target) {
			updateTrackingSuccessful();
		}
	}

	public AtomicInteger getCurrentCount() {
		return currentCount;
	}

	public int getTarget() {
		return target;
	}

	private ImportTracking updateTrackingSuccessful() {
		logger.info("All records imported, updating ImportTracking status to Successful");
		importTracking.setImportStatus(ImportStatus.IMPORT_SUCCESSFUL);
		return importTrackingRepository.save(importTracking);
	}

	private ImportTracking updateTrackingIncomplete() {
		logger.info("Async import started, updating ImportTracking status to Incomplete");
		importTracking.setImportStatus(ImportStatus.IMPORT_INCOMPLETE);
		return importTrackingRepository.save(importTracking);
	}

}
