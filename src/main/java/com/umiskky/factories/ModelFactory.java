package com.umiskky.factories;

import com.umiskky.model.DataModel;
import com.umiskky.model.DataModelManager;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
public class ModelFactory {

    private DataModel dataModel;

    public DataModel getDateModel(){
        if(dataModel == null) {
            dataModel = new DataModelManager();
        }
        return dataModel;
    }
}
