package com.mr_faton.core.util;

import com.mr_faton.core.entity.DBServer;
import com.mr_faton.core.entity.Telegram;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SettingsHolder {
    private static final Logger logger = Logger.getLogger("" +
            "com.mr_faton.core.util.SettingsHolder");
    private static final String TELEGRAM_LIST_FILE = "TelegramList.ser";
    private static final String DB_SERVER_LIST_FILE = "DBServerList.ser";
    private static List<Telegram> telegramList = null;
    private static List<DBServer> dbServerList = null;

    // ***** Load / Save *****
    public static void load() {
        logger.debug("loading telegram list and db server list");
        try {
            telegramList = (List<Telegram>) loadObject(TELEGRAM_LIST_FILE);
            dbServerList = (List<DBServer>) loadObject(DB_SERVER_LIST_FILE);
        } catch (Exception e) {
            logger.warn("exception while loading lists", e);
        }
    }

    public static void save() {
        logger.debug("save telegram list and db server list");
        try {
            saveObject(telegramList, TELEGRAM_LIST_FILE);
            saveObject(dbServerList, DB_SERVER_LIST_FILE);
        } catch (Exception e) {
            logger.warn("exception while saving lists", e);
        }
    }

    // ***** Setters *****
    public static void updateTelegramList(List<Telegram> updatedTelegramList) {
        telegramList = updatedTelegramList;
        logger.debug("telegram list updated");
    }

    public static void updateDBServerList(List<DBServer> updatedDBServerList) {
        dbServerList = updatedDBServerList;
        logger.debug("db server list updated");
    }

    // ***** Getters *****

    public static List<Telegram> getTelegramList() {
//        return Collections.unmodifiableList(telegramList);
        logger.debug("return telegram list");
        return telegramList;
    }

    public static List<Telegram> getEnableTelegramList() {
        if (telegramList == null || telegramList.isEmpty()) {
            logger.warn("telegram list (" + telegramList + ") = null or empty");
            return null;
        }
        List<Telegram> enableTelegramList = new ArrayList<>();
        for (Telegram telegram : telegramList) {
            if (telegram.getState()) enableTelegramList.add(telegram);
        }

        logger.debug("return enabled telegram list");
        return enableTelegramList;
    }

    public static List<DBServer> getDbServerList() {
        logger.debug("return db server list list");
        return dbServerList;
    }

    public static List<DBServer> getEnabledDbServerList() {
        if (dbServerList == null || dbServerList.isEmpty()) {
            logger.warn("dbServerList list (" + dbServerList + ") = null or empty");
            return null;
        }
        List<DBServer> enabledDBServerList = new ArrayList<>();
        for (DBServer dbServer : dbServerList) {
            if (dbServer.getState()) enabledDBServerList.add(dbServer);
        }

        logger.debug("return enabled db server list");
        return enabledDBServerList;
    }

    //     ***** Service *****

    private static Object loadObject(String FILE_NAME) throws Exception{
        try(ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return objectIn.readObject();
        }
    }

    private static void saveObject(Object object, String FILE_NAME) throws Exception {
        try(ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            objectOut.writeObject(object);
            objectOut.flush();
        }
    }
}
