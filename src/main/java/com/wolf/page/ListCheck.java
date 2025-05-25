package com.wolf.page;

import com.wolf.HelloApplication;
import com.wolf.content.CheckBlock;
import com.wolf.databass.MySQL;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListCheck extends ScrollPane {
    private final Button add;
    VBox box;
    public ListCheck() {
        box = new VBox(10);
        box.setStyle("-fx-background-color: transparent; -fx-padding: 20px;");
        setContent(box);
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(box, Priority.ALWAYS);
        VBox.setVgrow(box, Priority.ALWAYS);
        box.setFillWidth(true);
        box.prefWidthProperty().bind(widthProperty());

        add = new Button("Добавить");
        add.getStyleClass().setAll("add-button");
        add.prefWidthProperty().bind(widthProperty());
        add.setOnAction(e -> {
            HelloApplication.addPage(this);
        });
        box.getChildren().add(add);

        update();
    }

    public void update() {
        box.getChildren().clear();
        box.getChildren().add(add);
        ArrayList<CheckBlock> blocks = new ArrayList<>();
        ResultSet resultSet = MySQL.selectFrom("*", "check");
        try {
            while (resultSet.next()) {
                CheckBlock block = new CheckBlock(resultSet.getString("who_create"), resultSet.getString("all_sum"), resultSet.getString("debtor"));
                blocks.add(block);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i = blocks.size() - 1; i > 0; i--) {
            box.getChildren().add(blocks.get(i));
        }
    }
}
