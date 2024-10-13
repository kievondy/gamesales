package com.company.gamesales.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.gamesales.model.GameSales;
import com.company.gamesales.repository.GameSalesRepository;

public class GameSalesImportTaskRunnable implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(GameSalesImportTaskRunnable.class);

	private final GameSalesRepository gameSalesRepository;

	private final List<GameSales> gameSales;

	private final GameSalesImportCounter counter;

	public GameSalesImportTaskRunnable(GameSalesRepository gameSalesRepository, List<GameSales> gameSales,
			GameSalesImportCounter counter) {
		this.gameSalesRepository = gameSalesRepository;
		this.gameSales = gameSales;
		this.counter = counter;
	}

	@Override
	public void run() {
		try {
			int importedCount = gameSalesRepository.saveAll(gameSales).size();
			this.counter.addCount(importedCount);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
