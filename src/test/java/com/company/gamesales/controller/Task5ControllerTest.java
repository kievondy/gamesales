package com.company.gamesales.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.company.gamesales.service.GameSalesService;

@WebMvcTest(Task5Controller.class)
class Task5ControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	GameSalesService service;

	@Test
	void test_generateCsv() throws Exception {
		Resource resource = Mockito.mock(Resource.class);
		when(service.generateCsv(anyInt())).thenReturn(resource);
		mockMvc.perform(MockMvcRequestBuilders.get("/task5/generateCsv/" + 3))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_OCTET_STREAM));
		verify(service).generateCsv(anyInt());
	}

	@Test
	void test_generateCsv_when_error() throws Exception {
		Throwable exc = new RuntimeException("exception");
		when(service.generateCsv(anyInt())).thenThrow(exc);
		mockMvc.perform(MockMvcRequestBuilders.get("/task5/generateCsv/" + 3))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		verify(service).generateCsv(anyInt());
	}

}
