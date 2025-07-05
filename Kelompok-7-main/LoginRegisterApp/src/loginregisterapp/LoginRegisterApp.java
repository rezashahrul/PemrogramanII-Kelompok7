/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package loginregisterapp;

/**
 *
 * @author Asus
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginRegisterApp extends JFrame {
    private AuthManager authManager;
    private JTabbedPane tabbedPane;
    
    // Komponen untuk Login
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;
    private JLabel loginStatusLabel;
    
    // Komponen untuk Register
    private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JLabel registerStatusLabel;

    public LoginRegisterApp() {
        authManager = new AuthManager();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
        setTitle("Aplikasi Login & Register - Kelompok 7");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        tabbedPane = new JTabbedPane();

        // Komponen Login
        loginUsernameField = new JTextField(15);
        loginPasswordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        loginStatusLabel = new JLabel(" ");
        loginStatusLabel.setForeground(Color.RED);

        // Komponen Register
        registerUsernameField = new JTextField(15);
        registerPasswordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        registerButton = new JButton("Register");
        registerStatusLabel = new JLabel(" ");
        registerStatusLabel.setForeground(Color.RED);
    }

    private void setupLayout() {
        // Panel Login
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(loginUsernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(loginPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        gbc.gridy = 3;
        loginPanel.add(loginStatusLabel, gbc);

        // Panel Register
        JPanel registerPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        registerPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(registerUsernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        registerPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(registerPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        registerPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        registerPanel.add(registerButton, gbc);

        gbc.gridy = 4;
        registerPanel.add(registerStatusLabel, gbc);

        // Tambahkan panel ke tab
        tabbedPane.addTab("Login", loginPanel);
        tabbedPane.addTab("Register", registerPanel);

        add(tabbedPane);
    }

    private void setupEventHandlers() {
        // Event handler untuk tombol Login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // Event handler untuk tombol Register
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });

        // Enter key handler untuk login
        loginPasswordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // Enter key handler untuk register
        confirmPasswordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
    }

    private void handleLogin() {
        String username = loginUsernameField.getText().trim();
        String password = new String(loginPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            loginStatusLabel.setText("Username dan password tidak boleh kosong!");
            loginStatusLabel.setForeground(Color.RED);
            return;
        }

        if (authManager.login(username, password)) {
            loginStatusLabel.setText("Login berhasil! Selamat datang, " + username);
            loginStatusLabel.setForeground(Color.GREEN);
            
            // Bersihkan form
            loginUsernameField.setText("");
            loginPasswordField.setText("");
            
            // Tampilkan dialog sukses
            JOptionPane.showMessageDialog(this, 
                "Login berhasil!\nSelamat datang, " + username + "!", 
                "Login Sukses", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            loginStatusLabel.setText("Username atau password salah!");
            loginStatusLabel.setForeground(Color.RED);
            loginPasswordField.setText(""); // Bersihkan password field
        }
    }

    private void handleRegister() {
        String username = registerUsernameField.getText().trim();
        String password = new String(registerPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validasi input
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            registerStatusLabel.setText("Semua field harus diisi!");
            registerStatusLabel.setForeground(Color.RED);
            return;
        }

        if (username.length() < 3) {
            registerStatusLabel.setText("Username minimal 3 karakter!");
            registerStatusLabel.setForeground(Color.RED);
            return;
        }

        if (password.length() < 4) {
            registerStatusLabel.setText("Password minimal 4 karakter!");
            registerStatusLabel.setForeground(Color.RED);
            return;
        }

        if (!password.equals(confirmPassword)) {
            registerStatusLabel.setText("Konfirmasi password tidak cocok!");
            registerStatusLabel.setForeground(Color.RED);
            confirmPasswordField.setText("");
            return;
        }

        if (authManager.isUsernameExists(username)) {
            registerStatusLabel.setText("Username sudah digunakan!");
            registerStatusLabel.setForeground(Color.RED);
            return;
        }

        // Proses registrasi
        if (authManager.register(username, password)) {
            registerStatusLabel.setText("Registrasi berhasil! Silakan login.");
            registerStatusLabel.setForeground(Color.GREEN);
            
            // Bersihkan form
            registerUsernameField.setText("");
            registerPasswordField.setText("");
            confirmPasswordField.setText("");
            
            // Tampilkan dialog sukses dan pindah ke tab login
            JOptionPane.showMessageDialog(this, 
                "Registrasi berhasil!\nSilakan login dengan akun baru Anda.", 
                "Registrasi Sukses", 
                JOptionPane.INFORMATION_MESSAGE);
            
            tabbedPane.setSelectedIndex(0); // Pindah ke tab login
        } else {
            registerStatusLabel.setText("Registrasi gagal! Coba lagi.");
            registerStatusLabel.setForeground(Color.RED);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginRegisterApp().setVisible(true);
            }
        });
    }
}
