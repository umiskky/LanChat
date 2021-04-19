package org.umiskky.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.umiskky.viewmodel.LoginViewModel;


/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
public class LoginViewController {
    private LoginViewModel loginViewModel;

    @FXML
    private Button quit;
    @FXML
    private Button minimiser;
    @FXML
    private Button chooseHead;
    @FXML
    private TextField account;
    //@FXML
    //private ChoiceBox<String> networkCardSelector;
    @FXML
    private Button login;

    public void init(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        //networkCardSelectorInit();
        //tableInit();
        bindInit();
        addListener();
    }

    /**
     * @author Tee
     * @apiNote this method is used to init the bindings of the properties
     */
    public void bindInit(){
        //loginViewModel.getAccount().bind(account.textProperty());
    }

    /**
     * @author Tee
     * @apiNote this method is used to add listener to the properties
     *          Button quit -> 转到 quit 函数
     *
     */
    public void addListener(){
        quit.setTooltip(new Tooltip("退出"));
        quit.setOnAction((e) -> LoginViewModel.quit());

        minimiser.setTooltip(new Tooltip("最小化"));
        minimiser.setOnAction((e) -> LoginViewModel.minimise());

        chooseHead.setTooltip(new Tooltip("选择头像"));
        chooseHead.setOnAction((e) -> LoginViewModel.chooseHead());

        login.setTooltip(new Tooltip("登录"));
        login.setOnAction((e) -> LoginViewModel.login());
    }

    //public void networkCardSelectorInit(){
    //
    //}

}
