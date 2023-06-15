package com.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlterTable {

	private Connection connection;
	private String table;
	private static ResultSet resultSet;
	
	public AlterTable(Connection connection) {
		this.connection = connection;
	}

	public AlterTable() {
		// TODO Auto-generated constructor stub
	}

	public AlterTable(String table) {
		this.table = table;
	}

	public void init() throws SQLException {
		DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
		ResultSet resultSet = metaData.getColumns(null, null, table, null);
		Statement statement = connection.createStatement();
		String alterTableQuery = "ALTER TABLE " + table;
		String columnsQuery = "SHOW COLUMNS FROM " + table;
		statement.execute(columnsQuery);
		var result = statement.getResultSet();
		while (result.next()) {
			String columnNameString = result.getString("Field");
			String columnType = result.getString("Type");
			if (!columnNameString.equalsIgnoreCase("id")) {
				String modifyColumnQuery = alterTableQuery + " MODIFY " + columnNameString + " " + columnType + " NULL";
				statement.addBatch(modifyColumnQuery);
			}
		}
		String alterTable = "ALTER TABLE client MODIFY COLUMN id_doc_number2 VARCHAR(50);";
		statement.addBatch(alterTable);
		statement.executeBatch();
		statement.close();
		connection.close();
	}

	public void end() throws SQLException {
		Statement finishPreparedStatement = connection.createStatement();
		String alterTableEnd = "ALTER TABLE " + table;
		String columnsQueryEnd = "SHOW COLUMNS FROM " + table;
		finishPreparedStatement.execute(columnsQueryEnd);
		var resultEnd = finishPreparedStatement.getResultSet();
		while (resultEnd.next()) {
			String columnNameEnd = resultEnd.getString("Field");
			String columnTypeEnd = resultEnd.getString("Type");
			if (!columnNameEnd.equalsIgnoreCase("id")) {
				String modifyColumnQueryEnd = alterTableEnd + " MODIFY " + columnNameEnd + " " + columnTypeEnd
						+ " NOT NULL";
				finishPreparedStatement.addBatch(modifyColumnQueryEnd);
			}
		}
		finishPreparedStatement.executeBatch();

	}

	public static ResultSet getResultSet() {
		return resultSet;
	}
}




























Rescunho


int totalColumnsInDataBase = 13; 
List<String> colAumnNames = newArrayList<>();
List<String> valuePlaceholders = new ArrayList<>();
while(resultSet.next()) { String columnName =
resultSet.getString("COLUMN_NAME").toLowerCase(); if
(!columnName.equals("id") && !columnName.equals("name") &&
!columnName.equals("type1") && !columnName.equals("id_doc_number2") &&
!columnName.equals("id_doc_number3") && !columnName.equals("id_doc_number4")
&& !columnName.equals("cell_phone") && !columnName.equals("cell_phone2") &&
!columnName.equals("gender") && !columnName.equals("email") &&
!columnName.equals("birthday") && !columnName.equals("register")) {

insertQuery.append(String.join(",",columnName)); valuePlaceholders.add("?");
insertQuery.append(") VALUES (").append(valuePlaceholders).append(")");
totalColumnsInDataBase++; } if (columnName.equals("is_trainee")) { break; } }
insertQuery.append(")"); valuePlaceholders.add(")");
insertQuery.append(valuePlaceholders); String alterTable =
"ALTER TABLE client MODIFY COLUMN id_doc_number2 VARCHAR(50);";



//ResultSetMetaData metaData3 = (ResultSetMetaData) resultSet.getMetaData();
//int columnCount = metaData3.getColumnCount();
//int parameterIndex = 13;
//for (int j = 0; j < defaultValues.size(); j++) {
//	String value = defaultValues.get(j);
//	if (value.isEmpty()) {
//		continue;
//	}

//	int columnIndex = j + columnCount + 1; // Índice da coluna no ResultSetMetaData
//	preparedStatement.setString(parameterIndex + columnIndex, value);
//}




preparedStatement.setString(13, valueOccupation);
preparedStatement.setString(14, valuePhone);
preparedStatement.setString(15, valuePhone2);
preparedStatement.setString(17, valueCityId);
preparedStatement.setString(18, valueStreetName);
preparedStatement.setString(19, valueStreetNumber);
preparedStatement.setString(20, valueZipcode);
preparedStatement.setString(21, valueNeighborhoodId);
preparedStatement.setString(22, valueComplement);
preparedStatement.setString(23, valueIdDocNumber);
preparedStatement.setString(24, valueIdDocNumber5);
preparedStatement.setString(25, valueCompanyId);
preparedStatement.setString(26, valueNotes);
preparedStatement.setString(27, valueLocked);
preparedStatement.setString(28, valueCoordinates);
preparedStatement.setString(29, valueExpirationDay);
preparedStatement.setString(30, valueExpirationMonthOffset);
preparedStatement.setString(31, valueClientCreditLimit);
preparedStatement.setString(32, valueCompanyCreditLimit);
preparedStatement.setString(33, valueValue1);
preparedStatement.setString(34, valuePayments);
preparedStatement.setString(35, valueEmployeeId);
preparedStatement.setString(36, valueActive);
preparedStatement.setString(37, valueValue2);
preparedStatement.setString(38, valueValue3);
preparedStatement.setString(39, valueValue4);
preparedStatement.setString(40, valueExternalId);
preparedStatement.setString(41, valueBadge);
preparedStatement.setString(42, valueExternalSystemId);
preparedStatement.setString(43, valueIsTrainee);
