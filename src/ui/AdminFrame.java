package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminFrame extends JFrame {
    JTextField namaField, hargaField, stokField;

    public AdminFrame() {
        setTitle("Admin - Input Obat");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        namaField = new JTextField(20);
        hargaField = new JTextField(10);
        stokField = new JTextField(5);
        JButton simpanButton = new JButton("Simpan");

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Nama Obat")); panel.add(namaField);
        panel.add(new JLabel("Harga")); panel.add(hargaField);
        panel.add(new JLabel("Stok")); panel.add(stokField);
        panel.add(new JLabel()); panel.add(simpanButton);

        add(panel);
        setVisible(true);

        simpanButton.addActionListener(e -> simpanObat());
    }

    private void simpanObat() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO obat(nama, harga, stok) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, namaField.getText());
            stmt.setDouble(2, Double.parseDouble(hargaField.getText()));
            stmt.setInt(3, Integer.parseInt(stokField.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Obat berhasil ditambahkan!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
