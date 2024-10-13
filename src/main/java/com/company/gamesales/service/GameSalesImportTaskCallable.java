package com.company.gamesales.service;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.gamesales.model.GameSales;
import com.company.gamesales.repository.GameSalesRepository;

public class GameSalesImportTaskCallable implements Callable<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(GameSalesImportTaskCallable.class);

	private final GameSalesRepository gameSalesRepository;

	private final List<GameSales> gameSales;

	public GameSalesImportTaskCallable(GameSalesRepository gameSalesRepository, List<GameSales> gameSales) {
		this.gameSalesRepository = gameSalesRepository;
		this.gameSales = gameSales;
	}

	@Override
	public Integer call() throws Exception {
		try {
			int size = gameSalesRepository.saveAll(gameSales).size();
			return size;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

}
