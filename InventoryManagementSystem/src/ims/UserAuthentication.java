/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author kiranmat
 */
public class UserAuthentication {
    public static boolean authenticate(String username, String password) {
        password = hashInputPassword(password);
        return "admin".equals(username) && "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8".equals(password);
    }

    private static String hashInputPassword(String password) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            
            
            StringBuilder sb = new StringBuilder();
            for(byte b : hashedBytes){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }
        
        catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
}
