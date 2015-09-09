package com.mr_faton.core.util;

import com.mr_faton.core.entity.DBServer;
import com.mr_faton.core.entity.Telegram;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class SettingsHolder {
    private static final String TELEGRAM_LIST_FILE = "TelegramList.ser";
    private static final String DB_SERVER_LIST_FILE = "DBServerList.ser";
    private static List<Telegram> telegramList = null;
    private static List<DBServer> dbServerList = null;

    // ***** Load / Save *****
    public static void load() {
        try {
            telegramList = (List<Telegram>) loadObject(TELEGRAM_LIST_FILE);
            dbServerList = (List<DBServer>) loadObject(DB_SERVER_LIST_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            saveObject(telegramList, TELEGRAM_LIST_FILE);
            saveObject(dbServerList, DB_SERVER_LIST_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ***** Getters *****

    public static List<Telegram> getTelegramList() {
        return telegramList;
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
