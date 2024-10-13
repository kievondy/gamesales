package com.company.gamesales.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.rng.RestorableUniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import com.company.gamesales.model.GameSales;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.NamedCsvRecord;
import de.siegmar.fastcsv.writer.CsvWriter;

public class CsvParser {

	private static final ThreadLocal<SimpleDateFormat> formatter = ThreadLocal
			.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

	private static final RestorableUniformRandomProvider rng = RandomSource.JDK.create();

	private static final LocalDate apr1 = LocalDate.of(2024, 4, 1);
	private static final LocalDate apr30 = LocalDate.of(2024, 4, 30);

	public static String readFromInputStream(InputStream inputStream) throws IOException {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		}
		return sb.toString();
	}

	public static List<GameSales> parseRequest(InputStream inputStream) throws IOException {

		CsvReader<NamedCsvRecord> ofNamedCsvRecord = CsvReader.builder()
				.ofNamedCsvRecord(new InputStreamReader(inputStream));
		return ofNamedCsvRecord.stream().map(rec -> {
			String id = rec.getField(0);
			String gameNo = rec.getField(1);
			String gameName = rec.getField(2);
			String gameCode = rec.getField(3);
			String type = rec.getField(4);
			String costPrice = rec.getField(5);
			String tax = rec.getField(6);
			String salePrice = rec.getField(7);
			String dateOfSale = rec.getField(8);
			Date dateOfSaleDt = null;
			try {
				dateOfSaleDt = formatter.get().parse(dateOfSale);
			} catch (ParseException e) {
			}
			GameSales gameSales = new GameSales(Integer.parseInt(id), Integer.parseInt(gameNo), gameName, gameCode,
					Integer.parseInt(type), Double.parseDouble(costPrice), Double.parseDouble(tax),
					Double.parseDouble(salePrice), dateOfSaleDt);
			return gameSales;
		}).collect(Collectors.toList());
	}

	private static String[] csvHeader() {
		return new String[] { "id", "gameNo", "gameName", "gameCode", "type", "costPrice", "tax", "salePrice",
				"dateOfSale" };
	}

	private static String[] newGameSalesCsvDto(int i) {
		int gameNo = rng.nextInt(1, 101);
		String gameName = RandomStringUtils.randomAlphabetic(10);
		String gameCode = RandomStringUtils.randomAlphanumeric(5);
		int type = rng.nextInt(1, 3);
		double cost = new BigDecimal(rng.nextDouble(10, 101)).setScale(2, RoundingMode.HALF_UP).doubleValue();
		double tax = new BigDecimal(cost * 0.09).setScale(2, RoundingMode.HALF_UP).doubleValue();
		double salePrice = new BigDecimal(cost + tax).setScale(2, RoundingMode.HALF_UP).doubleValue();
		LocalDate dateOfSale = between(apr1, apr30);
		return new String[] { String.valueOf(i), String.valueOf(gameNo), gameName, gameCode, String.valueOf(type),
				String.valueOf(cost), String.valueOf(tax), String.valueOf(salePrice), String.valueOf(dateOfSale) };
	}

	private static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
		long startEpochDay = startInclusive.toEpochDay();
		long endEpochDay = endExclusive.toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
		return LocalDate.ofEpochDay(randomDay);
	}

	public static Resource generateCsv(int noOfRows) throws IOException {
		try (StringWriter sw = new StringWriter(); CsvWriter csvWriter = CsvWriter.builder().build(sw);) {
			csvWriter.writeRecord(csvHeader());
			for (int i = 1; i <= noOfRows; i++) {
				csvWriter.writeRecord(newGameSalesCsvDto(i));
			}
			return new ByteArrayResource(sw.toString().getBytes());
		}
	}

}
