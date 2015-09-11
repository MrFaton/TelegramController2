package com.mr_faton.gui.dialog;

import com.mr_faton.core.context.AppContext;
import com.mr_faton.core.entity.Telegram;
import com.mr_faton.core.util.SettingsHolder;
import com.mr_faton.gui.frame.MainFrame;
import com.mr_faton.gui.notifier.UserNotifier;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class TelegramsDialog extends JDialog {
    private static int WIDTH = 450;
    private static int HEIGHT = 400;
    private List<Telegram> telegramList;

    public TelegramsDialog() {
        super((MainFrame) AppContext.getBeanByName("mainFrame"), "Настройки телеграмм", true);

        telegramList = SettingsHolder.getTelegramList();

        String[] columnHeaders = {"Заголовок", "Цифр заголовок", "Час начала", "Минуты начала", "Период в мин", "Включен"};
        final DefaultTableModel tableModel = new DefaultTableModel(0, 6);
        tableModel.setColumnIdentifiers(columnHeaders);


        if (telegramList != null) {
            int i = 0;
            for (Telegram telegram : telegramList) {
                tableModel.insertRow(i, new Object[] {
                        telegram.getHeader(),
                        telegram.getDigitalHeader(),
                        telegram.getBeginHour(),
                        telegram.getBeginMin(),
                        telegram.getPeriodInMin(),
                        telegram.getState()
                });
                i++;
            }
        }

        final JTable table = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0: return String.class;
                    case 1: return String .class;
                    case 2: return Integer.class;
                    case 3: return Integer.class;
                    case 4: return Integer.class;
                    case 5: return Boolean.class;
                    default: return Object.class;
                }
            }
        };

        //устанавливаем цвет фона ячеек белым
        table.setBackground(Color.white);
        //добавляем скроллер в таблицу (так же это позволяет сделать видимыми название столбцов таблицы)
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 4, 5, 5));

        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //добавляем пустую строку в таблицу
                tableModel.addRow(new String[]{});
            }
        });

        JButton removeButton = new JButton("Удалить");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    tableModel.removeRow(row);
                } else {
                    UserNotifier.warningMessage("Строка не выбрана!", "Строка для удаления не выбрана!\n" +
                            "Пожалуйста выберите строку, а потом удаляйте!");
                }
            }
        });

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(new ActionListener() {
            //сохранить всю таблиыу в настройки
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Telegram> updatedTelegramList = new ArrayList<>();

                int rows = tableModel.getRowCount();
                for (int rowNumber = 0; rowNumber < rows; rowNumber++) {
                    Telegram telegram = new Telegram();

                    telegram.setHeader((String) tableModel.getValueAt(rowNumber, 0));
                    telegram.setDigitalHeader((String) tableModel.getValueAt(rowNumber, 1));
                    telegram.setBeginHour((Integer) tableModel.getValueAt(rowNumber, 2));
                    telegram.setBeginMin((Integer) tableModel.getValueAt(rowNumber, 3));
                    telegram.setPeriodInMin((Integer) tableModel.getValueAt(rowNumber, 4));
                    telegram.setState((Boolean) tableModel.getValueAt(rowNumber, 5));

                    updatedTelegramList.add(telegram);
                }

                SettingsHolder.updateTelegramList(updatedTelegramList);

                UserNotifier.infoMessage("Сохранение", "Настройки сохранены успешно");
            }
        });

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //просто делает окно настроек шаблоно невидимым
                setVisible(false);
                dispose();
            }
        });

        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        setLayout(new BorderLayout(5, 5));

        add(scrollPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension monitorScreenSize = toolkit.getScreenSize();
        int monitorWidth = monitorScreenSize.width;
        int monitorHeight = monitorScreenSize.height;
        //устанавливаем размер
        setSize(WIDTH, HEIGHT);
        //устанавливаем положение (координаты фрейма)
        setLocation(monitorWidth / 2 - WIDTH / 2, monitorHeight / 2 - HEIGHT / 2);
    }
}
