/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginregisterapp;

/**
 *
 * @author Asus
 */
public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter dan Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method untuk enkripsi password menggunakan Caesar Cipher
    public static String encryptPassword(String password) {
        StringBuilder encrypted = new StringBuilder();
        int shift = 3; // Shift untuk Caesar Cipher
        
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base + shift) % 26 + base);
            }
            encrypted.append(c);
        }
        return encrypted.toString();
    }

    // Method untuk dekripsi password
    public static String decryptPassword(String encryptedPassword) {
        StringBuilder decrypted = new StringBuilder();
        int shift = 3;
        
        for (char c : encryptedPassword.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base - shift + 26) % 26 + base);
            }
            decrypted.append(c);
        }
        return decrypted.toString();
    }

    @Override
    public String toString() {
        return username + ":" + password;
    }
}
