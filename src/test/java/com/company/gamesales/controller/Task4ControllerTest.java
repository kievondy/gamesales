package com.company.gamesales.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.company.gamesales.model.DailyGamesSalesRevenueByGameNoDto;
import com.company.gamesales.model.DailyGamesSalesRevenueDto;
import com.company.gamesales.service.GameSalesService;

@WebMvcTest(Task4Controller.class)
class Task4ControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	GameSalesService service;

	@Test
	void test_getDailyTotalSales() throws Exception {
		List<DailyGamesSalesRevenueDto> list = List.of(new DailyGamesSalesRevenueDto());
		when(service.getDailyTotalSales(any(), any())).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get("/task4/getDailyTotalSales/2024-04-01/2024-04-10"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpectAll(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void test_getDailyTotalSales_when_error() throws Exception {
		Throwable ex = new RuntimeException("exception");
		when(service.getDailyTotalSales(any(), any())).thenThrow(ex);
		mockMvc.perform(MockMvcRequestBuilders.get("/task4/getDailyTotalSales/2024-04-01/2024-04-10"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void test_getDailyTotalSalesByGameNo() throws Exception {
		List<DailyGamesSalesRevenueByGameNoDto> list = List.of(new DailyGamesSalesRevenueByGameNoDto());
		when(service.getDailyTotalSalesByGameNo(any(), any(), anyInt())).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get("/task4/getDailyTotalSalesByGameNo/2024-04-01/2024-04-10/123"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpectAll(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void test_getDailyTotalSalesByGameNo_when_error() throws Exception {
		Throwable ex = new RuntimeException("exception");
		when(service.getDailyTotalSalesByGameNo(any(), any(), anyInt())).thenThrow(ex);
		mockMvc.perform(MockMvcRequestBuilders.get("/task4/getDailyTotalSalesByGameNo/2024-04-01/2024-04-10/123"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

}
