package com.company.gamesales.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.company.gamesales.model.ImportTracking;
import com.company.gamesales.repository.GameSalesRepository;
import com.company.gamesales.repository.ImportTrackingRepository;

@ExtendWith(MockitoExtension.class)
class GameSalesImporterTest {

	private static final String CSV_CONTENT = "id,gameNo,gameName,gameCode,type,costPrice,tax,salePrice,dateOfSale\n"
			+ "1,991,Helldivers,HD1,1,66.34,5.9706,72.3106,2024-10-09\n"
			+ "2,882,Call of duty,CD1,1,73.12,6.5808,79.7008,2024-10-09";

	@InjectMocks
	GameSalesImporter gameSalesImporter = new GameSalesImporter(1);

	@Mock
	private GameSalesRepository gameSalesRepository;

	@Mock
	private ImportTrackingRepository importTrackingRepository;

	@Captor
	ArgumentCaptor<ImportTracking> captorImportTracking;

	@BeforeEach
	public void before() {
		ReflectionTestUtils.setField(gameSalesImporter, "partitionSize", 2);
	}

	@Test
	void test_importCsv() throws Exception {
		MultipartFile file = Mockito.mock(MultipartFile.class);
		InputStream is = new ByteArrayInputStream(CSV_CONTENT.getBytes());
		ImportTracking tracking = new ImportTracking();
		when(file.getInputStream()).thenReturn(is);
		when(importTrackingRepository.save(captorImportTracking.capture())).thenReturn(tracking);
		ImportTracking importCsv = gameSalesImporter.importCsv(file);
		verify(importTrackingRepository, atLeastOnce()).save(any());
		verify(gameSalesRepository).saveAll(anyCollection());
		MatcherAssert.assertThat(importCsv, Matchers.is(tracking));
	}
	
	@Test
	void test_importCsvAsync() throws Exception {
		MultipartFile file = Mockito.mock(MultipartFile.class);
		InputStream is = new ByteArrayInputStream(CSV_CONTENT.getBytes());
		ImportTracking tracking = new ImportTracking();
		when(file.getInputStream()).thenReturn(is);
		when(importTrackingRepository.save(captorImportTracking.capture())).thenReturn(tracking);
		ImportTracking importCsv = gameSalesImporter.importCsvAsync(file);
		verify(importTrackingRepository, atLeastOnce()).save(any());
		MatcherAssert.assertThat(importCsv, Matchers.is(tracking));
	}

}
