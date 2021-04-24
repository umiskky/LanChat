package org.umiskky.view;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import lombok.Getter;
import org.umiskky.model.dao.FriendDAO;
import org.umiskky.model.dao.LocalUserDAO;
import org.umiskky.model.entity.Friend;
import org.umiskky.view.items.ChatListItem;
import org.umiskky.view.items.FriendListItem;
import org.umiskky.view.utils.Tool;
import org.umiskky.viewmodel.ChatViewModel;

import java.util.ArrayList;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
@Getter
public class ChatViewController {
    private ChatViewModel chatViewModel;
    private Parent root;
    private String locuuid;
    private double xOffset = 0;
    private double yOffset = 0;

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
    @FXML
    private Button submit;
    @FXML
    private TextArea chattext;

    /**
     * @author Tee
     * @apiNote this method is used to initialize
     */
    public void init(ChatViewModel chatViewModel,Parent root) {
        this.root = root;
        this.chatViewModel = chatViewModel;
        this.locuuid = LocalUserDAO.getLocalUser().getUuid();
        headPortraitChat.setStyle(String.format("-fx-background-image: url('/org/umiskky/view/Image/head/%s.jpg')", LoginViewController.headid));
        chattext.setDisable(true);
        submit.setDisable(true);
        addListener();
        bindInit();
        friendListInit();
    }

    public void bindInit(){
        chatViewModel.getChattext().bind(chattext.textProperty());
    }

    /**
     * @author Tee
     * @apiNote this method is used to add Listeners to the buttons
     */
    public void addListener(){
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            root.getScene().getWindow().setX(event.getScreenX() - xOffset);
            root.getScene().getWindow().setY(event.getScreenY() - yOffset);
        });

        quit.setTooltip(new Tooltip("退出"));
        quit.setOnAction((e) -> chatViewModel.quit());

        chatSelect.setOnMouseClicked((e) -> chatViewModel.switchSelectFriend(this , this.friendList));

        friendSelect.setOnMouseClicked((e) -> chatViewModel.switchSelectUser(this , this.friendList));

        submit.setOnMouseClicked((e) -> {
            chatViewModel.submit(this.chatList);
            chattext.clear();
        });

    }

    /**
     * @author Tee
     * @apiNote this method is used to initialize the friendList when the first login
     */
    public void friendListInit(){
        ArrayList<Friend> friendlist = new ArrayList<>(FriendDAO.getAllFriends());
        if(!friendlist.isEmpty()){
            friendList.getItems().clear();
            for(int i = 0;i < friendlist.size();i ++){
                Boolean status = friendlist.get(i).getStatus();
                String inhead = Integer.toString(friendlist.get(i).getAvatarId());
                String account = friendlist.get(i).getNickname();
                String uuid = friendlist.get(i).getUuid();

                FriendListItem newFriend = new FriendListItem(inhead,account,status,uuid);
                newFriend.setActionForSendMsg(this,uuid,locuuid);
                friendList.getItems().add(newFriend.getPane());
            }
        }
    }

    /**
     * @author Tee
     * @apiNote this method is used to get the elements by the id and the type
     */
    public Object $(String id) {
        return (Object) root.lookup("#" + id);
    }

    /**
     * @author Tee
     * @apiNote this method is used to add the msg of the friend to the chatList
     * @param head
     * @param Msg
     */
    public void addLeft(String head,String Msg){
        chatList.getItems().add(new ChatListItem().Left(head,Msg, Tool.getWidth(Msg),Tool.getHight(Msg)));
    }

    /**
     * @author Tee
     * @apiNote this method is used to add the msg of me to the chatList
     * @param head
     * @param Msg
     */
    public void addRight(String head,String Msg){
        chatList.getItems().add(new ChatListItem().Right(head,Msg,Tool.getWidth(Msg),Tool.getHight(Msg)));
    }

    public String getLocuuid(){
        return this.locuuid;
    }

    public ChatViewModel getChatViewModel() {
        return chatViewModel;
    }
}

