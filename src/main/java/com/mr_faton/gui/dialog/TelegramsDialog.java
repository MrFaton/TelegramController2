package com.mr_faton.gui.dialog;

import com.mr_faton.core.context.AppContext;
import com.mr_faton.core.entity.Telegram;
import com.mr_faton.core.util.SettingsHolder;
import com.mr_faton.gui.frame.MainFrame;
import com.mr_faton.gui.notifier.UserNotifier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class TelegramsDialog extends JDialog {
    private static int WIDTH = 1000;
    private static int HEIGHT = 280;
    private List<Telegram> telegramList;

    public TelegramsDialog() {
        super((MainFrame) AppContext.getBeanByName("mainFrame"), "Настройки телеграмм", true);

        telegramList = SettingsHolder.getTelegramList();

        String[] columnHeaders = {
                "Заголовок",
                "Цифр заголовок",
                "Час начала",
                "Мин начала",
                "Задержка в мин",
                "Час окончания",
                "Мин окончания",
                "Период в мин",
                "Круглосуточно",
                "ПН",
                "ВТ",
                "СР",
                "ЧТ",
                "ПТ",
                "СБ",
                "ВС",
                "Включен"};
        final DefaultTableModel tableModel = new DefaultTableModel(0, 17);
        tableModel.setColumnIdentifiers(columnHeaders);

        if (telegramList != null) {
            int i = 0;
            for (Telegram telegram : telegramList) {
                tableModel.insertRow(i, new Object[] {
                        telegram.getHeader(),
                        telegram.getDigitalHeader(),
                        telegram.getBeginHour(),
                        telegram.getBeginMin(),
                        telegram.getDelayInMin(),
                        telegram.getStopHour(),
                        telegram.getStopMin(),
                        telegram.getPeriodInMin(),
                        telegram.isAroundTheClock(),
                        telegram.isMonday(),
                        telegram.isTuesday(),
                        telegram.isWednesday(),
                        telegram.isThursday(),
                        telegram.isFriday(),
                        telegram.isSaturday(),
                        telegram.isSunday(),
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
                    case 5: return Integer.class;
                    case 6: return Integer.class;
                    case 7: return Integer.class;
                    case 8: return Boolean.class;
                    case 9: return Boolean.class;
                    case 10: return Boolean.class;
                    case 11: return Boolean.class;
                    case 12: return Boolean.class;
                    case 13: return Boolean.class;
                    case 14: return Boolean.class;
                    case 15: return Boolean.class;
                    case 16: return Boolean.class;
                    default: return Object.class;
                }
            }
        };

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(60);
        columnModel.getColumn(9).setPreferredWidth(30);
        columnModel.getColumn(10).setPreferredWidth(30);
        columnModel.getColumn(11).setPreferredWidth(30);
        columnModel.getColumn(12).setPreferredWidth(30);
        columnModel.getColumn(13).setPreferredWidth(30);
        columnModel.getColumn(14).setPreferredWidth(30);
        columnModel.getColumn(15).setPreferredWidth(30);
        columnModel.getColumn(16).setPreferredWidth(60);

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
                int lastRow = tableModel.getRowCount() - 1;
                int lastColumn = tableModel.getColumnCount();
                for (int column = 8; column < lastColumn; column++ ) {
                    tableModel.setValueAt(false, lastRow, column);
                }
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
                if (!cellValidator(tableModel)) return;

                List<Telegram> updatedTelegramList = new ArrayList<>();

                int rows = tableModel.getRowCount();
                for (int rowNumber = 0; rowNumber < rows; rowNumber++) {
                    Telegram telegram = new Telegram();

                    telegram.setHeader((String) tableModel.getValueAt(rowNumber, 0));
                    telegram.setDigitalHeader((String) tableModel.getValueAt(rowNumber, 1));
                    telegram.setBeginHour((Integer) tableModel.getValueAt(rowNumber, 2));
                    telegram.setBeginMin((Integer) tableModel.getValueAt(rowNumber, 3));
                    telegram.setDelayInMin((Integer) tableModel.getValueAt(rowNumber, 4));
                    telegram.setStopHour((Integer) tableModel.getValueAt(rowNumber, 5));
                    telegram.setStopMin((Integer) tableModel.getValueAt(rowNumber, 6));
                    telegram.setPeriodInMin((Integer) tableModel.getValueAt(rowNumber, 7));
                    telegram.setAroundTheClock((Boolean) tableModel.getValueAt(rowNumber, 8));
                    telegram.setMonday((Boolean) tableModel.getValueAt(rowNumber, 9));
                    telegram.setTuesday((Boolean) tableModel.getValueAt(rowNumber, 10));
                    telegram.setWednesday((Boolean) tableModel.getValueAt(rowNumber, 11));
                    telegram.setThursday((Boolean) tableModel.getValueAt(rowNumber, 12));
                    telegram.setFriday((Boolean) tableModel.getValueAt(rowNumber, 13));
                    telegram.setSaturday((Boolean) tableModel.getValueAt(rowNumber, 14));
                    telegram.setSunday((Boolean) tableModel.getValueAt(rowNumber, 15));
                    telegram.setState((Boolean) tableModel.getValueAt(rowNumber, 16));

                    updatedTelegramList.add(telegram);
                }

                SettingsHolder.updateTelegramList(updatedTelegramList);

                UserNotifier.infoMessage("Сохранение", "Настройки сохранены успешно");
            }
        });

        JButton cancelButton = new JButton("Закрыть");
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

    private boolean cellValidator(final TableModel tableModel) {
        int rows = tableModel.getRowCount();
        int columns = tableModel.getColumnCount();

        for (int curRow = 0; curRow < rows; curRow++) {
            for (int curColumn = 0; curColumn < columns; curColumn++) {
                Object cellValue = tableModel.getValueAt(curRow, curColumn);
                if (cellValue == null || cellValue.toString().length() == 0) {
                    UserNotifier.warningMessage("Пустая ячейка",
                            "Ячейка в строке №" + (++curRow) + " и столбце №" + (++curColumn) + " не заполнена.<br/>" +
                                    "Заполните ячейку или снимите с неё выделение.");
                    return false;
                }
            }
        }

        return true;
    }
}
