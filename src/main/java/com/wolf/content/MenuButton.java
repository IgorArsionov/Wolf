package com.wolf.content;

import javafx.scene.control.Button;

public class MenuButton extends Button {
    public MenuButton(String name) {
        super(name);
        getStyleClass().setAll("menu-button");
    }

}
