package org.umiskky;

import javafx.application.Application;
import javafx.stage.Stage;
import org.umiskky.factories.ModelFactory;
import org.umiskky.factories.ServiceDispatcher;
import org.umiskky.factories.ViewHandler;
import org.umiskky.factories.ViewModelFactory;
import org.umiskky.service.InitService;

/**
 * JavaFX App
 * @author umiskky
 */
public class LanChat extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ModelFactory modelFactory = new ModelFactory();
        ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
        ViewHandler viewHandler = new ViewHandler(stage, viewModelFactory);
        viewHandler.start();
        ServiceDispatcher.setViewModelFactory(viewModelFactory);
        ServiceDispatcher.submitTask(new InitService());
    }
}