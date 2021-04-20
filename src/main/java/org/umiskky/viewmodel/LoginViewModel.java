package org.umiskky.viewmodel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.umiskky.model.DataModel;
import javafx.scene.control.*;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
@Getter
public class LoginViewModel {

    private DataModel dataModel;
    private StringProperty account;
    private Button headPortrait;

    /**
     * @author Tee
     * @apiNote Learned from umiskky:create a new object for the properties that will be bind
     *          preventing from facing the NonPionterException!!!
     */
    public LoginViewModel(DataModel dataModel) {
        this.dataModel = dataModel;
        vmPropertiesInit();
    }

    public void vmPropertiesInit(){
        account = new SimpleStringProperty();
        headPortrait = new Button();
    }

    /**
     * @author Tee
     * @apiNote this method is used to close the existing window
     */
    public static void quit() {
        //close();
        System.exit(0);
    }

    /**
     * @author Tee
     * @apiNote this method is used to minimise the existing window(not yet)
     */
    public static void minimise(){
        //setIconified(true);
    }

    /**
     * @author Tee
     * @apiNote this method is used to choose head
     */
    public static Button chooseHead(Button headPortrait,String head){
        headPortrait.setStyle(String.format("-fx-background-image: url('org/umiskky/view/Image/head/%s.jpg')",head));
        //headPortrait.setStyle("-fx-background-image: url('')");
        return headPortrait;
    }

    /**
     * @author Tee
     * @apiNote this method is used to login and go to the main window
     */
    public static void login(Button login){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(LoginViewModel.class.getResource("/org/umiskky/view/ChatView.fxml")));
            Stage chat = new Stage();
            chat.setTitle("LANChat");
            Scene scene = new Scene(root);
            chat.setScene(scene);
            chat.show();
            Stage now = (Stage)login.getScene().getWindow();
            now.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
