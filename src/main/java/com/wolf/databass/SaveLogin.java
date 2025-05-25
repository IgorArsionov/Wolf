package com.wolf.databass;

import java.io.*;

public class SaveLogin {
    private static File file = new File("login.txt");
    public static void saveLogin(String login, String pass) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("Файл хранения создан");
                }
            }
            writer.write(String.format("%s%n%s%n", login, pass));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getLogin() {
        if (!file.exists()) {
            return new String[2];
        }
        String[] value = new String[2];
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            for (int i = 0; i < 2; i++) {
                value[i] = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
