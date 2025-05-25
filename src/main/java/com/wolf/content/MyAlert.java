package com.wolf.content;

import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyAlert extends VBox {
    public MyAlert(Stage stage, String text) {
        Stage alertStage = new Stage();
        alertStage.initModality(Modality.WINDOW_MODAL);
        alertStage.initOwner(stage);
        alertStage.initStyle(StageStyle.TRANSPARENT);

        getStyleClass().setAll("alert-box");

        VBox gBox = new VBox();
        gBox.getStyleClass().setAll("alert-box");
        Label label = new Label(text);
        label.getStyleClass().setAll("alert-label");
        gBox.getChildren().add(label);

        //alertStage.setWidth(200);
        //alertStage.setHeight(20);

        Scene scene = new Scene(gBox);
        scene.getStylesheets().add(getClass().getResource("/Style/style.css").toExternalForm());
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        alertStage.setScene(scene);
        alertStage.show();
        close(alertStage);
    }

    private void close(Stage stage) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
                javafx.application.Platform.runLater(stage::close);
                return null;
            }
        };

        new Thread(task).start();
    }
}
