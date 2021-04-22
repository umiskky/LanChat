package org.umiskky.service;

import org.umiskky.factories.ServiceDispatcher;
import org.umiskky.service.task.ExitTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public class ExitService extends Thread{

    public ExitService() {
    }

    private void exitService(){
        ExitTask.closeDatabase();
        ServiceDispatcher.closeAllThreadPools();
    }

    @Override
    public void run() {
        Thread.currentThread().setName(Thread.currentThread().getName() + "(Exit_Service_Thread)");
        exitService();
    }

}
