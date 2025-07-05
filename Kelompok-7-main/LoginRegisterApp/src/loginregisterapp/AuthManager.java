/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginregisterapp;

/**
 *
 * @author Asus
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuthManager {
    private static final String FILE_PATH = "users.txt";
    private List<User> users;

    public AuthManager() {
        users = new ArrayList<>();
        loadUsersFromFile();
    }

    // Method untuk memuat data user dari file
    private void loadUsersFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(":");
                    if (parts.length >= 2) {
                        String username = parts[0];
                        String encryptedPassword = parts[1];
                        users.add(new User(username, encryptedPassword));
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }

    // Method untuk menyimpan data user ke file
    private void saveUsersToFile() {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            for (User user : users) {
                writer.write(user.toString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // Method untuk registrasi user baru
    public boolean register(String username, String password) {
        // Validasi input
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        // Cek apakah username sudah ada
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // Username sudah digunakan
            }
        }

        // Enkripsi password dan simpan user baru
        String encryptedPassword = User.encryptPassword(password);
        users.add(new User(username, encryptedPassword));
        saveUsersToFile();
        return true;
    }

    // Method untuk login user
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        String encryptedPassword = User.encryptPassword(password);
        
        for (User user : users) {
            if (user.getUsername().equals(username) && 
                user.getPassword().equals(encryptedPassword)) {
                return true;
            }
        }
        return false;
    }

    // Method untuk mengecek apakah username sudah ada
    public boolean isUsernameExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Method untuk mendapatkan jumlah user terdaftar
    public int getTotalUsers() {
        return users.size();
    }
}