package com.mr_faton.gui.panel;

import com.mr_faton.gui.dialog.TelegramsDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JMenuBar {

    public Menu() {
        JMenu settings = new JMenu("Настройки");
        JMenu help = new JMenu("Помощь");

        JMenuItem telegramSettings = new JMenuItem("Телеграммы");
        JMenuItem dbServerSettings = new JMenuItem("Серверы БД");

        JMenuItem about = new JMenuItem("О программе");

        telegramSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelegramsDialog telegramsDialog = new TelegramsDialog();
                telegramsDialog.setVisible(true);
                telegramsDialog.toFront();
            }
        });

        settings.add(telegramSettings);
        settings.add(dbServerSettings);

        help.add(about);

        add(settings);
        add(help);
    }
}
