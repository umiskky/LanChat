package org.umiskky.service.task;

import org.slf4j.Logger;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public class ExitTask {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ExitTask.class);

    /**
     * @description The method closeDatabase is used to construct a task to close the database.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/19-18:40
     */
    public static void closeDatabase(){
        InitTask.store.close();
        log.info("Close the database.");
    }
}
