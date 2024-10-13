package com.company.gamesales.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "GAME_SALES")
public class GameSales implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long gameSalesId;

	@ManyToOne
	@JoinColumn(name = "importId")
	private ImportTracking importTracking;

	private int id;
	private int gameNo;
	private String gameName;
	private String gameCode;
	private int type;
	private double costPrice;
	private double tax;
	private double salePrice;
	private Date dateOfSale;

	@CreationTimestamp
	private Date createdDate;

	public GameSales() {
	}

	public GameSales(int id, 
			int gameNo, 
			String gameName, 
			String gameCode, 
			int type, 
			double costPrice, 
			double tax,
			double salePrice,
			Date dateOfSale) {
		this.id = id;
		this.gameNo = gameNo;
		this.gameName = gameName;
		this.gameCode = gameCode;
		this.type = type;
		this.costPrice = costPrice;
		this.tax = tax;
		this.salePrice = salePrice;
		this.dateOfSale = dateOfSale;
	}
	public GameSales(int id, 
			int gameNo, 
			String gameName, 
			String gameCode, 
			int type, 
			double costPrice, 
			double tax,
			double salePrice) {
		this.id = id;
		this.gameNo = gameNo;
		this.gameName = gameName;
		this.gameCode = gameCode;
		this.type = type;
		this.costPrice = costPrice;
		this.tax = tax;
		this.salePrice = salePrice;
//		this.dateOfSale = dateOfSale;
	}

	public long getGameSalesId() {
		return gameSalesId;
	}

	public void setGameSalesId(long gameSalesId) {
		this.gameSalesId = gameSalesId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public ImportTracking getImportTracking() {
		return importTracking;
	}

	public void setImportTracking(ImportTracking importTracking) {
		this.importTracking = importTracking;
	}

	public int getGameNo() {
		return gameNo;
	}

	public void setGameNo(int gameNo) {
		this.gameNo = gameNo;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public Date getDateOfSale() {
		return dateOfSale;
	}

	public void setDateOfSale(Date dateOfSale) {
		this.dateOfSale = dateOfSale;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
