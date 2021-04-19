package org.umiskky;

import org.slf4j.Logger;
import org.umiskky.service.task.InitTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
public class Test {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Test.class);

    @org.junit.Test
    public void test(){
        System.out.println(InitTask.class.getResource("/").getPath());
    }
}
