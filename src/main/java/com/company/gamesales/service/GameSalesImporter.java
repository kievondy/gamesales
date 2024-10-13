package com.company.gamesales.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.company.gamesales.model.GameSales;
import com.company.gamesales.model.ImportStatus;
import com.company.gamesales.model.ImportTracking;
import com.company.gamesales.repository.GameSalesRepository;
import com.company.gamesales.repository.ImportTrackingRepository;
import com.company.gamesales.util.CsvParser;

@Component
public class GameSalesImporter implements DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(GameSalesImporter.class);

	@Value("${partitionSize:10000}")
	private int partitionSize;

	@Autowired
	private GameSalesRepository gameSalesRepository;

	@Autowired
	private ImportTrackingRepository importTrackingRepository;

	private ExecutorService executor;

	public GameSalesImporter(@Value("${executorSize:10}") int executorSize) {
		this.executor = Executors.newFixedThreadPool(executorSize);
		logger.info("executor initialised with size: {}", executorSize);
	}

	@Override
	public void destroy() throws Exception {
		this.executor.shutdown();
	}

	public ImportTracking importCsv(MultipartFile csvFile) throws Exception {

		Instant start = Instant.now();
		List<GameSales> games = CsvParser.parseRequest(csvFile.getInputStream());
		Instant end = Instant.now();
		logger.info("Parsing CSV took {} ms", Duration.between(start, end).toMillis());

		ImportTracking importTracking = new ImportTracking();
		importTracking.setFileName(csvFile.getOriginalFilename());
		importTracking.setImportCount(games.size());
		importTracking.setImportStatus(ImportStatus.RECEIVED);
		ImportTracking saveIt = importTrackingRepository.save(importTracking);
		games.stream().forEach(g -> g.setImportTracking(saveIt));
		importGameSales(games);
		saveIt.setImportStatus(ImportStatus.IMPORT_SUCCESSFUL);
		return importTrackingRepository.save(saveIt);
	}

	private void importGameSales(List<GameSales> gameSales) throws Exception {

		List<List<GameSales>> partition = ListUtils.partition(gameSales, partitionSize);
		logger.info("Splitting CSV import into partition, size {}", partition.size());

		List<Future<Integer>> submitted = new ArrayList<>();
		for (List<GameSales> list : partition) {
			GameSalesImportTaskCallable gameSalesImport = new GameSalesImportTaskCallable(gameSalesRepository, list);
			Future<Integer> submit = executor.submit(gameSalesImport);
			submitted.add(submit);

		}
		AtomicInteger insertedCount = new AtomicInteger(0);
		for (Future<Integer> future : submitted) {
			insertedCount.getAndAdd(future.get());
		}
		logger.info("Imported {} records", insertedCount.get());
	}

	public ImportTracking importCsvAsync(MultipartFile csvFile) throws Exception {

		Instant start = Instant.now();
		List<GameSales> games = CsvParser.parseRequest(csvFile.getInputStream());
		Instant end = Instant.now();
		logger.info("Parsing CSV took {} ms", Duration.between(start, end).toMillis());

		ImportTracking importTracking = new ImportTracking();
		importTracking.setFileName(csvFile.getOriginalFilename());
		importTracking.setImportCount(games.size());
		importTracking.setImportStatus(ImportStatus.RECEIVED);
		ImportTracking saveIt = importTrackingRepository.save(importTracking);
		games.stream().forEach(g -> g.setImportTracking(saveIt));
		importGameSalesAsync(games, importTracking);
		return saveIt;
	}

	private void importGameSalesAsync(List<GameSales> gameSales, ImportTracking tracking) throws Exception {

		List<List<GameSales>> partition = ListUtils.partition(gameSales, partitionSize);
		logger.info("Splitting CSV import into partition, size {}", partition.size());

		GameSalesImportCounter counter = new GameSalesImportCounter(importTrackingRepository, tracking,
				gameSales.size());
		for (List<GameSales> list : partition) {
			GameSalesImportTaskRunnable im = new GameSalesImportTaskRunnable(gameSalesRepository, list, counter);
			CompletableFuture.supplyAsync(() -> im, executor).thenAcceptAsync(x -> x.run());
		}
	}

}
