package ui;

import db.DBConnection;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login Apotek");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username");
        JLabel passLabel = new JLabel("Password");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(userLabel); panel.add(usernameField);
        panel.add(passLabel); panel.add(passwordField);
        panel.add(new JLabel()); panel.add(loginButton);

        add(panel);
        setVisible(true);

        loginButton.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usernameField.getText());
            stmt.setString(2, new String(passwordField.getPassword()));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                dispose();
                if (role.equals("admin")) {
                    new AdminFrame();
                } else {
                    new KasirFrame();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login gagal!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
