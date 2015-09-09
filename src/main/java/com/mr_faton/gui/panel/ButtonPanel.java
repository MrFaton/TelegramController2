package com.mr_faton.gui.panel;

import com.mr_faton.core.util.SettingsHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {
    private final JButton startButton;
    private final JButton notifiedButton;
    private final JButton stopButton;

    public ButtonPanel() {
        startButton = new JButton("Старт");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableStartButton();
                enableStopButton();
                System.out.println("press Start Button");
            }
        });

        notifiedButton = new JButton("Уведомлён");
        notifiedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //notified button listener
            }
        });

        stopButton = new JButton("Стоп");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableStopButton();
                enableStartButton();
                System.out.println("press Stop Button");
            }
        });

        setLayout(new GridLayout(1, 3, 60, 0));
        add(startButton);
        add(notifiedButton);
        add(stopButton);

        disableNotifiedButton();
        disableStopButton();
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
