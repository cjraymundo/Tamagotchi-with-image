/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.finals;

import javax.swing.SwingUtilities;

public class FINALS {
    public static void main(String[] args) {
        // Run the Swing GUI safely on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new Game().setVisible(true);
        });
    }
}
