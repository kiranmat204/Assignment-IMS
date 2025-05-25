/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

/**
 *
 * @author kiranmat
 */
public class UserAuthentication {
    public static boolean authenticate(String username, String password) {
        // Placeholder logic. Replace with DB check or hashed password comparison
        return "admin".equals(username) && "password".equals(password);
    }
}
