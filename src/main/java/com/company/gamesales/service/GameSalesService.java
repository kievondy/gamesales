package com.company.gamesales.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.company.gamesales.model.DailyGamesSalesRevenueByGameNoDto;
import com.company.gamesales.model.DailyGamesSalesRevenueDto;
import com.company.gamesales.model.GameSales;
import com.company.gamesales.model.ImportTracking;
import com.company.gamesales.repository.GameSalesRepository;
import com.company.gamesales.util.CsvParser;

@Service
public class GameSalesService {

	@Value("${paginationSize:100}")
	private int paginationSize;

	@Autowired
	private GameSalesRepository gameSalesRepository;

	@Autowired
	private GameSalesImporter gameSalesImporter;

	public String ok() {
		return "OK";
	}

	public ImportTracking importCsv(MultipartFile csvFile) throws Exception {
		return gameSalesImporter.importCsv(csvFile);
	}

	public ImportTracking importCsvAsync(MultipartFile csvFile) throws Exception {
		return gameSalesImporter.importCsvAsync(csvFile);
	}

	public Page<GameSales> getGameSales(int pageNo) {
		Pageable pageable = PageRequest.of(pageNo, paginationSize);
		Page<GameSales> all = gameSalesRepository.findAll(pageable);
		return all;
	}

	public Page<GameSales> getGameSalesWhereSaleDateBetween(LocalDate from, LocalDate to, int pageNo) {
		Pageable pageable = PageRequest.of(pageNo, paginationSize);
		Page<GameSales> retval = gameSalesRepository.findWhereSaleDateBetween(from, to, pageable);
		return retval;
	}

	public Page<GameSales> getGameSalesWhereSalePriceLessThan(double salePrice, int pageNo) {
		Pageable pageable = PageRequest.of(pageNo, paginationSize);
		Page<GameSales> retval = gameSalesRepository.findWhereSalePriceLessThan(salePrice, pageable);
		return retval;
	}

	public Page<GameSales> getGameSalesWhereSalePriceMoreThan(double salePrice, int pageNo) {
		Pageable pageable = PageRequest.of(pageNo, paginationSize);
		Page<GameSales> retval = gameSalesRepository.findWhereSalePriceMoreThan(salePrice, pageable);
		return retval;
	}

	public List<DailyGamesSalesRevenueDto> getDailyTotalSales(LocalDate from, LocalDate to) {
		return gameSalesRepository.findGameSalesSummary(toUtilDate(from), toUtilDate(to));
	}

	public List<DailyGamesSalesRevenueByGameNoDto> getDailyTotalSalesByGameNo(LocalDate from, LocalDate to,
			int gameNo) {
		return gameSalesRepository.findGameSalesSummaryByGameNo(toUtilDate(from), toUtilDate(to), gameNo);
	}

	private static Date toUtilDate(LocalDate dateToConvert) {
		return Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public Resource generateCsv(int noOfRows) throws IOException {
		return CsvParser.generateCsv(noOfRows);
	}

}
