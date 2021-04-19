package org.umiskky.config;

import org.junit.Test;
import org.slf4j.Logger;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
public class ConfigParseTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ConfigParseTest.class);

    @Test
    public void testConfigParse(){
        ConfigParse.configParseInit();
        log.info("Test Done!");
    }
}
