package com.mr_faton.core;

import com.mr_faton.core.dao.TelegramControllerDAO;
import com.mr_faton.core.entity.DBServer;
import com.mr_faton.core.entity.Telegram;
import com.mr_faton.core.util.AlarmPlayer;
import com.mr_faton.core.util.SettingsHolder;
import com.mr_faton.gui.notifier.UserNotifier;
import com.mr_faton.gui.panel.ButtonPanel;
import com.mr_faton.gui.panel.NotificationPanel;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class TelegramController implements Runnable{
    private static final Logger logger = Logger.getLogger("" +
            "com.mr_faton.core.TelegramController");
    private final List<Telegram> enabledTelegrams = SettingsHolder.getEnableTelegramList();
    private final List<DBServer> enabledDBServers = SettingsHolder.getEnabledDbServerList();
    private final TelegramControllerDAO telegramControllerDAO;
    private final NotificationPanel notificationPanel;
    private final ButtonPanel buttonPanel;
    private final AlarmPlayer alarmPlayer;

    public TelegramController(TelegramControllerDAO telegramControllerDAO,
                              NotificationPanel notificationPanel,
                              ButtonPanel buttonPanel,
                              AlarmPlayer alarmPlayer) {
        this.telegramControllerDAO = telegramControllerDAO;
        this.notificationPanel = notificationPanel;
        this.buttonPanel = buttonPanel;
        this.alarmPlayer = alarmPlayer;

        if (enabledTelegrams == null || enabledTelegrams.isEmpty()) {
            UserNotifier.errorMessage("Список телеграмм", "Список телеграмм для работы пуст.<br/>" +
                    "Добавьте в список хотя бы одну телеграмму или включите уже имеющуюся.");
            buttonPanel.enableStartButton();
            buttonPanel.disableStopButton();
            return;
        }

        if (enabledDBServers == null || enabledDBServers.isEmpty()) {
            UserNotifier.errorMessage("Список серверов баз данных", "Список серверов баз данных для работы пуст.<br/>" +
                    "Добавьте в список хотя бы одну базу данных или включите уже имеющуюся.");
            buttonPanel.enableStartButton();
            buttonPanel.disableStopButton();
            return;
        }
    }

    @Override
    public void run() {
        logger.info("TelegramController is start");
        notificationPanel.addNotification("Начало работы");
        logger.debug("resolve next time for every enabled telegram from list " + enabledTelegrams);
        try {
            for (Telegram telegram : enabledTelegrams) {
                telegram.setNextTime(resolveNextTime(telegram));
            }

            Telegram nextTelegram;
            long sleepTime;
            long telegramTime;
            while (true) {
                nextTelegram = resolveNextTelegram(enabledTelegrams);
                telegramTime = nextTelegram.getNextTime();
                sleepTime = telegramTime - System.currentTimeMillis();

                if (sleepTime > 0) {
                    logger.info("next telegram is " + nextTelegram + " and it processed at " + convertTime(sleepTime));
                    notificationPanel.addNotification("Следующая телеграмма \"" + nextTelegram +
                            "\" и мы проверим её в " + convertTime(sleepTime));
                    Thread.sleep(sleepTime);
                }

                for (DBServer dbServer : enabledDBServers) {
                    notificationPanel.
                            addNotification("Проверяем телеграмму \"" + nextTelegram + "\" в БД \"" + dbServer + " \"");
                    if (!telegramControllerDAO.isTelegramExist(nextTelegram, dbServer)) {
                        logger.info("telegram" + nextTelegram + " is not exist on db server " + dbServer);
                        buttonPanel.enableNotifiedButton();
                        buttonPanel.disableStopButton();
                        notificationPanel.addWarningNotification("Телеграмма \"" + nextTelegram + "\" в БД \"" +
                                dbServer + "\" НЕ существует!");
                        alarmPlayer.play();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                UserNotifier.errorMessage("Пропущена телеграмма",
                                        "Была пропущена телеграмма, ознакомьтесь с деталями!");
                            }
                        }).start();

                    } else {
                        notificationPanel.
                                addNotification("Телеграмма \"" + nextTelegram + "\" есть в БД \"" + dbServer + "\"");
                        logger.info("telegram \"" + nextTelegram + "\" exists in DB \"" + dbServer + "\"");
                    }
                }

                nextTelegram.setNextTime(resolveNextTime(nextTelegram));
            }
        } catch (InterruptedException e) {
            logger.warn("TelegramController was interrupted");
            notificationPanel.addNotification("Работа прервана");
        } catch (Exception ex) {
            logger.warn("some exception", ex);
            notificationPanel.addNotification("Работа прервана");
        }
    }

    private long resolveNextTime(Telegram telegram) {
        logger.debug("resolve next time for telegram " + telegram);

        int beginHour = telegram.getBeginHour();
        int beginMin = telegram.getBeginMin();
        int periodInMin = telegram.getPeriodInMin();
        int delayMin = telegram.getDelayInMin();
        int resultMin = beginMin + delayMin;

        Calendar workTime = Calendar.getInstance();
        workTime.set(Calendar.HOUR_OF_DAY, beginHour);
        workTime.set(Calendar.MINUTE, resultMin);
        workTime.set(Calendar.SECOND, 0);
        workTime.set(Calendar.MILLISECOND, 0);

        long currentTime = System.currentTimeMillis();

        while (workTime.getTimeInMillis() <= currentTime) {
            workTime.add(Calendar.MINUTE, periodInMin);
        }

        return workTime.getTimeInMillis();
    }

    private Telegram resolveNextTelegram(List<Telegram> enabledTelegrams) {
        logger.debug("resolve next telegram");

        long minTime = Long.MAX_VALUE;
        long telegramTime;
        Telegram nextTelegram = null;
        for (Telegram telegram : enabledTelegrams) {
            telegramTime = telegram.getNextTime();
            if (minTime >= telegramTime) {
                minTime = telegramTime;
                nextTelegram = telegram;
            }
        }
        return nextTelegram;
    }

    private String convertTime(long sleepTime) {
        Date date = new Date(System.currentTimeMillis() + sleepTime);
        return String.format("%tH:%<tM:%<tS", date);
    }
}
