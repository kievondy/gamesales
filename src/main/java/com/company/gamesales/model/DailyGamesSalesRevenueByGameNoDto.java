package com.company.gamesales.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DailyGamesSalesRevenueByGameNoDto extends DailyGamesSalesRevenueDto {

	private static final long serialVersionUID = 1L;

	private int gameNo;

	public DailyGamesSalesRevenueByGameNoDto() {
		super();
	}

	public DailyGamesSalesRevenueByGameNoDto(Date dateOfSale, int gameNo, long salesCount, double totalSales) {
		super(dateOfSale, salesCount, totalSales);
		this.gameNo = gameNo;
	}

	public int getGameNo() {
		return gameNo;
	}

	public void setGameNo(int gameNo) {
		this.gameNo = gameNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
