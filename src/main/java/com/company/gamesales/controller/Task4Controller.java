package com.company.gamesales.controller;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.gamesales.model.DailyGamesSalesRevenueByGameNoDto;
import com.company.gamesales.model.DailyGamesSalesRevenueDto;
import com.company.gamesales.service.GameSalesService;

@RestController
@RequestMapping("/task4")
public class Task4Controller {

	private static final Logger logger = LoggerFactory.getLogger(Task4Controller.class);

	@Autowired
	private GameSalesService service;

	@GetMapping("/getDailyTotalSales/{from}/{to}")
	public ResponseEntity<?> getDailyTotalSales(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
		try {
			Instant start = Instant.now();
			List<DailyGamesSalesRevenueDto> dailyTotalSales = service.getDailyTotalSales(from, to);
			Instant end = Instant.now();
			logger.info("dailyTotalSales took {} ms", Duration.between(start, end).toMillis());
			return ResponseEntity.ok(dailyTotalSales);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error getting getDailyTotalSales");
		}
	}

	@GetMapping("/getDailyTotalSalesByGameNo/{from}/{to}/{gameNo}")
	public ResponseEntity<?> getDailyTotalSalesByGameNo(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to, 
			@PathVariable int gameNo) {
		try {
			Instant start = Instant.now();
			List<DailyGamesSalesRevenueByGameNoDto> dailyTotalSalesByGameNo = service.getDailyTotalSalesByGameNo(from, to, gameNo);
			Instant end = Instant.now();
			logger.info("dailyTotalSalesByGameNo took {} ms", Duration.between(start, end).toMillis());
			return ResponseEntity.ok(dailyTotalSalesByGameNo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error getting getDailyTotalSalesByGameNo");
		}
	}

}
