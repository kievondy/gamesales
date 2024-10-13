package com.company.gamesales.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.rng.RestorableUniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.company.gamesales.model.DailyGamesSalesRevenueByGameNoDto;
import com.company.gamesales.model.DailyGamesSalesRevenueDto;
import com.company.gamesales.model.GameSales;
import com.company.gamesales.model.ImportTracking;
import com.company.gamesales.repository.GameSalesRepository;

@ExtendWith(MockitoExtension.class)
class GameSalesServiceTest {

	private static final RestorableUniformRandomProvider rng = RandomSource.JDK.create();

	@InjectMocks
	GameSalesService service;

	@Mock
	private GameSalesRepository gameSalesRepository;

	@Mock
	private GameSalesImporter gameSalesImporter;

	@BeforeEach
	public void before() {
		ReflectionTestUtils.setField(service, "paginationSize", 100);
	}

	@Test
	void test_getImport() throws Exception {
		MultipartFile file = Mockito.mock(MultipartFile.class);
		ImportTracking tracking = new ImportTracking();
		when(gameSalesImporter.importCsv(file)).thenReturn(tracking);
		ImportTracking importCsv = service.importCsv(file);
		verify(gameSalesImporter).importCsv(file);
		MatcherAssert.assertThat(importCsv, Matchers.is(tracking));
	}

	@Test
	void test_getGameSales() {
		int pageNo = 0;
		Page<GameSales> page = new PageImpl<GameSales>(List.of(new GameSales()));
		when(gameSalesRepository.findAll(any(Pageable.class))).thenReturn(page);
		Page<GameSales> gameSales = service.getGameSales(pageNo);
		verify(gameSalesRepository).findAll(any(Pageable.class));
		MatcherAssert.assertThat(gameSales, Matchers.is(page));
	}

	@Test
	void test_getGameSalesWhereSalePriceLessThan() {
		int pageNo = 0;
		double salePrice = 45.67;
		Page<GameSales> page = new PageImpl<GameSales>(List.of(new GameSales()));
		when(gameSalesRepository.findWhereSalePriceLessThan(eq(salePrice), any(Pageable.class))).thenReturn(page);
		Page<GameSales> gameSales = service.getGameSalesWhereSalePriceLessThan(salePrice, pageNo);
		verify(gameSalesRepository).findWhereSalePriceLessThan(eq(salePrice), any(Pageable.class));
		MatcherAssert.assertThat(gameSales, Matchers.is(page));
	}

	@Test
	void test_getGameSalesWhereSalePriceMoreThan() {
		int pageNo = 0;
		double salePrice = 45.67;
		Page<GameSales> page = new PageImpl<GameSales>(List.of(new GameSales()));
		when(gameSalesRepository.findWhereSalePriceMoreThan(eq(salePrice), any(Pageable.class))).thenReturn(page);
		Page<GameSales> gameSales = service.getGameSalesWhereSalePriceMoreThan(salePrice, pageNo);
		verify(gameSalesRepository).findWhereSalePriceMoreThan(eq(salePrice), any(Pageable.class));
		MatcherAssert.assertThat(gameSales, Matchers.is(page));
	}

	@Test
	void test_getGameSalesWhereSaleDateBetween() {
		int pageNo = 0;
		LocalDate from = LocalDate.of(2024, 4, 1);
		LocalDate to = LocalDate.of(2024, 4, 30);
		Page<GameSales> page = new PageImpl<GameSales>(List.of(new GameSales()));
		when(gameSalesRepository.findWhereSaleDateBetween(eq(from), eq(to), any(Pageable.class))).thenReturn(page);
		Page<GameSales> gameSales = service.getGameSalesWhereSaleDateBetween(from, to, pageNo);
		verify(gameSalesRepository).findWhereSaleDateBetween(eq(from), eq(to), any(Pageable.class));
		MatcherAssert.assertThat(gameSales, Matchers.is(page));
	}

	@Test
	void test_getDailyTotalSalesByGameNo() {
		LocalDate from = LocalDate.of(2024, 4, 1);
		LocalDate to = LocalDate.of(2024, 4, 10);
		int gameNo = rng.nextInt(1, 100);
		List<DailyGamesSalesRevenueByGameNoDto> list = List.of(new DailyGamesSalesRevenueByGameNoDto());
		when(gameSalesRepository.findGameSalesSummaryByGameNo(any(), any(), eq(gameNo))).thenReturn(list);
		List<DailyGamesSalesRevenueByGameNoDto> dailyTotalSalesByGameNo = service.getDailyTotalSalesByGameNo(from, to,
				gameNo);
		verify(gameSalesRepository).findGameSalesSummaryByGameNo(any(), any(), eq(gameNo));
		MatcherAssert.assertThat(dailyTotalSalesByGameNo, Matchers.is(list));
	}

	@Test
	void test_getDailyTotalSales() {
		LocalDate from = LocalDate.of(2024, 4, 1);
		LocalDate to = LocalDate.of(2024, 4, 10);
		List<DailyGamesSalesRevenueDto> list = List.of(new DailyGamesSalesRevenueDto());
		when(gameSalesRepository.findGameSalesSummary(any(), any())).thenReturn(list);
		List<DailyGamesSalesRevenueDto> dailyTotalSales = service.getDailyTotalSales(from, to);
		verify(gameSalesRepository).findGameSalesSummary(any(), any());
		MatcherAssert.assertThat(dailyTotalSales, Matchers.is(list));
	}

}
