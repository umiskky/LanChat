package org.umiskky.view;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import lombok.Getter;
import org.umiskky.viewmodel.ChatViewModel;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
@Getter
public class ChatViewController {
    private ChatViewModel chatViewModel;
    //private static Friend[] FList;
    private Parent root;

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
    public void init(ChatViewModel chatViewModel) {
        this.chatViewModel = chatViewModel;
        headPortraitChat.setStyle(String.format("-fx-background-image: url('org/umiskky/view/Image/head/%s.jpg')", LoginViewController.headid));
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
        quit.setTooltip(new Tooltip("退出"));
        quit.setOnAction((e) -> chatViewModel.quit());

        chatSelect.setOnMouseClicked((e) -> chatViewModel.switchSelectFriend(this,this.friendList));

        friendSelect.setOnMouseClicked((e) -> chatViewModel.switchSelectUser(this,this.friendList));

        submit.setOnMouseClicked((e) -> chatViewModel.submit(this.chatList));

    }

    /**
     * @author Tee
     * @apiNote this method is used to initialize the friendList when the first login
     */
    public void friendListInit(){
        //for(int i = 0;i < FList.length;i ++){
        //    FriendListItem newFriend = new FriendListItem(Integer.toString(FList[i].getAvatarId()),FList[i].getNickname(),FList[i].getStatus(),FList[i].getUuid());
        //    newFriend.setActionForSendMsg(this,FList[i].getUuid());
        //    friendList.getItems().add(newFriend);
        //}
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

}

