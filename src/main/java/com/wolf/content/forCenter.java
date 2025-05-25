package com.wolf.content;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class forCenter extends HBox {
    VBox center;
    public forCenter() {
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox empty1 = new VBox();
        setGrow(empty1);
        //empty1.setStyle("-fx-border-color: red;");

        center = new VBox(10);
        center.setStyle("-fx-padding: 10px;");

        VBox empty2 = new VBox();
        setGrow(empty2);
        //empty2.setStyle("-fx-border-color: red;");

        getChildren().addAll(empty1, center, empty2);
    }

    public VBox getCenter() {
        return center;
    }

    public void setCenter(VBox center) {
        this.center = center;
    }

    private void setGrow(VBox box) {
        HBox.setHgrow(box, Priority.ALWAYS);
        VBox.setVgrow(box, Priority.ALWAYS);
    }
}
