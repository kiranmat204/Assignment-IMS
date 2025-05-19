/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author kiranmat
 */
public class DeleteProductForm {

    protected JFrame frame;
    
    public DeleteProductForm() {
        frame = new JFrame("Delete Product");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        
        JLabel productIDLabel = new JLabel("Product ID:");
        JTextField productIDField = new JTextField(10);
        JButton inputConfirmButton = new JButton("Confirm Delete");
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(productIDLabel,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        frame.add(productIDField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(inputConfirmButton,gbc);
        
        inputConfirmButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    ProductDAO productDAO = new ProductDAO();
                    
                    String productId = productIDField.getText();
                    boolean result = productDAO.removeProduct(productId);
                    if(result){
                        JOptionPane.showMessageDialog(frame, "Product deleted successfully!");
                        frame.dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "Product ID not found!");
                    }
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(frame, "Enter a valid product ID!");
                }
            }
        
        });
        frame.setVisible(true);
    }
}
