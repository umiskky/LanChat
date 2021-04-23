package org.umiskky.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import org.umiskky.model.dao.MessageDAO;
import org.umiskky.model.entity.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendListItem {
    private String msg;         //先表示一下
    private Map<String,String> MsgMap = new HashMap<>();

    private Pane pane;
    private Button head;
    private Label name;
    //private Button MsgTip;
    private Button status;
    private Button chosen;
    private String friendHead;
    private String friendName;
    private String friendUuid;

    public FriendListItem(String inhead,String account,Boolean instatus,String uuid){
        pane = new Pane();
        head = new Button();
        name = new Label();
        //MsgTip = new Button();
        status = new Button();
        chosen = new Button();

        pane.setPrefSize(220,40);
        pane.getStyleClass().add("ListItem");
        status.setPrefSize(10,10);
        status.setLayoutX(42);
        status.setLayoutY(25);
        status.getStyleClass().add("outline");
        head.setPrefSize(30,30);
        head.setLayoutX(5);
        head.setLayoutY(5);
        head.getStyleClass().add("head");
        name.setPrefSize(170,20);
        name.setLayoutX(45);
        name.setLayoutY(3);
        chosen.setPrefSize(220,40);
        chosen.setLayoutX(0);
        chosen.setLayoutY(0);
        chosen.getStyleClass().add("chosen");
        pane.getChildren().addAll(head,status,name,chosen);

        if(instatus){
            setOnline();
        }
        setHead(inhead);
        setName(account);
        setUuid(uuid);

    }

    public Pane getPane(){
        return pane;
    }

    private void setHead(String inhead) {
        this.head.setStyle(String.format("-fx-background-image: url('/org/umiskky/view/Image/head/%s.jpg')",inhead));
        this.head.setStyle("-fx-background-size: 30px 30px");
        friendHead = inhead;
    }

    private void setName(String inanme) {
        this.name.setText(inanme);
        friendName = inanme;
    }

    private void setUuid(String uuid) {
        friendUuid = uuid;
    }

    public void setOnline(){
        status.getStyleClass().clear();
        status.getStyleClass().add("online");
    }

    public void setOutline(){
        status.getStyleClass().clear();
        status.getStyleClass().add("outline");
    }

    public void setActionForSendMsg(ChatViewController chatViewController,String Freuuid,String Locuuid){
        chosen.setOnAction((e) -> {
            //String friendAccount = friendName;

            ((Label) chatViewController.$("F_account")).setText(name.getText());
            ((TextArea)chatViewController.$("chattext")).setDisable(false);
            ((Button)chatViewController.$("submit")).setDisable(false);
            ((ListView) chatViewController.$("chatList")).getItems().clear();

            ArrayList<Message> messageslist = new ArrayList<>(MessageDAO.getMessages(Freuuid));

            for(int i = 0;i < messageslist.size();i ++){
                if(messageslist.get(i).getFromUuid().equals(Freuuid)){
                    chatViewController.addLeft(friendHead,messageslist.get(i).getMessage());
                }
                else{
                    chatViewController.addRight(LoginViewController.headid, messageslist.get(i).getMessage());
                }
            }

        });
    }

}
