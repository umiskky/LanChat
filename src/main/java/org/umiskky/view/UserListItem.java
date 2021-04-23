package org.umiskky.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.umiskky.service.task.pcap.sendtask.SendMakeFriendsPacketTask;

public class UserListItem {
    private String userHead;
    private String userUuid;
    private String userName;
    private Pane pane;
    private Button head;
    private Label name;
    //private Button MsgTip;
    private Button status;
    private Button chosen;


    public UserListItem(String inhead,String account,Boolean instatus,String uuid){
        pane = new Pane();
        head = new Button();
        name = new Label();
        status = new Button();
        chosen = new Button();

        pane.setPrefSize(200,40);
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
        userHead = inhead;
    }

    private void setName(String inanme) {
        this.name.setText(inanme);
        userName = inanme;
    }

    private void setUuid(String uuid) {
        userUuid = uuid;
    }

    public void setOnline(){
        status.getStyleClass().clear();
        status.getStyleClass().add("online");
    }

    public void setOutline(){
        status.getStyleClass().clear();
        status.getStyleClass().add("outline");
    }

    public void setActionForAddFriend(ChatViewController chatViewController,String uuid){
        chosen.setOnAction((e) -> {
            //SendMakeFriendsPacketTask makeFriend = new SendMakeFriendsPacketTask();


        });
    }
}
