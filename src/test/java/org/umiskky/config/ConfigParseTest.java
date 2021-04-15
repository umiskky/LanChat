package org.umiskky.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
@Slf4j
public class ConfigParseTest {

    @Test
    public void testConfigParse(){
        ConfigParse.configParseInit();
        log.info("Test Done!");
    }
}
