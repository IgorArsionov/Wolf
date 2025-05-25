package com.wolf.content;

import com.wolf.databass.DB;
import com.wolf.databass.MySQL;
import com.wolf.databass.Session;
import com.wolf.page.ListCheck;
import com.wolf.page.Profile;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Arrays;
import java.util.Locale;

public class AddPage extends VBox {
    private static TextField summ;
    private static TextField[] cost;
    private static CheckBox[] checkBoxes;
    private static double value;

    public static void addPage(Stage stage, ListCheck listCheck) {
        Stage addStage = new Stage();
        addStage.initOwner(stage);
        addStage.initModality(Modality.WINDOW_MODAL);
        addStage.initStyle(StageStyle.TRANSPARENT);
        ScrollPane pane = new ScrollPane();
        VBox gBox = new VBox(5);
        gBox.setPrefWidth(300);
        gBox.setPrefHeight(400);



        summ = new TextField();
        summ.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));
        gBox.getChildren().addAll(summ);
        gBox.setStyle("-fx-padding: 5px;");
        summ.setPromptText("Сумма чека");
        checkBoxes = new CheckBox[DB.getUsers().size()];
        cost = new TextField[DB.getUsers().size()];
        for (int i = 0; i < DB.getUsers().size(); i++) {
            checkBoxes[i] = new CheckBox(DB.getUsers().get(i));
            cost[i] = new TextField();
            cost[i].setTextFormatter(new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d*(\\.\\d*)?")) {
                    return change;
                }
                return null;
            }));
            cost[i].setDisable(true);
            HBox nextBox = new HBox(4);
            nextBox.getChildren().addAll(checkBoxes[i], cost[i]);
            gBox.getChildren().add(nextBox);
        }

        listen();

        Button submit = new Button("Сохранить");
        submit.setOnAction(e -> {
            MySQL.insert("check", Session.getSession(), Integer.parseInt(summ.getText()), getCheck(), cost);
            addStage.close();
            listCheck.update();
            Profile.setMoneyDraft();
        });
        gBox.getChildren().addAll(submit);

        pane.setContent(gBox);

        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                addStage.close();
            }
        });
        addStage.setScene(scene);
        addStage.show();
    }

    private static boolean[] isManual;

    private static void listen() {
        isManual = new boolean[checkBoxes.length];

        summ.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                try {
                    value = Double.parseDouble(newVal);
                    Arrays.fill(isManual, false); // сбрасываем ручной ввод
                    updateOthers(-1); // пересчет всех
                } catch (NumberFormatException e) {
                    // игнорируем
                }
            }
        });

        for (int i = 0; i < checkBoxes.length; i++) {
            int index = i;

            checkBoxes[i].selectedProperty().addListener((obs, oldVal, newVal) -> {
                cost[index].setDisable(!newVal);
                cost[index].setText("");
                isManual[index] = false;
                updateOthers(-1);
            });

            cost[i].textProperty().addListener((obs, oldVal, newVal) -> {
                if (!cost[index].isFocused()) return;

                try {
                    double val = Double.parseDouble(newVal);
                    isManual[index] = true;
                    updateOthers(index); // передаем, кто изменил
                } catch (NumberFormatException ignored) {}
            });
        }
    }

    private static void updateOthers(int changedIndex) {
        if (value == 0) return;

        double used = 0;
        int remaining = 0;

        for (int i = 0; i < cost.length; i++) {
            if (checkBoxes[i].isSelected()) {
                if (isManual[i]) {
                    try {
                        used += Double.parseDouble(cost[i].getText());
                    } catch (NumberFormatException ignored) {}
                } else {
                    remaining++;
                }
            }
        }

        double remainingPerPerson = remaining > 0 ? (value - used) / remaining : 0;

        for (int i = 0; i < cost.length; i++) {
            if (checkBoxes[i].isSelected() && !isManual[i]) {
                cost[i].setText(String.format(Locale.US,"%.2f", remainingPerPerson));
            }
        }
    }

    private static String getCheck() {
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < cost.length; i++) {
            String text = cost[i].getText();
            if (text != null && !text.isEmpty()) {
                value.append(DB.getUsers().get(i) + " : ").append(text).append(";");
            } else {
                value.append(DB.getUsers().get(i) + " : ").append("0.0").append(";");
            }
        }
        return value.toString();
    }

}
