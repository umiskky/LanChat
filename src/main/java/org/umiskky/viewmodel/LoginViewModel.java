package org.umiskky.viewmodel;

import org.umiskky.model.DataModel;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;
/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
@Getter
public class LoginViewModel {
    private DataModel dataModel;
    private StringProperty account;

    public LoginViewModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    /**
     * @author Tee
     * @apiNote this method is used to close the existing window
     */
    public static void quit() {
        //close();
        System.exit(0);
    }

    /**
     * @author Tee
     * @apiNote this method is used to minimise the existing window(not yet)
     */
    public static void minimise(){
        //setIconified(true);
    }

    /**
     * @author Tee
     * @apiNote this method is used to choose head
     */
    public static void chooseHead(){

    }

    /**
     * @author Tee
     * @apiNote this method is used to login and go to the main window
     */
    public static void login(){

    }
}
