package com.company.gamesales.controller;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.gamesales.service.GameSalesService;

@RestController
@RequestMapping("/task5")
public class Task5Controller {

	private static final Logger logger = LoggerFactory.getLogger(Task5Controller.class);

	@Autowired
	private GameSalesService service;

	@GetMapping("/generateCsv/{noOfRows}")
	public ResponseEntity<?> generateCsv(@PathVariable int noOfRows) {
		try {
			Instant start = Instant.now();
			Resource resource = service.generateCsv(noOfRows);
			Instant end = Instant.now();
			logger.info("generateCsv took {} ms", Duration.between(start, end).toMillis());
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
					.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=gamessales.csv")
					.body(resource);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error getting Csv template");
		}
	}

}
