package com.example;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/*						Quando for um cliente Pessoa Fisica
													type1 = 1
													gender = 1 (masculino) e 2 (feminino)
													id_doc_number2
						Quando for um cliente Pessoa Juridica
													type1 = 2
													id_doc_number3 = CNPJ
													id_doc_number4 = IE
*/
public class Main {
	public static void main(String[] args) {
		/*
		 * JFileChooser fileChooser = new JFileChooser(); int result =
		 * fileChooser.showOpenDialog(null); String filePath = null; if (result ==
		 * JFileChooser.APPROVE_OPTION) { filePath =
		 * fileChooser.getSelectedFile().getAbsolutePath();
		 * JOptionPane.showMessageDialog(null, "Arquivo Selecionado" + filePath); }
		 */
		String filePath = "C:\\Users\\lukin\\OneDrive\\Área de Trabalho\\planilha\\cliente.xlsx";
		String username = "root"; /* JOptionPane.showInputDialog("Insira o Usuario do DB"); */
		String password = "@soma+"; /* JOptionPane.showInputDialog("Insira a senha do DB"); */
		String db = "db000"; /* JOptionPane.showInputDialog("Insira o banco que deseja fazer a Query"); */
		String table = "client"; /* JOptionPane.showInputDialog("Insira a tabela"); */
		String url = "jdbc:mysql://localhost:3306/" + db;
		String defaultValue = "";
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();
			StringBuilder insertQuery = new StringBuilder("INSERT INTO " + table
					+ " (id,name,type1,id_doc_number2,id_doc_number3,id_doc_number4,cell_phone,cell_phone2,gender,email,birthday,register");
			StringBuilder valuePlaceholders = new StringBuilder(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?");
			List<String> defaultValues = new ArrayList<>();
			DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
			ResultSet resultSet = metaData.getColumns(null, null, table, null);
			SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd.MM.yyyy\\yyyy\\yyyy\\yyyy");
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			int totalColumnsInDataBase = 13;
			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME").toLowerCase();
				if (!columnName.equals("id") && !columnName.equals("name") && !columnName.equals("type1")
						&& !columnName.equals("id_doc_number2") && !columnName.equals("id_doc_number3")
						&& !columnName.equals("id_doc_number4") && !columnName.equals("cell_phone")
						&& !columnName.equals("cell_phone2") && !columnName.equals("gender")
						&& !columnName.equals("email") && !columnName.equals("birthday")
						&& !columnName.equals("register")) {
					if (totalColumnsInDataBase > 0) {
						insertQuery.append(",");
						valuePlaceholders.append(",");
					}
					insertQuery.append(columnName);
					valuePlaceholders.append("?");
					defaultValues.add(defaultValue);
					totalColumnsInDataBase++;
				}
				if (columnName.equals("is_trainee")) {
					break;
				}
			}
			resultSet.close();
			insertQuery.append(")");
			valuePlaceholders.append(")");
			insertQuery.append(valuePlaceholders);

			Statement statement = connection.createStatement();
			String alterTableQuery = "ALTER TABLE " + table;
			String columnsQuery = "SHOW COLUMNS FROM " + table;
			statement.execute(columnsQuery);
			var result = statement.getResultSet();
			while (result.next()) {
				String columnNameString = result.getString("Field");
				String columnType = result.getString("Type");
				if (!columnNameString.equalsIgnoreCase("id")) {
					String modifyColumnQuery = alterTableQuery + " MODIFY " + columnNameString + " " + columnType
							+ " NULL";
					statement.addBatch(modifyColumnQuery);
				}
			}
			String alterTable = "ALTER TABLE client MODIFY COLUMN id_doc_number2 VARCHAR(50);";
			statement.addBatch(alterTable);
			statement.executeBatch();
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
				PreparedStatement preparedStatement = connection.prepareStatement(insertQuery.toString());
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
				for (int j = 0; j < defaultValues.size(); j++) {
					String value = defaultValues.get(j);
					if (value.isEmpty()) {
						preparedStatement.setNull(j + 13, java.sql.Types.NULL);
					} else {
						preparedStatement.setString(j + 13, "");
					}
				}
				preparedStatement.execute();
				preparedStatement.close();
				totalLinhasInseridas++;
			}
			ResultSet resultSet1 = metaData.getColumns(null, null, table, null);
			statement.execute(columnsQuery);
			while (resultSet1.next()) {
				String columnNameString = resultSet1.getString("Field");
				String columnType = resultSet1.getString("Type");
				if (!columnNameString.equalsIgnoreCase("id")) {
					String modifyColumnQuery = alterTableQuery + " MODIFY " + columnNameString + " " + columnType
							+ " NOT NULL";
					statement.addBatch(modifyColumnQuery);
					System.out.println(modifyColumnQuery.toString());
				}
				statement.executeBatch();
				statement.close();
			}
			System.out.println("Row affected = " + totalLinhasInseridas);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
