package com.example;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		String db = "db00"; /* JOptionPane.showInputDialog("Insira o banco que deseja fazer a Query"); */
		String table = "client"; /* JOptionPane.showInputDialog("Insira a tabela"); */
		String url = "jdbc:mysql://localhost:3306/db000";
		String defaultValue = "";
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();
			StringBuilder insertQuery = new StringBuilder("INSERT INTO " + table
					+ " (name, type1, id_doc_number2, id_doc_number3, id_doc_number4, cell_phone, cell_phone2, gender, email");
			StringBuilder valuePlaceholders = new StringBuilder(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?");
			List<String> defaultValues = new ArrayList<>();
			DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
			ResultSet resultSet = metaData.getColumns(null, null, table, null);
			int totalColumnsInDataBase = 9;
			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");
				if (!columnName.equals("name") && !columnName.equals("type1") && !columnName.equals("id_doc_number2")
						&& !columnName.equals("id_doc_number3") && !columnName.equals("id_doc_number4")
						&& !columnName.equals("cell_phone") && !columnName.equals("cell_phone2")
						&& !columnName.equals("gender") && !columnName.equals("email")) {
					if (totalColumnsInDataBase > 0) {
						insertQuery.append(",");
						valuePlaceholders.append(",");
					}
					insertQuery.append(columnName);
					valuePlaceholders.append("?");
					defaultValues.add(defaultValue);
					totalColumnsInDataBase++;
				}
			}
			resultSet.close();
			insertQuery.append(")");
			valuePlaceholders.append(")");
			insertQuery.append(valuePlaceholders);
			int rowIndex;
			int totalLinhasInseridas = 0;
			for (rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				Cell name = row.getCell(0);
				Cell typeDoc = row.getCell(1);
				Cell numberDoc = row.getCell(2);
				Cell typeDoc1 = row.getCell(3);
				Cell numberDoc1 = row.getCell(4);
				Cell cell_phone = row.getCell(5);
				Cell cell_phone2 = row.getCell(6);
				Cell gender = row.getCell(7);
				Cell email = row.getCell(8);
				String nameValue = dataFormatter.formatCellValue(name);
				String type1Value = dataFormatter.formatCellValue(typeDoc);
				String numberDocValue = dataFormatter.formatCellValue(numberDoc);
				String typeDoc1Value = dataFormatter.formatCellValue(typeDoc1);
				String numberDoc1Value = dataFormatter.formatCellValue(numberDoc1);
				String cell_phoneValue = dataFormatter.formatCellValue(cell_phone);
				String cell_phone2Value = dataFormatter.formatCellValue(cell_phone2);
				String genderValue = dataFormatter.formatCellValue(gender);
				String emailValue = dataFormatter.formatCellValue(email);
				Integer typeResult;
				  if (type1Value.equals("CPF")) { typeResult = 1;

				  } else { typeResult = 2;
				  }
				PreparedStatement preparedStatement = connection.prepareStatement(insertQuery.toString());
				preparedStatement.setString(1, nameValue);
				preparedStatement.setInt(2, typeResult);
				preparedStatement.setString(3, numberDocValue);
				preparedStatement.setString(4, typeDoc1Value);
				preparedStatement.setString(5, numberDoc1Value);
				preparedStatement.setString(6, cell_phoneValue);
				preparedStatement.setString(7, cell_phone2Value);
				preparedStatement.setInt(8, 1);
				preparedStatement.setString(9, emailValue);
			    for (int j = 0; j < defaultValues.size(); j++) {
                    String value = defaultValues.get(j);
                    if (value.isEmpty()) {
                        preparedStatement.setInt(j + 10, 0);
                    } else {
                        preparedStatement.setString(j + 10, value);
                    }
                }
			    //Inserir a data de nascimento
				preparedStatement.execute();
				preparedStatement.close();
				System.out.println("TA FUNCIONANDO!!" + nameValue);
				System.out.println("Olha aqui a porra do type " + type1Value);
				System.out.println("CPF ou CNPJ " + numberDocValue);
				System.out.println("RG ou IE " + numberDoc1Value);
				System.out.println("Celular " + cell_phoneValue);
				System.out.println("Segundo Celular " + cell_phone2Value);
				System.out.println("Sexo " + genderValue);
				System.out.println("Email " + emailValue);
				System.out.println("----------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}