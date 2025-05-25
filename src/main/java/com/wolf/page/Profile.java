package com.wolf.page;

import com.wolf.databass.DB;
import com.wolf.databass.MySQL;
import com.wolf.databass.Session;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Profile extends VBox {
    private static double money = 0.0;
    private static double[] moneyDraft;
    private static ArrayList<String> profiles = DB.getUsers();
    public Profile() {
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);
        moneyDraft = new double[profiles.size()];
    }

    public void update() {
        getChildren().clear();
        VBox moneyBox = new VBox(10);
        Label moneyLabel = new Label(String.format("Баланс: %.2f", money));
        moneyBox.getChildren().add(moneyLabel);
        setMoneyDraft();
        for (int i = 0; i < moneyDraft.length; i++) {
            if (!profiles.get(i).equals(Session.getSession())) {
                Label moneyDraftLabel = new Label(String.format("Ты должен: %s : %.2f", profiles.get(i), moneyDraft[i]));
                TextField pay = new TextField();
                pay.setTextFormatter(new TextFormatter<>(change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("\\d*(\\.\\d*)?")) {
                        return change;
                    }
                    return null;
                }));
                Button buttonPay = new Button("Отдать");
                String name = profiles.get(i);
                double val = moneyDraft[i];
                buttonPay.setOnAction(e -> {
                    double value = Double.parseDouble(pay.getText());
                    double raz = val - value > 0 ? val-value:0;
                    MySQL.update(name, raz);
                    update();
                });

                HBox box = new HBox(5);
                box.getChildren().addAll(moneyDraftLabel, pay, buttonPay);
                moneyBox.getChildren().add(box);
            }
        }
        getChildren().add(moneyBox);
    }

    public static double getMoney() {
        return money;
    }

    public static void setMoney(double money) {
        Profile.money = money;
    }

    public static void setMoneyDraft() {
        ResultSet rs = MySQL.selectFrom("*", "user");
        Arrays.fill(moneyDraft, 0);
        try {
            int i = 0;
            while (rs.next()) {
                moneyDraft[i] += rs.getDouble(Session.getSession());
                System.out.println(moneyDraft[i]);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
