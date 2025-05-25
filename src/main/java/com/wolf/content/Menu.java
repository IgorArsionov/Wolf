package com.wolf.content;

import com.wolf.page.Profile;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class Menu extends HBox {
    Profile profil;
    public Menu(int val) {
        super(val);
        setPrefHeight(30);
        HBox.setHgrow(this, Priority.ALWAYS);
        setStyle("-fx-padding: 5px;");

        MenuButton check = new MenuButton("Чеки");
        MenuButton miniGames = new MenuButton("Игры");
        MenuButton credits = new MenuButton("Долги");
        MenuButton chat = new MenuButton("Чат");
        MenuButton wish = new MenuButton("WishList");
        MenuButton profile = new MenuButton("\uD83D\uDC64");

        check.setOnAction(e -> {
            GeneralBox.showCard("check");
        });
        profile.setOnAction(e -> {
            GeneralBox.showCard("Profile");
            profil.update();
        });

        getChildren().addAll(check, miniGames, credits, chat, wish, profile);
    }

    public void updateProfile(Profile profile) {
        this.profil = profile;
    }
}
