package org.umiskky.view;

import javafx.fxml.FXML;
import javafx.scene.Parent;
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
    public static String headid;

    private Parent root;
    private double xOffset = 0;
    private double yOffset = 0;

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
    public void init(LoginViewModel loginViewModel, Parent root) {
        this.root = root;
        this.loginViewModel = loginViewModel;
        //networkCardSelectorInit();
        headid = "0";
        headPortrait.setStyle(String.format("-fx-background-image: url('org/umiskky/view/Image/head/%s.jpg')",headid));
        bindInit();
        addListener();
    }

    /**
     * @author Tee
     * @apiNote this method is used to init the bindings of the properties
     */
    public void bindInit(){
        LoginViewModel.account.bind(account.textProperty());
    }

    /**
     * @author Tee
     * @apiNote this method is used to add listener to the properties
     *          Button quit -> 转到 quit 函数
     *          Button chooseHead -> 弹出 dialog 进行头像选择
     *          Button login -> 跳转到聊天室
     */
    public void addListener(){
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            root.getScene().getWindow().setX(event.getScreenX() - xOffset);
            root.getScene().getWindow().setY(event.getScreenY() - yOffset);
        });

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
        if(headid.equals("9")){
            headid = "0";
        }
        return headid;
    }

    public LoginViewModel getLoginViewModel() {
        return this.loginViewModel;
    }

    public Button getQuit() {
        return this.quit;
    }

    public Button getMinimiser() {
        return this.minimiser;
    }

    public Button getHeadPortrait() {
        return this.headPortrait;
    }

    public Button getChooseHead() {
        return this.chooseHead;
    }

    public TextField getAccount() {
        return this.account;
    }

    public Button getLogin() {
        return this.login;
    }

    //public void networkCardSelectorInit(){
    //
    //}

}
