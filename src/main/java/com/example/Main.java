package com.example;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Main {
	public static void main(String[] args) {

		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(null);
		String filePath = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile().getAbsolutePath();
			JOptionPane.showMessageDialog(null, "Arquivo Selecionado" + filePath);
		}

		String username = JOptionPane.showInputDialog("Insira o Usuario do DB");
		String password = JOptionPane.showInputDialog("Insira a senha do DB");
		String db = JOptionPane.showInputDialog("Insira o banco que deseja fazer a Query");
		String table = JOptionPane.showInputDialog("Insira a tabela");
		String url = "jdbc:mysql://localhost:3306/" + db;
		String defaultValue = "";
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();
			String insertQuery = "INSERT INTO client (id, name, type1, id_doc_number2, id_doc_number3, id_doc_number4, cell_phone, cell_phone2, gender, email, birthday, register, "
					+ "occupation, phone, phone2, state_id, city_id, street_name, street_number, zipcode, neighborhood_id, complement, id_doc_number, id_doc_number5, company_id, "
					+ "notes, locked, coordinates, expiration_day, expiration_month_offset, client_credit_limit, company_credit_limit, value1, payments, employee_id, active, value2, value3, value4, "
					+ "external_id, badge, external_system_id, is_trainee, doc_name, owner_name, type2, send_notifications) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			List<String> defaultValues = new ArrayList<>();
			DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
			ResultSet resultSet = metaData.getColumns(null, null, table, null);
			SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd.MM.yyyy\\yyyy\\yyyy\\yyyy");
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			int rowIndex;
			int totalLinhasInseridas = 0;
			for (rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				Cell id = row.getCell(0);
				Cell name = row.getCell(1);
				Cell typeDoc = row.getCell(2);
				Cell numberDoc = row.getCell(3);
				Cell typeDoc1 = row.getCell(4);
				Cell numberDoc1 = row.getCell(5);
				Cell cell_phone = row.getCell(6);
				Cell cell_phone2 = row.getCell(7);
				Cell gender = row.getCell(8);
				Cell email = row.getCell(9);
				Cell aniver = row.getCell(10);
				Cell registro = row.getCell(11);

				String idValue = dataFormatter.formatCellValue(id);
				int idDouble = Integer.parseInt(idValue);

				String nameValue = dataFormatter.formatCellValue(name);
				String type1Value = dataFormatter.formatCellValue(typeDoc);
				String numberDocValue = dataFormatter.formatCellValue(numberDoc);
				String typeDoc1Value = dataFormatter.formatCellValue(typeDoc1);
				String numberDoc1Value = dataFormatter.formatCellValue(numberDoc1);
				String cell_phoneValue = dataFormatter.formatCellValue(cell_phone);
				String cell_phone2Value = dataFormatter.formatCellValue(cell_phone2);
				String genderValue = dataFormatter.formatCellValue(gender);
				String emailValue = dataFormatter.formatCellValue(email);
				String birthdayValue = dataFormatter.formatCellValue(aniver);
				String registerValue = dataFormatter.formatCellValue(registro);

				String yearValue = birthdayValue.substring(birthdayValue.lastIndexOf('\\') + 1);
				int year = Integer.parseInt(yearValue);
				Date birthdayDate = new Date(year - 1900, 0, 2);
				String formattedBirthday = outputDateFormat.format(birthdayDate);

				Date registerDate = new Date(year - 1900, 0, 2);
				String formattedRegister = outputDateFormat.format(registerDate);
				Integer typeResult;
				if (type1Value.equals("CPF")) {
					typeResult = 1;
				} else {
					typeResult = 2;
				}

				String valueOccupation = "";
				String valuePhone = "";
				String valuePhone2 = "";
				Integer valueStateId = 0;
				Integer valueCityId = 0;
				Integer valueStreetName = 0;
				Integer valueStreetNumber = 0;
				String valueZipcode = "";
				Integer valueNeighborhoodId = 0;
				String valueComplement = "";
				String valueIdDocNumber = "";
				String valueIdDocNumber5 = "";
				Integer valueCompanyId = 0;
				String valueNotes = "";
				Integer valueLocked = 0;
				String valueCoordinates = "";
				Integer valueExpirationDay = 0;
				Integer valueExpirationMonthOffset = 0;
				Integer valueClientCreditLimit = 0;
				Integer valueCompanyCreditLimit = 0;
				Integer valueValue1 = 0;
				String valuePayments = "";
				Integer valueEmployeeId = 0;
				Integer valueActive = 0;
				Integer valueValue2 = 0;
				Integer valueValue3 = 0;
				Integer valueValue4 = 0;
				Integer valueExternalId = 0;
				String valueBadge = "";
				String valueExternalSystemId = "";
				Integer valueIsTrainee = 0;

				PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
				preparedStatement.setInt(1, idDouble);
				preparedStatement.setString(2, nameValue);
				preparedStatement.setInt(3, typeResult);
				preparedStatement.setString(4, numberDocValue);
				preparedStatement.setString(5, typeDoc1Value);
				preparedStatement.setString(6, numberDoc1Value);
				preparedStatement.setString(7, cell_phoneValue);
				preparedStatement.setString(8, cell_phone2Value);
				preparedStatement.setInt(9, 1);
				preparedStatement.setString(10, emailValue);
				preparedStatement.setString(11, formattedBirthday);
				preparedStatement.setString(12, formattedRegister);

				preparedStatement.setString(13, valueOccupation);
				preparedStatement.setString(14, valuePhone);
				preparedStatement.setString(15, valuePhone2);
				preparedStatement.setInt(16, valueStateId);
				preparedStatement.setInt(17, valueCityId);
				preparedStatement.setInt(18, valueStreetName);
				preparedStatement.setInt(19, valueStreetNumber);
				preparedStatement.setString(20, valueZipcode);
				preparedStatement.setInt(21, valueNeighborhoodId);
				preparedStatement.setString(22, valueComplement);
				preparedStatement.setString(23, valueIdDocNumber);
				preparedStatement.setString(24, valueIdDocNumber5);
				preparedStatement.setInt(25, valueCompanyId);
				preparedStatement.setString(26, valueNotes);
				preparedStatement.setInt(27, valueLocked);
				preparedStatement.setString(28, valueCoordinates);
				preparedStatement.setInt(29, valueExpirationDay);
				preparedStatement.setInt(30, valueExpirationMonthOffset);
				preparedStatement.setInt(31, valueClientCreditLimit);
				preparedStatement.setInt(32, valueCompanyCreditLimit);
				preparedStatement.setInt(33, valueValue1);
				preparedStatement.setString(34, valuePayments);
				preparedStatement.setInt(35, valueEmployeeId);
				preparedStatement.setInt(36, valueActive);
				preparedStatement.setInt(37, valueValue2);
				preparedStatement.setInt(38, valueValue3);
				preparedStatement.setInt(39, valueValue4);
				preparedStatement.setInt(40, valueExternalId);
				preparedStatement.setString(41, valueBadge);
				preparedStatement.setString(42, valueExternalSystemId);
				preparedStatement.setInt(43, valueIsTrainee);
				preparedStatement.setString(44, "");
				preparedStatement.setString(45, "");
				preparedStatement.setInt(46, 0);
				preparedStatement.setInt(47, 0);

				preparedStatement.execute();
				preparedStatement.close();
				totalLinhasInseridas++;
			}
			System.out.println("Row affected = " + totalLinhasInseridas);
			connection.close();
		}

		catch (

		Exception e) {
			e.printStackTrace();
		}
	}
}
