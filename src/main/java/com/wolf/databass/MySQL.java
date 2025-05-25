package com.wolf.databass;

import javafx.scene.control.TextField;

import java.sql.*;
import java.util.ArrayList;

public class MySQL {

    private static final String URL = "jdbc:mysql://s8.thehost.com.ua:3306/home?useSSL=false&serverTimezone=UTC";
    private static final String USER = "IgorArs";
    private static final String PASS = "Homepass1";

    private static Connection connection;

    private MySQL() {}

    public static Connection getConnection() {

        if (connection == null) {
            try {

                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("✅ Подключение установлено.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static ResultSet select(String name) {
        try {
            Connection conn = MySQL.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE name = '" + name + "'");
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet selectFrom(String where, String from) {
        try {
            Connection conn = MySQL.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT "+where+" FROM `"+from+"`");
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet selectFrom(String where, String from, String nameDB, String name) {
        try {
            Connection conn = MySQL.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT "+where+" FROM `"+from+"` WHERE "+nameDB+" = '"+name+"'");
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insert(String from, String who, int sum, String check, TextField[] cost) {
        try {
            Connection conn = MySQL.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO `"+from+"` (who_create, all_sum, debtor) VALUES (?,?,?)");
            stmt.setString(1, who);
            stmt.setInt(2, sum);
            stmt.setString(3, check);
            stmt.executeUpdate();

            draftUpdate(conn, cost, who);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(String name, Double value) {
        System.out.println(name);
        System.out.println(value);
        try {
            Connection conn = MySQL.getConnection();
            PreparedStatement stmt2 = conn.prepareStatement("UPDATE `user` SET "+Session.getSession()+" = ? WHERE name = ?;");
            stmt2.setString(2, name);
            stmt2.setDouble(1, value);
            stmt2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void draftUpdate(Connection conn, TextField[] cost, String who) {
        ResultSet rs = MySQL.selectFrom("*", "user", "name", who);
        ArrayList<Double> value = new ArrayList<>();

        try {
            while (rs.next()) {
                value.add(rs.getDouble("Igor"));
                value.add(rs.getDouble("Kiril"));
                value.add(rs.getDouble("Vlad"));
            }
            PreparedStatement stmt2 = conn.prepareStatement("UPDATE `user` SET Igor = ?, Kiril = ?, Vlad = ? WHERE name = ?;");

            stmt2.setDouble(1, value.get(0) + Empty(cost[0].getText(), cost[0]));
            stmt2.setDouble(2, value.get(1) + Empty(cost[1].getText(), cost[1]));
            stmt2.setDouble(3, value.get(2) + Empty(cost[2].getText(), cost[2]));
            stmt2.setString(4, who);
            stmt2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static double Empty(String val, TextField cost) {
        return Double.parseDouble(cost.getText().isEmpty() ? String.valueOf(0) :cost.getText());
    }
}
