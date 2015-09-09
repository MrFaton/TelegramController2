package com.mr_faton.gui.dialog;

import com.mr_faton.core.context.AppContext;
import com.mr_faton.core.entity.Telegram;
import com.mr_faton.core.util.SettingsHolder;
import com.mr_faton.gui.frame.MainFrame;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelegramsDialog extends JDialog {
    private static final Logger logger = Logger.getLogger("" +
            "com.mr_faton.gui.dialog.TelegramsDialog");
    private static int WIDTH = 450;
    private static int HEIGHT = 400;
    private List<Telegram> telegramList;

    public TelegramsDialog() {
        super((MainFrame) AppContext.getBeanByName("mainFrame"), "Настройки телеграмм", true);

        telegramList = SettingsHolder.getTelegramList();

        String[] columnHeaders = {"Заголовок", "  Цифр заголовок", "Час начала", "Минуты начала", "Период в мин"};
        DefaultTableModel tableModel = new DefaultTableModel(0, 5);
        tableModel.setColumnIdentifiers(columnHeaders);


        if (telegramList != null) {
            int i = 0;
            for (Telegram telegram : telegramList) {
                tableModel.insertRow(i, new String[] {
                        telegram.getHeader(),
                        telegram.getDigitalHeader(),
                        telegram.getBeginHour() + "",
                        telegram.getBeginMin() + "",
                        telegram.getPeriodInMin() + ""
                });
            }
        }

        final JTable table = new JTable(tableModel);
        //устанавливаем цвет фона ячеек белым
        table.setBackground(Color.white);
        //добавляем скроллер в таблицу (так же это позволяет сделать видимыми название столбцов таблицы)
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


    }
}
