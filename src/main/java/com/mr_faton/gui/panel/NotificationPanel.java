package com.mr_faton.gui.panel;

import javax.swing.*;
import java.awt.*;

public class NotificationPanel extends JPanel {

    private final JTextArea notificationArea;
    private final JScrollPane scrollPane;

    public NotificationPanel() {
        notificationArea = new JTextArea("уведомлений пока нет...");
        notificationArea.setLineWrap(true);
        notificationArea.setEditable(false);

        scrollPane = new JScrollPane(notificationArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        setBorder(BorderFactory.createTitledBorder("Активность..."));
        setLayout(new BorderLayout(5, 5));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addNotification(String notification) {
        notificationArea.append(notification);
    }

    public void addWarningNotification(String warningNotification) {
//        Font font = new Font("Verdana", Font.BOLD, 12);
//        notificationArea.setFont(font);
//        notificationArea.setForeground(Color.BLUE);
        notificationArea.append(warningNotification);
    }
}
