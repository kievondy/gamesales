package com.company.gamesales.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.commons.rng.RestorableUniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.company.gamesales.model.GameSales;
import com.company.gamesales.service.GameSalesService;

@WebMvcTest(Task3Controller.class)
class Task3ControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	GameSalesService service;

	private static final RestorableUniformRandomProvider rng = RandomSource.JDK.create();

	@Test
	void test_getGameSales() throws Exception {
		int pageNo = rng.nextInt(1, 10);
		Page<GameSales> value = new PageImpl<GameSales>(List.of(new GameSales()));
		when(service.getGameSales(pageNo)).thenReturn(value);
		mockMvc.perform(MockMvcRequestBuilders.get("/task3/getGameSales/" + pageNo))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpectAll(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		verify(service).getGameSales(pageNo);
	}

	@Test
	void test_getGameSales_when_error() throws Exception {
		int pageNo = rng.nextInt(1, 10);
		Throwable ex = new RuntimeException("exception");
		when(service.getGameSales(pageNo)).thenThrow(ex);
		mockMvc.perform(MockMvcRequestBuilders.get("/task3/getGameSales/" + pageNo))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		verify(service).getGameSales(pageNo);
	}

	@Test
	void test_getGameSalesBySaleDate() throws Exception {
		int pageNo = rng.nextInt(1, 10);
		Page<GameSales> value = new PageImpl<GameSales>(List.of(new GameSales()));
		when(service.getGameSalesWhereSaleDateBetween(any(), any(), eq(pageNo))).thenReturn(value);
		mockMvc.perform(MockMvcRequestBuilders.get("/task3/getGameSalesBySaleDate/2024-04-01/2024-04-10/" + pageNo))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpectAll(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		verify(service).getGameSalesWhereSaleDateBetween(any(), any(), eq(pageNo));
	}

	@Test
	void test_getGameSalesBySaleDate_when_error() throws Exception {
		int pageNo = rng.nextInt(1, 10);
		Throwable ex = new RuntimeException("exception");
		when(service.getGameSalesWhereSaleDateBetween(any(), any(), eq(pageNo))).thenThrow(ex);
		mockMvc.perform(MockMvcRequestBuilders.get("/task3/getGameSalesBySaleDate/2024-04-01/2024-04-10/" + pageNo))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		verify(service).getGameSalesWhereSaleDateBetween(any(), any(), eq(pageNo));
	}

	@Test
	void test_getGameSalesWhereSalePriceLessThan() throws Exception {
		int pageNo = rng.nextInt(1, 10);
		Page<GameSales> value = new PageImpl<GameSales>(List.of(new GameSales()));
		when(service.getGameSalesWhereSalePriceLessThan(anyDouble(), eq(pageNo))).thenReturn(value);
		mockMvc.perform(MockMvcRequestBuilders.get("/task3/getGameSalesWhereSalePriceLessThan/45.66/" + pageNo))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpectAll(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		verify(service).getGameSalesWhereSalePriceLessThan(anyDouble(), eq(pageNo));
	}

	@Test
	void test_getGameSalesWhereSalePriceLessThan_when_error() throws Exception {
		int pageNo = rng.nextInt(1, 10);
		Throwable ex = new RuntimeException("exception");
		when(service.getGameSalesWhereSalePriceLessThan(anyDouble(), eq(pageNo))).thenThrow(ex);
		mockMvc.perform(MockMvcRequestBuilders.get("/task3/getGameSalesWhereSalePriceLessThan/45.66/" + pageNo))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		verify(service).getGameSalesWhereSalePriceLessThan(anyDouble(), eq(pageNo));
	}

	@Test
	void test_getGameSalesWhereSalePriceMoreThan() throws Exception {
		int pageNo = rng.nextInt(1, 10);
		Page<GameSales> value = new PageImpl<GameSales>(List.of(new GameSales()));
		when(service.getGameSalesWhereSalePriceMoreThan(anyDouble(), eq(pageNo))).thenReturn(value);
		mockMvc.perform(MockMvcRequestBuilders.get("/task3/getGameSalesWhereSalePriceMoreThan/45.66/" + pageNo))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpectAll(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		verify(service).getGameSalesWhereSalePriceMoreThan(anyDouble(), eq(pageNo));
	}

	@Test
	void test_getGameSalesWhereSalePriceMoreThan_when_error() throws Exception {
		int pageNo = rng.nextInt(1, 10);
		Throwable ex = new RuntimeException("exception");
		when(service.getGameSalesWhereSalePriceMoreThan(anyDouble(), eq(pageNo))).thenThrow(ex);
		mockMvc.perform(MockMvcRequestBuilders.get("/task3/getGameSalesWhereSalePriceMoreThan//45.66/" + pageNo))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		verify(service).getGameSalesWhereSalePriceMoreThan(anyDouble(), eq(pageNo));
	}

}
