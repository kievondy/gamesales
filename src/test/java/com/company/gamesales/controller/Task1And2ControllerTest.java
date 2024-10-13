package com.company.gamesales.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.company.gamesales.model.ImportTracking;
import com.company.gamesales.service.GameSalesService;

@WebMvcTest(Task1And2Controller.class)
class Task1And2ControllerTest {

	private static final String CSV_CONTENT = "id,gameNo,gameName,gameCode,type,costPrice,tax,salePrice,dateOfSale\n"
			+ "1,991,Helldivers,HD1,1,66.34,5.9706,72.3106,2024-10-09\n"
			+ "2,882,Call of duty,CD1,1,73.12,6.5808,79.7008,2024-10-09";

	@Autowired
	MockMvc mockMvc;

	@MockBean
	GameSalesService service;

	@Test
	void test_ok() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/task1/ok")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void test_import() throws Exception {
		ImportTracking tracking = new ImportTracking();
		MockMultipartFile file = new MockMultipartFile("csvFile", "data.csv", MediaType.TEXT_PLAIN_VALUE,
				CSV_CONTENT.getBytes());
		when(service.importCsv(file)).thenReturn(tracking);
		mockMvc.perform(MockMvcRequestBuilders.multipart("/task1/import").file(file))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		verify(service).importCsv(file);
	}

	@Test
	void test_import_when_error() throws Exception {
		Throwable ex = new RuntimeException("exception");
		MockMultipartFile file = new MockMultipartFile("csvFile", "data.csv", MediaType.TEXT_PLAIN_VALUE,
				CSV_CONTENT.getBytes());
		when(service.importCsv(file)).thenThrow(ex);
		mockMvc.perform(MockMvcRequestBuilders.multipart("/task1/import").file(file))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		verify(service).importCsv(file);
	}

}
