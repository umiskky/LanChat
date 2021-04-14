package com.umiskky.factories;

import com.umiskky.model.DateModel;
import com.umiskky.model.DateModelManager;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
public class ModelFactory {

    private DateModel dateModel;

    public DateModel getDateModel(){
        if(dateModel == null) {
            dateModel = new DateModelManager();
        }
        return dateModel;
    }
}
