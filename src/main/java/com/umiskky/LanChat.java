package com.umiskky;

import com.umiskky.config.ConfigParse;
import com.umiskky.factories.ModelFactory;
import com.umiskky.factories.ViewHandler;
import com.umiskky.factories.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 * @author umiskky
 */
public class LanChat extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ConfigParse.configParseInit();
        ModelFactory modelFactory = new ModelFactory();
        ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
        ViewHandler viewHandler = new ViewHandler(stage, viewModelFactory);
        viewHandler.start();
    }
}