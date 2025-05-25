package com.wolf.content;

import com.wolf.page.ListCheck;
import com.wolf.page.Profile;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class GeneralBox extends VBox {
    private static StackPane pane;
    private static HashMap<String, Node> pages = new HashMap<>();
    public Profile profile;
    public GeneralBox(int v) {
        super(v);
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    public GeneralBox() {
        profile = new Profile();
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);

        Menu menu = new Menu(5);
        menu.updateProfile(profile);
        pane = new StackPane();
        //pane.setStyle("-fx-border-color: yellow;");
        HBox.setHgrow(pane, Priority.ALWAYS);
        VBox.setVgrow(pane, Priority.ALWAYS);

        pages.put("check", new ListCheck());
        pages.put("Profile", profile);
        ScrollPane scroll = new ScrollPane();

        getChildren().addAll(menu, pane);
    }

    public static void showCard(String name) {
        Node page = pages.get(name);
        pane.getChildren().setAll(page);
    }


}
