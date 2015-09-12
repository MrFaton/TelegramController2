package com.mr_faton.gui.panel;

import com.mr_faton.core.TelegramController;
import com.mr_faton.core.context.AppContext;
import com.mr_faton.core.dao.TelegramControllerDAO;
import com.mr_faton.core.util.AlarmPlayer;
import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {
    private final JButton startButton;
    private final JButton notifiedButton;
    private final JButton stopButton;
    private final AlarmPlayer alarmPlayer;
    private Thread telegramControllerTread = null;

    public ButtonPanel(final AlarmPlayer alarmPlayer) {
        this.alarmPlayer = alarmPlayer;

        startButton = new JButton("Старт");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableStartButton();
                enableStopButton();
                telegramControllerTread = new Thread(new TelegramController(
                        (TelegramControllerDAO) AppContext.getBeanByName("telegramControllerDAO"),
                        (NotificationPanel) AppContext.getBeanByName("notificationPanel"),
                        (ButtonPanel) AppContext.getBeanByName("buttonPanel"),
                        (AlarmPlayer) AppContext.getBeanByName("alarmPlayer")
                ));
                telegramControllerTread.start();
            }
        });

        notifiedButton = new JButton("Уведомлён");
        notifiedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableStopButton();
                alarmPlayer.stop();
            }
        });

        stopButton = new JButton("Стоп");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableStopButton();
                enableStartButton();
                telegramControllerTread.interrupt();
            }
        });

        setLayout(new GridLayout(1, 3, 60, 0));
        add(startButton);
        add(notifiedButton);
        add(stopButton);

        disableNotifiedButton();
        disableStopButton();
    }

    public boolean isStartButtonEnabled() {
        return startButton.isEnabled();
    }
    public void enableStartButton() {
        startButton.setEnabled(true);
    }
    public void disableStartButton() {
        startButton.setEnabled(false);
    }

    public void enableNotifiedButton() {
        notifiedButton.setEnabled(true);
    }
    public void disableNotifiedButton() {
        notifiedButton.setEnabled(false);
    }

    public void enableStopButton() {
        stopButton.setEnabled(true);
    }
    public void disableStopButton() {
        stopButton.setEnabled(false);
    }
}
