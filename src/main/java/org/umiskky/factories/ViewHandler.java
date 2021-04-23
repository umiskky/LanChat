package org.umiskky.factories;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.umiskky.view.ChatViewController;
import org.umiskky.view.LoginViewController;

import java.io.IOException;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
public class ViewHandler {

    private Stage stage;
    private ViewModelFactory viewModelFactory;

    public ViewHandler(Stage stage, ViewModelFactory viewModelFactory){
        this.stage = stage;
        this.viewModelFactory = viewModelFactory;
    }

    public void start() throws Exception{
        stage.initStyle(StageStyle.UNDECORATED);
        openView("Login");
    }

    public void openView(String viewToOpen) throws IOException {
        Scene scene = null;
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        loader.setLocation(getClass().getResource("/org/umiskky/view/" + viewToOpen + "View.fxml"));
        root = loader.load();
        switch (viewToOpen){
            case "Login":
                LoginViewController loginViewController = loader.getController();
                loginViewController.init(viewModelFactory.getLoginViewModel());
                stage.setTitle("Login");
                break;
            case "Chat":
                ChatViewController chatViewController = loader.getController();
                chatViewController.init(viewModelFactory.getChatViewModel(),root);
                stage.setTitle("Chat");
                break;
            default:
                break;
        }

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public Stage getStage() {
        return this.stage;
    }

    public ViewModelFactory getViewModelFactory() {
        return this.viewModelFactory;
    }
}
