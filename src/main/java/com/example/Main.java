package com.example;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.*;

public class Main {
    public static void main(String[] args) throws SQLException {
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
        String url = "jdbc:mysql://localhost:3306/db000";
        String defaultValue = "";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            StringBuilder insertQuery = new StringBuilder("INSERT INTO" + table + "()");
        }
    }
}