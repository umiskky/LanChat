package org.umiskky.view;

import javafx.fxml.FXML;
import org.umiskky.viewmodel.ChatViewModel;

import javax.swing.text.html.ListView;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
public class ChatViewController {
    private ChatViewModel chatViewModel;

    @FXML
    private ListView friendList;

    public void init(ChatViewModel chatViewModel) {
        this.chatViewModel = chatViewModel;
    }

}
