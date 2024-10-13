package com.company.gamesales.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DailyGamesSalesRevenueDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dateOfSale;
	private long salesCount;
	private double totalSales;

	public DailyGamesSalesRevenueDto() {
	}

	public DailyGamesSalesRevenueDto(Date dateOfSale, long salesCount, double totalSales) {
		this.dateOfSale = dateOfSale;
		this.salesCount = salesCount;
		this.totalSales = totalSales;
	}

	public Date getDateOfSale() {
		return dateOfSale;
	}

	public void setDateOfSale(Date dateOfSale) {
		this.dateOfSale = dateOfSale;
	}

	public long getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(long salesCount) {
		this.salesCount = salesCount;
	}

	public double getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
