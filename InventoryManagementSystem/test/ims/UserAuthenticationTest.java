/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ims;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 *
 * @author ankur
 */
public class UserAuthenticationTest {
    
    @Before
    public void setUp() {
        
        // This step is unnecessary for UserAuthentication, but this is for general setup
    }

    @After
    public void tearDown() {
       
        // This is not required for UserAuthentication since no database is used in this class
    }

    @Test
    public void testAuthenticateValidCredentials() {
        String validUsername = "admin";
        String validPassword = "password"; // We will compare hashed password

        // Expected hash of the password "password" using SHA-256
        String expectedHash = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
        
        // Authenticate using valid credentials
        boolean result = UserAuthentication.authenticate(validUsername, validPassword);
        
        // Assert that the authentication returns true for correct credentials
        assertTrue("Valid credentials should authenticate successfully", result);
    }

    @Test
    public void testAuthenticateInvalidUsername() {
        String invalidUsername = "wrongUser";
        String validPassword = "password";

        // Authenticate using invalid username
        boolean result = UserAuthentication.authenticate(invalidUsername, validPassword);
        
        // Assert that the authentication returns false for incorrect username
        assertFalse("Invalid username should fail authentication", result);
    }

    @Test
    public void testAuthenticateInvalidPassword() {
        String validUsername = "admin";
        String invalidPassword = "wrongPassword";

        // Authenticate using valid username but invalid password
        boolean result = UserAuthentication.authenticate(validUsername, invalidPassword);
        
        // Assert that the authentication returns false for incorrect password
        assertFalse("Invalid password should fail authentication", result);
    }

    @Test
    public void testAuthenticateBothInvalid() {
        String invalidUsername = "wrongUser";
        String invalidPassword = "wrongPassword";

        // Authenticate using invalid username and invalid password
        boolean result = UserAuthentication.authenticate(invalidUsername, invalidPassword);
        
        // Assert that the authentication returns false for both incorrect username and password
        assertFalse("Both invalid username and password should fail authentication", result);
    }
}
