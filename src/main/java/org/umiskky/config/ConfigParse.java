package org.umiskky.config;

import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
public class ConfigParse {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ConfigParse.class);

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
        log.info("Relocate config files.");
        initPropertiesBasedPacketFactory();
    }

    private static void initPropertiesBasedPacketFactory(){
        System.setProperty("org.pcap4j.packet.Packet.classFor.org.pcap4j.packet.namednumber.EtherType.0xaaa0", "org.umiskky.service.pcaplib.packet.HelloPacket");
        System.setProperty("org.pcap4j.packet.Packet.classFor.org.pcap4j.packet.namednumber.EtherType.0xaaa1", "org.umiskky.service.pcaplib.packet.MakeFriendsPacket");
        System.setProperty("org.pcap4j.packet.Packet.classFor.org.pcap4j.packet.namednumber.EtherType.0xaaa2", "org.umiskky.service.pcaplib.packet.StatusAckPacket");
        System.setProperty("org.pcap4j.packet.Packet.classFor.org.pcap4j.packet.namednumber.EtherType.0xaaa3", "org.umiskky.service.pcaplib.packet.InviteToGroupPacket");
        System.setProperty("org.pcap4j.packet.Packet.classFor.org.pcap4j.packet.namednumber.EtherType.0xaaa4", "org.umiskky.service.pcaplib.packet.NotifyPacket");
    }

}
