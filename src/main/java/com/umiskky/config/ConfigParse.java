package com.umiskky.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
@Slf4j
public class ConfigParse {

    /**
     * @description The method configParseInit is used to set system properties and relocate the path of config files.
     * @param
     * @return void
     * @author umiskky
     * @date 2021/4/14-19:24
     */
    public static void configParseInit(){


        String log4j2Path = ConfigParse.class.getResource("log4j2.xml").getPath();
        String packetFactoryPath = ConfigParse.class.getResource("packet-factory.properties").getPath();

        System.setProperty("log4j2.configurationFile", log4j2Path);
        System.setProperty("org.pcap4j.packet.factory.properties", packetFactoryPath);
        Configurator.initialize("log4j2.xml", ConfigParse.class.getClassLoader(), log4j2Path);
        log.info("Relocate config files!");
    }

}
