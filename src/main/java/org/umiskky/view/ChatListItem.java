package org.umiskky.view;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;

public class ChatListItem {
    private Pane pane;
    private Button head;
    private TextArea text;
    private Pane left;
    private Pane right;
    private Button arrow;

    public ChatListItem(){
        pane = new Pane();
        head = new Button();
        text = new TextArea();
        pane.setPrefSize(600,70);
        left = new Pane();
        right = new Pane();
        arrow = new Button();
        arrow.setDisable(false);
        arrow.setPrefSize(5,5);
        left.setPrefSize(550,60);
        right.setPrefSize(550,60);
        head.getStyleClass().add("head");
        pane.getStyleClass().add("pane");
        left.getStyleClass().add("pane");
        right.getStyleClass().add("pane");
        head.setPrefSize(20,20);
        text.setPrefSize(400,40);
        text.setWrapText(true);
        text.setEditable(false);

    }

    public Pane Left(String headid,String ctext,double width,double height){
        text.getStyleClass().add("lefttext");
        arrow.getStyleClass().add("leftarrow");
        pane.setPrefHeight(height + 40);
        left.setPrefHeight(height + 20);
        head.setLayoutX(5);
        head.setLayoutY(5);
        text.setPrefSize(width,height);
        text.setLayoutX(40);
        text.setLayoutY(15);
        arrow.setLayoutX(35);
        arrow.setLayoutY(25);
        text.setText(ctext);
        head.setStyle(String.format("-fx-background-image: url('org/umiskky/view/Image/head/%s.jpg')",headid));
        left.getChildren().add(head);
        left.getChildren().add(text);
        left.getChildren().add(arrow);
        pane.getChildren().add(left);

        return pane;
    }

    public Pane Right(String headid,String ctext,double width,double height){
        text.getStyleClass().add("righttext");
        arrow.getStyleClass().add("rightarrow");
        pane.setPrefHeight(height + 40);
        right.setPrefHeight(height + 20);
        head.setLayoutX(575);
        head.setLayoutY(5);
        text.setPrefSize(width,height);
        text.setLayoutX(550-width);
        text.setLayoutY(15);
        arrow.setLayoutX(560);
        arrow.setLayoutY(25);
        text.setText(ctext);
        head.setStyle(String.format("-fx-background-image: url('org/umiskky/view/Image/head/%s.jpg')",headid));
        right.getChildren().add(head);
        right.getChildren().add(text);
        right.getChildren().add(arrow);
        pane.getChildren().add(right);

        return pane;
    }


}
