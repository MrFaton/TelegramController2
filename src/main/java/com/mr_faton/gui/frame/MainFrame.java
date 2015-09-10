package com.mr_faton.gui.frame;

import com.mr_faton.gui.panel.*;
import com.mr_faton.gui.panel.Menu;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private final int WIDTH = 645;
    private final int HEIGHT = 360;

    public MainFrame(NotificationPanel notificationPanel, ButtonPanel buttonPanel, Menu menuBar) throws HeadlessException {
        setLayout(new BorderLayout(10, 10));
        setJMenuBar(menuBar);
        add(notificationPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension monitorScreenSize = toolkit.getScreenSize();
        int monitorWidth = monitorScreenSize.width;
        int monitorHeight = monitorScreenSize.height;

        setSize(WIDTH, HEIGHT);
        setLocation(monitorWidth / 2 - WIDTH / 2, monitorHeight / 2 - HEIGHT / 2);
        setResizable(true);
        setTitle("Telegram Controller 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
