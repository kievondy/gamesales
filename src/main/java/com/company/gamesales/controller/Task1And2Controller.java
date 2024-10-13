package com.company.gamesales.controller;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.gamesales.model.ImportTracking;
import com.company.gamesales.service.GameSalesService;

@RestController
@RequestMapping("/task1")
public class Task1And2Controller {

	private static final Logger logger = LoggerFactory.getLogger(Task1And2Controller.class);

	@Autowired
	private GameSalesService service;

	@GetMapping("/ok")
	public ResponseEntity<?> ok() {
		return ResponseEntity.ok(service.ok());
	}

	@PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> importCsv(@RequestPart MultipartFile csvFile) {
		try {
			Instant start = Instant.now();
			ImportTracking importCsv = service.importCsv(csvFile);
			Instant end = Instant.now();
			logger.info("CSV Import {} records took {} ms", importCsv.getImportCount(), Duration.between(start, end).toMillis());
			return ResponseEntity.ok(importCsv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while importing Csv file " + e.getMessage());
		}
	}
	
	@PostMapping(value = "/importAsync", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> importCsvAsync(@RequestPart MultipartFile csvFile) {
		try {
			Instant start = Instant.now();
			ImportTracking importCsv = service.importCsvAsync(csvFile);
			Instant end = Instant.now();
			logger.info("CSV Import Async took {} ms", Duration.between(start, end).toMillis());
			return ResponseEntity.ok(importCsv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while importing Csv file " + e.getMessage());
		}
	}

}
