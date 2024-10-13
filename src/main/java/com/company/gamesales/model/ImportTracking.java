package com.company.gamesales.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "IMPORT_TRACKING")
public class ImportTracking implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long importId;

	private String fileName;
	private int importCount;
	@Enumerated(EnumType.STRING)
	private ImportStatus importStatus;

	@CreationTimestamp
	private Date importDate;

	public long getImportId() {
		return importId;
	}

	public void setImportId(long importId) {
		this.importId = importId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getImportCount() {
		return importCount;
	}

	public void setImportCount(int importCount) {
		this.importCount = importCount;
	}

	public ImportStatus getImportStatus() {
		return importStatus;
	}

	public void setImportStatus(ImportStatus importStatus) {
		this.importStatus = importStatus;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
