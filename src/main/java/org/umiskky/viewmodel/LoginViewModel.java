package org.umiskky.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.umiskky.factories.ViewModelFactory;
import org.umiskky.model.DataModel;
import org.umiskky.model.dao.LocalUserDAO;
import org.umiskky.service.task.InitTask;
import org.umiskky.view.ChatViewController;
import org.umiskky.view.LoginViewController;

import java.io.IOException;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
public class LoginViewModel {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LoginViewModel.class);
    private static ViewModelFactory viewModelFactory1;
    private DataModel dataModel;
    public static StringProperty account;
    private Button headPortrait;

    /**
     * @author Tee
     * @apiNote Learned from umiskky:create a new object for the properties that will be bind
     *          preventing from facing the NonPionterException!!!
     */
    public LoginViewModel(DataModel dataModel,ViewModelFactory viewModelFactory) {
        viewModelFactory1 = viewModelFactory;
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
            InitTask.localUser.setNickname(account.get());
            InitTask.localUser.setAvatarId(Integer.parseInt(LoginViewController.headid));
            LocalUserDAO.putLocalUser(InitTask.localUser);


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginViewModel.class.getResource("/org/umiskky/view/" + "ChatView.fxml"));
            Parent root = loader.load();
            ChatViewController chatViewController = loader.getController();
            chatViewController.init(viewModelFactory1.getChatViewModel());
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

    public DataModel getDataModel() {
        return this.dataModel;
    }

    //public StringProperty getAccount() {
    //    return this.account;
    //}

    public Button getHeadPortrait() {
        return this.headPortrait;
    }
}
