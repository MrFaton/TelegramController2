package com.mr_faton.gui.notifier;

import com.mr_faton.core.context.AppContext;
import com.mr_faton.gui.frame.MainFrame;

import javax.swing.*;

public class UserNotifier {
    private static final MainFrame mainFrame = (MainFrame) AppContext.getBeanByName("mainFrame");

    public static void warningMessage(String title, String message) {
        JOptionPane.showMessageDialog(mainFrame, message, title, JOptionPane.WARNING_MESSAGE);
    }
}
