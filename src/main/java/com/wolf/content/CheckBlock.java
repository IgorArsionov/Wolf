package com.wolf.content;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CheckBlock extends VBox {
    public CheckBlock(String who, String sum, String check) {
        HBox.setHgrow(this, Priority.ALWAYS);
        setPrefHeight(200);

        setStyle("-fx-background-color: gray;");

        VBox title = new VBox();
        title.setPrefWidth(30);
        Label whoCreate = new Label(who + " : создал чек на сумму: " + sum);
        title.getChildren().add(whoCreate);

        VBox shortText = new VBox();
        HBox.setHgrow(shortText, Priority.ALWAYS);
        VBox.setVgrow(shortText, Priority.ALWAYS);
        String[] checks = checks(check);

        for (int i = 0; i < checks.length; i++) {
            Label thisCheck = new Label(checks[i]);
            double value = Double.parseDouble(checks[i].split(" : ")[1]);
            //Profile.setMoneyDraft();
            shortText.getChildren().add(thisCheck);
        }

        VBox buttonBox = new VBox();
        buttonBox.setPrefWidth(30);


        getChildren().addAll(title, shortText, buttonBox);
    }

    private String[] checks(String check) {
        return check.split(";");
    }
}
