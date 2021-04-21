package org.umiskky.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import org.umiskky.viewmodel.ChatViewModel;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
public class ChatViewController {
    private ChatViewModel chatViewModel;

    @FXML
    private Button headPortraitChat;
    @FXML
    private Button quit;
    @FXML
    private ListView friendList;
    @FXML
    private ListView chatList;
    @FXML
    private HBox chatSelect;
    @FXML
    private HBox friendSelect;
    /**
     * @author Tee
     * @apiNote this method is used to initialize
     */
    public void init(ChatViewModel chatViewModel) {
        this.chatViewModel = chatViewModel;
        headPortraitChat.setStyle(String.format("-fx-background-image: url('org/umiskky/view/Image/head/%s.jpg')", LoginViewController.headid));
        addListener();
    }

    /**
     * @author Tee
     * @apiNote this method is used to add Listeners to the buttons
     */
    public void addListener(){
        quit.setTooltip(new Tooltip("退出"));
        quit.setOnAction((e) -> ChatViewModel.quit());

        chatSelect.setOnMouseClicked((e) -> ChatViewModel.switchSC());

        friendSelect.setOnMouseClicked((e) -> ChatViewModel.switchSF());
    }

}
