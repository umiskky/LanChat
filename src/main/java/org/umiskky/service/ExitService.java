package org.umiskky.service;

import org.umiskky.service.task.ExitTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public class ExitService {

    public ExitService() {
    }

    public void exitService(){
        ExitTask.closeDatabase();
    }
}
