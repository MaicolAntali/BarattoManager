package com.barattoManager;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.ui.BarattoManagerGui;

import java.awt.*;

public class BarattoManager {
    public static void main(String[] args) {
        new Thread(() -> EventQueue.invokeLater(
                () -> new BarattoManagerGui().setVisible(true))
        ).start();
    }
}
