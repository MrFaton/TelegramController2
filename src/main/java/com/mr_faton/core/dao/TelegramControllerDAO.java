package com.mr_faton.core.dao;

import com.mr_faton.core.entity.DBServer;
import com.mr_faton.core.entity.Telegram;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.Calendar;

public class TelegramControllerDAO {
    private static final Logger logger = Logger.getLogger("" +
            "com.mr_faton.core.dao.TelegramControllerDAO");
    private static final String SQL = "SELECT title FROM brizdb WHERE digit_title = ? AND p_date >= ?;";

    public TelegramControllerDAO() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.warn("JDBC Driver class not found", e);
            throw e;
        }
    }

    public boolean isTelegramExist(Telegram telegram, DBServer dbServer) throws SQLException {
        boolean found = false;

        final String jdbcURL = dbServer.getUrl();
        final String user = dbServer.getUser();
        final String password = dbServer.getUserPassword();

        final String header = telegram.getHeader();
        final String digitalHeader = telegram.getDigitalHeader();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -(telegram.getBeginMin()));
        final Date thresholdDate = new Date(calendar.getTimeInMillis());

        Connection connection = DriverManager.getConnection(jdbcURL, user, password);
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

        preparedStatement.setString(1, digitalHeader);
        preparedStatement.setDate(2, thresholdDate);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String extractedHeader = koi8Decoder(resultSet.getBytes("title"));
            if (extractedHeader.equals(header)) {
                found = true;
                break;
            }
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return found;
    }

    private String koi8Decoder(byte[] bytes) {
        try {
//            return new String(bytes, "KOI8-R");
            return new String(bytes, "UTF8");
        } catch (UnsupportedEncodingException e) {
            logger.warn("unsupported encoding!", e);
        }
        return "";
    }
}
