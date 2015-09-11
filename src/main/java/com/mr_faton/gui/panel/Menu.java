package com.mr_faton.gui.panel;

import com.mr_faton.gui.dialog.DBServersDialog;
import com.mr_faton.gui.dialog.TelegramsDialog;
import com.mr_faton.gui.notifier.UserNotifier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JMenuBar {
    private final ButtonPanel buttonPanel;

    public Menu(final ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;

        JMenu settings = new JMenu("Настройки");
        JMenu help = new JMenu("Помощь");

        JMenuItem telegramSettings = new JMenuItem("Телеграммы");
        JMenuItem dbServerSettings = new JMenuItem("Серверы БД");

        JMenuItem about = new JMenuItem("О программе");

        telegramSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!buttonPanel.isStartButtonEnabled()) {
                    UserNotifier.warningMessage("Просмотри и редактирование настроек",
                            "Во время работы программы просмотр и редактирование настроек недоступен.<p/>" +
                                    "Пожалуйста остановите работу приложения, а занем просматривайте и редактируйте " +
                                    "настройки телеграмм.");
                    return;
                }

                TelegramsDialog telegramsDialog = new TelegramsDialog();
                telegramsDialog.setVisible(true);
                telegramsDialog.toFront();
            }
        });

        dbServerSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!buttonPanel.isStartButtonEnabled()) {
                    UserNotifier.warningMessage("Просмотри и редактирование настроек",
                            "Во время работы программы просмотр и редактирование настроек недоступен.<p/>" +
                                    "Пожалуйста остановите работу приложения, а занем просматривайте и редактируйте " +
                                    "настройки серверов баз данных.");
                    return;
                }

                DBServersDialog dbServersDialog = new DBServersDialog();
                dbServersDialog.setVisible(true);
                dbServersDialog.toFront();
            }
        });

        settings.add(telegramSettings);
        settings.add(dbServerSettings);

        help.add(about);

        add(settings);
        add(help);
    }
}
