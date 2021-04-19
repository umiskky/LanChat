package org.umiskky.service;

import org.umiskky.service.task.InitTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public class InitService {

    public InitService() {
    }

    public void initService(){
        InitTask.importConfig();
        InitTask.initDatabase();
        InitTask.cleanDatabase();
        InitTask.initNetworkCards();
    }
}
