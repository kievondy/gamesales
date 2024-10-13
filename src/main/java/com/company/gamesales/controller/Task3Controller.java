package com.company.gamesales.controller;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.gamesales.model.GameSales;
import com.company.gamesales.service.GameSalesService;

@RestController
@RequestMapping("/task3")
public class Task3Controller {

	private static final Logger logger = LoggerFactory.getLogger(Task3Controller.class);

	@Autowired
	private GameSalesService service;

	@GetMapping("/getGameSales/{pageNo}")
	public ResponseEntity<?> getGameSales(@PathVariable int pageNo) {
		try {
			Instant start = Instant.now();
			Page<GameSales> gameSales = service.getGameSales(pageNo);
			Instant end = Instant.now();
			logger.info("gameSales pageNo {} took {} ms", pageNo, Duration.between(start, end).toMillis());
			return ResponseEntity.ok(gameSales);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error getting GameSales");
		}
	}

	@GetMapping("/getGameSalesBySaleDate/{from}/{to}/{pageNo}")
	public ResponseEntity<?> getGameSalesWhereSaleDateBetween(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to, 
			@PathVariable int pageNo) {
		try {
			Instant start = Instant.now();
			Page<GameSales> gameSalesWhereSaleDateBetween = service.getGameSalesWhereSaleDateBetween(from, to, pageNo);
			Instant end = Instant.now();
			logger.info("gameSalesWhereSaleDateBetween {}/{}/{} took {} ms", from, to, pageNo, Duration.between(start, end).toMillis());
			return ResponseEntity.ok(gameSalesWhereSaleDateBetween);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error getting GameSales");
		}
	}

	@GetMapping("/getGameSalesWhereSalePriceLessThan/{salePrice}/{pageNo}")
	public ResponseEntity<?> getGameSalesWhereSalePriceLessThan(@PathVariable double salePrice, @PathVariable int pageNo) {
		try {
			Instant start = Instant.now();
			Page<GameSales> gameSalesWhereSalePriceLessThan = service.getGameSalesWhereSalePriceLessThan(salePrice, pageNo);
			Instant end = Instant.now();
			logger.info("gameSalesWhereSalePriceLessThan {}/{} took {} ms", salePrice, pageNo, Duration.between(start, end).toMillis());
			return ResponseEntity.ok(gameSalesWhereSalePriceLessThan);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error getting getGameSalesWhereSalePriceLessThan");
		}
	}

	@GetMapping("/getGameSalesWhereSalePriceMoreThan/{salePrice}/{pageNo}")
	public ResponseEntity<?> getGameSalesWhereSalePriceMoreThan(@PathVariable double salePrice, @PathVariable int pageNo) {
		try {
			Instant start = Instant.now();
			Page<GameSales> gameSalesWhereSalePriceMoreThan = service.getGameSalesWhereSalePriceMoreThan(salePrice, pageNo);
			Instant end = Instant.now();
			logger.info("gameSalesWhereSalePriceMoreThan {}/{} took {} ms", salePrice, pageNo, Duration.between(start, end).toMillis());
			return ResponseEntity.ok(gameSalesWhereSalePriceMoreThan);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error getting getGameSalesWhereSalePriceMoreThan");
		}
	}

}
