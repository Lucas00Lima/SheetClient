package com.example;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Main {
    public static void main(String[] args) {
        /*
         * JFileChooser fileChooser = new JFileChooser();
         * int result = fileChooser.showOpenDialog(null);
         * String filePath = null;
         * if (result == JFileChooser.APPROVE_OPTION) {
         * filePath = fileChooser.getSelectedFile().getAbsolutePath();
         * JOptionPane.showMessageDialog(null, "Arquivo Selecionado" + filePath);
         * }
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
            StringBuilder insertQuery = new StringBuilder(
                    "INSERT INTO " + table + " VALUES (name,type1,cell_phone,cell_phone2,email,id_doc_number2,id_doc_number3,id_doc_number4)");
            StringBuilder valuePlaceholders =  new StringBuilder(" VALUES (?,?,?,?,?,?,?,?)");
            List<String> defaultValues = new ArrayList<>();
            DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, table, null);
            int totalColumnsInDataBase = 6;

            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                if (!columnName.equals("name") && !columnName.equals("type1") && !columnName.equals("cell_phone") 
                && !columnName.equals("cell_phone2") && !columnName.equals("email") 
                && !columnName.equals("id_doc_number2") && !columnName.equals("id_doc_number3") && !columnName.equals("id_doc_number4")){;
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
            	//Seta cada celula que vou pegar da planilha
            }
            System.out.println("TA FUNCIONANDO!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}