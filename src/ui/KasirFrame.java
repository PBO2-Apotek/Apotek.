package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class KasirFrame extends JFrame {
    JTextField idObatField, jumlahField, bulanField, tahunField;

    public KasirFrame() {
        setTitle("Kasir - Transaksi & Laporan");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        idObatField = new JTextField(5);
        jumlahField = new JTextField(5);
        bulanField = new JTextField(5);
        tahunField = new JTextField(5);

        JButton beliButton = new JButton("Beli");
        JButton laporanButton = new JButton("Lihat Laporan");

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("ID Obat")); panel.add(idObatField);
        panel.add(new JLabel("Jumlah")); panel.add(jumlahField);
        panel.add(new JLabel()); panel.add(beliButton);
        panel.add(new JLabel("Bulan")); panel.add(bulanField);
        panel.add(new JLabel("Tahun")); panel.add(tahunField);
        panel.add(new JLabel()); panel.add(laporanButton);

        add(panel);
        setVisible(true);

        beliButton.addActionListener(e -> beliObat());
        laporanButton.addActionListener(e -> tampilkanLaporan());
    }

    private void beliObat() {
        try (Connection conn = DBConnection.getConnection()) {
            int idObat = Integer.parseInt(idObatField.getText());
            int jumlah = Integer.parseInt(jumlahField.getText());
            LocalDate now = LocalDate.now();

            // Update stok
            String updateStok = "UPDATE obat SET stok = stok - ? WHERE id = ?";
            PreparedStatement stmt1 = conn.prepareStatement(updateStok);
            stmt1.setInt(1, jumlah);
            stmt1.setInt(2, idObat);
            stmt1.executeUpdate();

            // Simpan pembelian
            String insert = "INSERT INTO pembelian(id_obat, jumlah, tanggal) VALUES (?, ?, ?)";
            PreparedStatement stmt2 = conn.prepareStatement(insert);
            stmt2.setInt(1, idObat);
            stmt2.setInt(2, jumlah);
            stmt2.setDate(3, Date.valueOf(now));
            stmt2.executeUpdate();

            JOptionPane.showMessageDialog(this, "Pembelian berhasil!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void tampilkanLaporan() {
        try (Connection conn = DBConnection.getConnection()) {
            int bulan = Integer.parseInt(bulanField.getText());
            int tahun = Integer.parseInt(tahunField.getText());

            String sql = """
                SELECT o.nama, SUM(p.jumlah) AS total
                FROM pembelian p
                JOIN obat o ON p.id_obat = o.id
                WHERE MONTH(p.tanggal) = ? AND YEAR(p.tanggal) = ?
                GROUP BY o.nama
                """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bulan);
            stmt.setInt(2, tahun);
            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder("Laporan:\n");
            while (rs.next()) {
                sb.append(rs.getString("nama")).append(" - ").append(rs.getInt("total")).append(" unit\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
