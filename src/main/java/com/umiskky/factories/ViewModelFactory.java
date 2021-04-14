package com.umiskky.factories;

import com.umiskky.viewmodel.ChatViewModel;
import com.umiskky.viewmodel.LoginViewModel;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
public class ViewModelFactory {

    private ChatViewModel chatViewModel;
    private LoginViewModel loginViewModel;


    public ViewModelFactory(ModelFactory modelFactory){
        chatViewModel = new ChatViewModel(modelFactory.getDateModel());
        loginViewModel = new LoginViewModel(modelFactory.getDateModel());
    }

    public ChatViewModel getChatViewModel() {
        return chatViewModel;
    }

    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }
}
