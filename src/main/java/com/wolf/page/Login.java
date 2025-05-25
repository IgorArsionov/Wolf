package com.wolf.page;

import com.wolf.HelloApplication;
import com.wolf.content.forCenter;
import com.wolf.databass.SaveLogin;
import com.wolf.databass.Session;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Login extends VBox {
    PasswordField pass;
    TextField text;
    public Login() {
        forCenter top = new forCenter();
        top.setPrefHeight(100);

        forCenter center = new forCenter();
        center.setPrefHeight(200);
        String[] value = SaveLogin.getLogin();
        center.getCenter().getChildren().addAll(textField(value[0]), passwordField(value[1]), loginButton());
        center.getCenter().setPrefHeight(200);
        center.getCenter().setPrefWidth(200);

        getChildren().addAll(top, center);

    }

    private TextField textField(String val) {
        text = new TextField(val);
        text.getStyleClass().setAll("custom-textfield");
        text.setPromptText("Login");
        return text;
    }

    private PasswordField passwordField(String val) {
        pass = new PasswordField();
        pass.setText(val);
        pass.getStyleClass().setAll("custom-textfield");
        pass.setPromptText("Password");
        return pass;
    }

    private Button loginButton() {
        Button button = new Button("Login");
        button.getStyleClass().setAll("login-button");
        button.setPrefWidth(200);

        button.setOnAction(e -> {
            if (Session.setSession(text.getText(), pass.getText())) {
                HelloApplication.showCard("General");
                SaveLogin.saveLogin(text.getText(), pass.getText());
            }
        });

        return button;
    }
}
