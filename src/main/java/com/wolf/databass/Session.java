package com.wolf.databass;

import com.wolf.HelloApplication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Session {
    public static String session;
    private static Connection conn = MySQL.getConnection();


    public static String getSession() {
        return session;
    }

    public static boolean setSession(String session, String password) {
        if (!session.isEmpty() && session.length() <= 20) {
            try {
                ResultSet rs = getResult(session);
                if (rs.next()) {
                    System.out.println("Логин найден");
                    if (rs.getString("password").equals(password)) {
                        Session.session = session;
                        return true;
                    } else {
                        HelloApplication.myAlert("Неверный пароль");
                    }
                } else {
                    HelloApplication.myAlert("Неверный логин");
                    System.out.println("Логин не найден");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static ResultSet getResult(String name) {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM user WHERE name = '" + name + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
}
