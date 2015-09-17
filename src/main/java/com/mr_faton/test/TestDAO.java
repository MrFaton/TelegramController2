package com.mr_faton.test;

import com.mr_faton.core.context.AppContext;
import com.mr_faton.core.dao.TelegramControllerDAO;
import com.mr_faton.core.entity.DBServer;
import com.mr_faton.core.entity.Telegram;

import java.sql.SQLException;

/**
 * Created by root on 17.09.2015.
 */
public class TestDAO {
    public static void main(String[] args) throws SQLException {
        TelegramControllerDAO telegramControllerDAO = (TelegramControllerDAO) AppContext.getBeanByName("telegramControllerDAO");

        Telegram telegram = new Telegram();
        telegram.setHeader("САУР40 КИХА");
        telegram.setDigitalHeader("022 870281");
        telegram.setBeginHour(0);
        telegram.setBeginMin(30);
        telegram.setPeriodInMin(60);
        telegram.setDelayInMin(5);
        telegram.setState(true);

        DBServer dbServer = new DBServer();
        dbServer.setName("Test Server");
        dbServer.setUrl("jdbc:mysql://192.168.101.2:3306/brizdb");
        dbServer.setUser("engineer");
        dbServer.setUserPassword("qwertyui");
        dbServer.setState(true);

        System.out.println(telegramControllerDAO.isTelegramExist(telegram, dbServer));
    }
}
