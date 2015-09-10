package com.mr_faton.gui.dialog;

import com.mr_faton.core.context.AppContext;
import com.mr_faton.core.entity.Telegram;
import com.mr_faton.core.util.SettingsHolder;
import com.mr_faton.gui.frame.MainFrame;
import com.mr_faton.gui.notifier.UserNotifier;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
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
            }
        }

        final JTable table = new JTable(tableModel);
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
                if (telegramList == null) telegramList = new ArrayList<>();

                Vector dataVector = tableModel.getDataVector();

                System.out.println(dataVector);

//                //remove telegram who was deleted as row
//                int rows = tableModel.getRowCount();
//                for (int i = 0; i < rows; i++) {
//                    String telegramHeaderInTable = tableModel.getValueAt(i, 0).toString();
//                    for (Telegram telegram : telegramList) {
//                        String telegramHeaderInList = tele
//                    }
//                }
//
//                //создаём мапу, в которой будет храниться таблица (ключ: названик карты, значение: заголовок карты)
//                Map<String, String> allPatternsMap = new LinkedHashMap<>();
//                //определяем кол-во строк
//                int rowCount = table.getRowCount();
//                //запускаем цикл по каждой строке
//                for (int row = 0; row < rowCount; row++) {
//                    //получем имя карты из текущей строки
//                    String mapName = (String) tableModel.getValueAt(row, 0);
//                    //если его нет либо оно пустое, показываем пользователю сообщение о том, что ячейка пустая
//                    if (mapName == null || mapName.length() == 0) {
//                        showMessage(row + 1, table.getColumnName(0));
//                        return;
//                    }
//                    //получаем заголовок карты из текущей строки
//                    String mapHeader = (String) tableModel.getValueAt(row, 1);
//                    //если его нет либо оно пустое, показываем пользователю сообщение о том, что ячейка пустая
//                    if (mapHeader == null || mapHeader.length() == 0) {
//                        showMessage(row + 1, table.getColumnName(1));
//                        return;
//                    }
//                    //ложем в нашу мапу имя карты и её заголовок
//                    allPatternsMap.put(mapName, mapHeader);
//                }
//                //передаём нашему обработчику xml-файла с настройками мапу шаблонов для сохранения в xml
//                settingsWorker.setPatterns(allPatternsMap);
//                //сохранить настройки в xml-файл, true - чтобы показать сообщение об успешном сохранении
//                settingsWorker.saveSettings(true);
//            }
//
//            //конструирует объект с предупреждающим сообщением (если есть пустая ячейка)
//            private void showMessage(int row, String columnName) {
//                new WarningMessenger("Пустая ячейка!",
//                        "Не не не, мы не будем сохранять данные,\n" +
//                                "когда ячейка в строке № \"" + row + "\" и столбце \"" + columnName + "\" пустая.\n" +
//                                "Вернитесь к таблице и заполните пустую ячейку!"
//                );
            }
        });

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //просто делает окно настроек шаблоно невидимым
                setVisible(false);
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
