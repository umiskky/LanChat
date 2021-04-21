package org.umiskky.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ListView;
import org.umiskky.model.DataModel;
import org.umiskky.view.ChatListItem;
import org.umiskky.view.LoginViewController;
import org.umiskky.view.Tool;

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

    public void switchSC(){

    }

    public void switchSF(){

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
