package org.umiskky.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ListView;
import org.umiskky.model.DataModel;
import org.umiskky.view.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
public class ChatViewModel {
    private StringProperty chattext;
    private DataModel dataModel;
    private Map<String, Vector<String>> friendMap = new HashMap<>();
    private Vector<String> friendInfo = new Vector<>();
    private Map<String, Vector<String>> userMap = new HashMap<>();
    private Vector<String> userInfo = new Vector<>();

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

    public void switchSelectFriend(ListView friendList){
        friendList.getItems().clear();
        for(Map.Entry<String,Vector<String>> entry : friendMap.entrySet()){
            String inhead = entry.getValue().get(0);
            String account = entry.getValue().get(1);
            String instatus = entry.getValue().get(2);
            String uuid = entry.getValue().get(3);
            Boolean status = false;

            if(instatus.equals("true")) {
                status = true;
            }else if(instatus.equals("false")){
                status = false;
            }

            friendList.getItems().add(new FriendListItem(inhead,account,status,uuid));
        }
    }

    public void switchSelectUser(ListView friendList){
        friendList.getItems().clear();
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
