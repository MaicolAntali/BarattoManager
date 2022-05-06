package com.barattoManager;

import com.barattoManager.ui.BarattoManagerGui;

import java.awt.*;

/**
 * Main Class
 */
public class BarattoManager {
    public static void main(String[] args) {
        new Thread(() -> EventQueue.invokeLater(
                () -> new BarattoManagerGui().setVisible(true))
        ).start();
    }
}
