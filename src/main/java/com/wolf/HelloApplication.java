package com.wolf;

import com.wolf.content.AddPage;
import com.wolf.content.GeneralBox;
import com.wolf.content.MyAlert;
import com.wolf.databass.DB;
import com.wolf.page.ListCheck;
import com.wolf.page.Login;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class HelloApplication extends Application {
    private static HashMap<String, Node> panels = new HashMap<>();
    private static StackPane pane;
    private static Stage alertStage;

    @Override
    public void start(Stage stage) throws IOException {
        DB.setAllUser();
        alertStage = stage;
        stage.setTitle("Hello!");
        pane = new StackPane();
        pane.getStyleClass().setAll("root");
        //GeneralBox generalBox = new GeneralBox();

        panels.put("Login", new Login());
        panels.put("General", new GeneralBox());

        showCard("Login");
        stage.setHeight(700);
        stage.setWidth(500);
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("/Style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void showCard(String name) {
        Node panel = panels.get(name);
        pane.getChildren().setAll(panel);
    }

    public static void myAlert(String text) {
        new MyAlert(alertStage, text);
    }

    public static void addPage(ListCheck listCheck) {
        AddPage.addPage(alertStage, listCheck);
    }
}