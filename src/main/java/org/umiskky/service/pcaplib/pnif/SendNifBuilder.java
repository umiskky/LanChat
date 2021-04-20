package org.umiskky.service.pcaplib.pnif;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.slf4j.Logger;
import org.umiskky.factories.ServiceDispatcher;

import java.io.IOException;
import java.util.Properties;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/20
 */
public class SendNifBuilder {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SendNifBuilder.class);
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(ServiceDispatcher.class.getResourceAsStream("../config/pcap-handle.properties"));
            log.info("Load Pcap Handle Config File.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String nifName;
    private PcapHandle.Builder builder;

    /**
     * @description The method SendNifBuilder is used to construct a SendNifBuilder object with default builder settings.
     * @param nifName
     * @return
     * @author umiskky
     * @date 2021/4/20-15:28
     */
    public SendNifBuilder(String nifName) {

        this.nifName = nifName;
        this.builder = new PcapHandle.Builder(nifName)
                .snaplen(Integer.parseInt(properties.getProperty("DEFAULT.PCAP_SEND_HANDLE.SNAP_LEN")))
                .timeoutMillis(Integer.parseInt(properties.getProperty("DEFAULT.PCAP_SEND_HANDLE.READ_TIMEOUT")));

        if(Integer.parseInt(properties.getProperty("DEFAULT.PCAP_SEND_HANDLE.PROMISCUOUS")) == 0){
            this.builder.promiscuousMode(PcapNetworkInterface.PromiscuousMode.NONPROMISCUOUS);
        }else if(Integer.parseInt(properties.getProperty("DEFAULT.PCAP_SEND_HANDLE.PROMISCUOUS")) == 1){
            this.builder.promiscuousMode(PcapNetworkInterface.PromiscuousMode.PROMISCUOUS);
        }

        if(Integer.parseInt(properties.getProperty("DEFAULT.PCAP_SEND_HANDLE.TIMESTAMP_PRECISION_NANO")) == 1){
            this.builder.timestampPrecision(PcapHandle.TimestampPrecision.NANO);
        }

    }

    /**
     * @description The method SendNifBuilder is used to construct a SendNifBuilder object with nifName and builder parameters input.
     * @param nifName
     * @param builder
     * @return 
     * @author umiskky
     * @date 2021/4/20-15:29
     */
    public SendNifBuilder(String nifName, PcapHandle.Builder builder) {
        this.nifName = nifName;
        this.builder = builder;
    }

    /**
     * @description The method build is used to build a pcap handle.
     * @param
     * @return org.pcap4j.core.PcapHandle
     * @author umiskky
     * @date 2021/4/20-15:37
     */
    public PcapHandle build(){
        PcapHandle handle = null;
        try {
            handle = this.builder.build();
        } catch (PcapNativeException e) {
            log.error(e.getMessage());
        }
        return handle;
    }

    public String getNifName() {
        return this.nifName;
    }

    public PcapHandle.Builder getBuilder() {
        return this.builder;
    }
}
