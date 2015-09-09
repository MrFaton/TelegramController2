package com.mr_faton;

import com.mr_faton.core.context.AppContext;
import com.mr_faton.core.util.SettingsHolder;
import com.mr_faton.gui.frame.MainFrame;

public class StartGUI {
    public static void main(String[] args) {
        SettingsHolder.load();

        MainFrame mainFrame = (MainFrame) AppContext.getBeanByName("mainFrame");
        mainFrame.setVisible(true);
        mainFrame.toFront();
    }
}
