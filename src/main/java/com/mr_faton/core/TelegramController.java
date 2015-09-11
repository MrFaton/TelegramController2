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
import java.util.List;

public class TelegramController implements Runnable{
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
    }

    @Override
    public void run() {
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
                sleepTime = System.currentTimeMillis() - telegramTime;

                String message = "Следующая телеграмма " + nextTelegram + " и она обработается ";
                if (sleepTime > 0) {
                    message = message + "через " + sleepTime / 60_000 + " мин";
                    Thread.sleep(sleepTime);
                } else {
                    message = message + "сейчас";
                }
                notificationPanel.addNotification(message);

                for (DBServer dbServer : enabledDBServers) {
                    if (!telegramControllerDAO.isTelegramExist(nextTelegram, dbServer)) {
                        buttonPanel.enableNotifiedButton();
                        buttonPanel.disableStopButton();
                        notificationPanel.addWarningNotification("Телеграмма " + nextTelegram + " пропущена");
                        alarmPlayer.play();
                        UserNotifier.errorMessage("Пропущена телеграмма",
                                "Была пропущена телеграмма, ознакомьтесь с деталями!");
                    }
                    notificationPanel.addNotification("Телеграмма " + nextTelegram + " прошла успешно");
                }

                nextTelegram.setNextTime(resolveNextTime(nextTelegram));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long resolveNextTime(Telegram telegram) {
        int beginHour = telegram.getBeginHour();
        int beginMin = telegram.getBeginMin();
        int periodInMin = telegram.getPeriodInMin();

        Calendar workTime = Calendar.getInstance();
        workTime.set(Calendar.HOUR_OF_DAY, beginHour);
        workTime.set(Calendar.MINUTE, beginMin);

        long currentTime = System.currentTimeMillis();

        while (workTime.getTimeInMillis() <= currentTime) {
            workTime.add(Calendar.MINUTE, periodInMin);
        }

        return workTime.getTimeInMillis() - currentTime;
    }

    private Telegram resolveNextTelegram(List<Telegram> enabledTelegrams) {
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
}
