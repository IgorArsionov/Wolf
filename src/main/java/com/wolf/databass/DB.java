package com.wolf.databass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB {
    private static ArrayList<String> users = new ArrayList<>();
    public static void setAllUser() {
        ResultSet rs = MySQL.selectFrom("*", "user");

        try {
            while (rs.next()) {
                users.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static ArrayList<String> getUsers() {
        return users;
    }
}
