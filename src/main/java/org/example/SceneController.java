package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainRecord.fxml"));
        Parent scene = loader.load();

        SystemImpl manageScene = loader.getController();
        manageScene.setPrimaryStage(stage);
        manageScene.setMainSwitch(scene);
        stage.setTitle("ATTENDANCE RECORDS");
        stage.setScene(new Scene(scene, 1300, 690));
        stage.show();
    }

}
