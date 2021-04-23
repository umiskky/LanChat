package org.umiskky.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ListView;
import org.umiskky.model.DataModel;
import org.umiskky.model.dao.FriendDAO;
import org.umiskky.model.dao.UserDAO;
import org.umiskky.model.entity.Friend;
import org.umiskky.model.entity.User;
import org.umiskky.view.*;

import java.util.*;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
public class ChatViewModel {
    private StringProperty chattext;
    private DataModel dataModel;


    public ChatViewModel(DataModel dataModel) {
        this.dataModel = dataModel;
        vmPropertiesInit();
    }

    private void vmPropertiesInit() {
        chattext = new SimpleStringProperty();
    }

    public void quit(){
        System.exit(0);
    }

    public void switchSelectFriend(ChatViewController chatViewController,ListView friendList){
        ArrayList<Friend> friendlist = new ArrayList<>(FriendDAO.getAllFriends());
        if(friendlist.isEmpty() == false){
            friendList.getItems().clear();
            for(int i = 0;i < friendlist.size();i ++){
                Boolean status = friendlist.get(i).getStatus();
                String inhead = Integer.toString(friendlist.get(i).getAvatarId());
                String account = friendlist.get(i).getNickname();
                String uuid = friendlist.get(i).getUuid();

                FriendListItem newFriend = new FriendListItem(inhead,account,status,uuid);
                newFriend.setActionForSendMsg(chatViewController,uuid,chatViewController.getLocuuid());
                friendList.getItems().add(newFriend.getPane());
            }
        }

    }

    public void switchSelectUser(ChatViewController chatViewController,ListView friendList){
        ArrayList<User> userList = new ArrayList<>(UserDAO.getAllUsers());
        if(userList.isEmpty() == false){
            friendList.getItems().clear();
            for(int i = 0;i < userList.size();i ++){
                Boolean status = userList.get(i).getStatus();
                String inhead = Integer.toString(userList.get(i).getAvatarId());
                String account = userList.get(i).getNickname();
                String uuid = userList.get(i).getUuid();

                UserListItem newUser = new UserListItem(inhead, account, status, uuid);
                newUser.setActionForAddFriend(chatViewController, uuid);
                friendList.getItems().add(newUser.getPane());
            }

        }
    }

    public void submit(ListView chatList){
        String head = LoginViewController.headid;
        String Msg = this.chattext.get();
        chatList.getItems().add(new ChatListItem().Right(head,Msg, Tool.getWidth(Msg),Tool.getHight(Msg)));

    }

    public StringProperty getChattext() {
        return this.chattext;
    }

    public DataModel getDataModel() {
        return this.dataModel;
    }
}
