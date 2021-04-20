package org.umiskky.view;

import org.umiskky.viewmodel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
public class LoginViewController {
    private LoginViewModel loginViewModel;
    private static String headid = "1";

    @FXML
    private Button quit;
    @FXML
    private Button minimiser;
    @FXML
    private Button headPortrait;
    @FXML
    private Button chooseHead;
    @FXML
    private TextField account;
    //@FXML
    //private ChoiceBox<String> networkCardSelector;
    @FXML
    private Button login;


    /**
     * @author Tee
     * @apiNote this method is the initial process
     */
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
        loginViewModel.getAccount().bind(account.textProperty());
    }

    /**
     * @author Tee
     * @apiNote this method is used to add listener to the properties
     *          Button quit -> 转到 quit 函数
     *          Button chooseHead -> 弹出 dialog 进行头像选择
     *          Button login -> 跳转到聊天室
     */
    public void addListener(){
        quit.setTooltip(new Tooltip("退出"));
        quit.setOnAction((e) -> LoginViewModel.quit());

        minimiser.setTooltip(new Tooltip("最小化"));
        minimiser.setOnAction((e) -> LoginViewModel.minimise());

        chooseHead.setTooltip(new Tooltip("选择头像"));
        chooseHead.setOnAction((e) -> LoginViewModel.chooseHead(headPortrait,choose()));

        login.setTooltip(new Tooltip("登录"));
        login.setOnAction((e) -> LoginViewModel.login(login));
    }

    /**
     * @author Tee
     * @apiNote this method is used to choose the headportrait
     */
    public String choose(){
        headid = Integer.toString((Integer.parseInt(headid) + 1) % 10);
        return headid;
    }

    //public void networkCardSelectorInit(){
    //
    //}

}
