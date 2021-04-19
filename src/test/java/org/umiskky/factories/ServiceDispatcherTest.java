package org.umiskky.factories;

import org.junit.Test;
import org.umiskky.config.ConfigParse;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public class ServiceDispatcherTest {
    @Test
    public void TestServiceDispatcher(){
        ConfigParse.configParseInit();
        ServiceDispatcher serviceDispatcher = new ServiceDispatcher();
    }
}
