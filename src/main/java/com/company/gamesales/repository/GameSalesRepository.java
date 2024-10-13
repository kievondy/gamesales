package com.company.gamesales.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.company.gamesales.model.GameSales;
import com.company.gamesales.model.DailyGamesSalesRevenueDto;
import com.company.gamesales.model.DailyGamesSalesRevenueByGameNoDto;

public interface GameSalesRepository extends JpaRepository<GameSales, Long> {

	@Query(value = "SELECT * FROM GAME_SALES WHERE SALE_PRICE < ?1", nativeQuery = true)
	Page<GameSales> findWhereSalePriceLessThan(double salePrice, Pageable pageable);

	@Query(value = "SELECT * FROM GAME_SALES WHERE SALE_PRICE > ?1", nativeQuery = true)
	Page<GameSales> findWhereSalePriceMoreThan(double salePrice, Pageable pageable);

	@Query(value = "SELECT * FROM GAME_SALES WHERE DATE_OF_SALE > ?1 AND DATE_OF_SALE < ?2", nativeQuery = true)
	Page<GameSales> findWhereSaleDateBetween(LocalDate from, LocalDate to, Pageable pageable);

	@Query(value = "SELECT new com.company.gamesales.model.DailyGamesSalesRevenueDto(GS.dateOfSale, COUNT(1) AS salesCount, SUM(GS.salePrice) AS totalSales) "
			+ "FROM GameSales GS " 
			+ "WHERE GS.dateOfSale > ?1 AND GS.dateOfSale < ?2 " 
			+ "GROUP BY GS.dateOfSale "
			+ "ORDER BY GS.dateOfSale")
	List<DailyGamesSalesRevenueDto> findGameSalesSummary(Date from, Date to);

	@Query(value = "SELECT new com.company.gamesales.model.DailyGamesSalesRevenueByGameNoDto (GS.dateOfSale, GS.gameNo, "
			+ "COUNT(1) AS salesCount, SUM(GS.salePrice) AS totalSales) "
			+ "FROM GameSales GS " 
			+ "WHERE GS.dateOfSale > ?1 "
			+ "AND GS.dateOfSale < ?2 " 
			+ "AND GS.gameNo = ?3 " 
			+ "GROUP BY GS.dateOfSale, GS.gameNo "
			+ "ORDER BY GS.dateOfSale, GS.gameNo")
	List<DailyGamesSalesRevenueByGameNoDto> findGameSalesSummaryByGameNo(Date from, Date to, int gameNo);

}
