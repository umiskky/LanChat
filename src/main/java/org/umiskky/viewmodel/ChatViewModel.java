package org.umiskky.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ListView;
import org.slf4j.Logger;
import org.umiskky.factories.ServiceDispatcher;
import org.umiskky.model.DataModel;
import org.umiskky.model.dao.FriendDAO;
import org.umiskky.model.dao.MessageDAO;
import org.umiskky.model.dao.UserDAO;
import org.umiskky.model.entity.Friend;
import org.umiskky.model.entity.Message;
import org.umiskky.model.entity.User;
import org.umiskky.model.verification.FriendVerification;
import org.umiskky.service.task.InitTask;
import org.umiskky.service.task.socket.ClientThread;
import org.umiskky.view.ChatViewController;
import org.umiskky.view.LoginViewController;
import org.umiskky.view.items.ChatListItem;
import org.umiskky.view.items.FriendListItem;
import org.umiskky.view.items.UserListItem;
import org.umiskky.view.utils.Tool;

import java.time.Instant;
import java.util.ArrayList;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
public class ChatViewModel {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ChatViewModel.class);
    private StringProperty chattext;
    private DataModel dataModel;

    private String sessionId;


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
        friendList.getItems().clear();
        if(!friendlist.isEmpty()){

            for (Friend friend : friendlist) {
                Boolean status = friend.getStatus();
                String inhead = Integer.toString(friend.getAvatarId());
                String account = friend.getNickname();
                String uuid = friend.getUuid();

                if ("".equals(account)) {
                    account = uuid;
                }
                FriendListItem newFriend = new FriendListItem(inhead, account, status, uuid);
                newFriend.setActionForSendMsg(chatViewController, uuid, chatViewController.getLocuuid());
                friendList.getItems().add(newFriend.getPane());
            }
        }

    }

    public void switchSelectUser(ChatViewController chatViewController,ListView friendList){
        ArrayList<User> userList = new ArrayList<>(UserDAO.getAllUsers());
        friendList.getItems().clear();
        if(!userList.isEmpty()){

            for(int i = 0;i < userList.size();i ++){
                Boolean status = userList.get(i).getStatus();
                String inhead = Integer.toString(userList.get(i).getAvatarId());
                String account = userList.get(i).getNickname();
                String uuid = userList.get(i).getUuid();

                if("".equals(account)){
                    account = uuid;
                }
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

        Friend friend = FriendDAO.getFriendById(sessionId);
        if(FriendVerification.isValidFriend(friend) && friend.getStatus()){
            Message msg = new Message(0, sessionId, InitTask.localUser.getUuid(), sessionId, Instant.now().toEpochMilli(), Msg);
            log.debug("Construct a message.\n" + msg);
            MessageDAO.putMessage(msg);

            Message sendMsg = (Message) msg.clone();
            sendMsg.setSessionId(InitTask.localUser.getUuid());
            Message.encryptMessage(sendMsg, friend.getKey());
            ClientThread clientThread = new ClientThread(friend.getIpAddress(), friend.getServerPort(), sendMsg);
            ServiceDispatcher.submitTask(clientThread);
            log.debug("Send a message.\n" + sendMsg);
        }else if(!FriendVerification.isValidFriend(friend)){
            log.error("Failed to send a message because an invalid friend!");
        }else if(!friend.getStatus()){
            log.error("Failed to send a message because the friend is offline!");
        }

    }

    public StringProperty getChattext() {
        return this.chattext;
    }

    public DataModel getDataModel() {
        return this.dataModel;
    }


    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
