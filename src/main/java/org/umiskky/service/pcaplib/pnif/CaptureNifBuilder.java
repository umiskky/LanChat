package org.umiskky.service.pcaplib.pnif;

import org.pcap4j.core.*;
import org.slf4j.Logger;
import org.umiskky.factories.ServiceDispatcher;

import java.io.IOException;
import java.util.Properties;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/20
 */
public class CaptureNifBuilder {
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
     * @description The method CaptureNifBuilder is used to construct a CaptureNifBuilder object with default builder settings.
     * @param nifName
     * @return
     * @author umiskky
     * @date 2021/4/20-15:42
     */
    public CaptureNifBuilder(String nifName) {

        this.nifName = nifName;
        this.builder = new PcapHandle.Builder(nifName)
                .snaplen(Integer.parseInt(properties.getProperty("DEFAULT.PCAP_CAPTURE_HANDLE.SNAP_LEN")))
                .timeoutMillis(Integer.parseInt(properties.getProperty("DEFAULT.PCAP_CAPTURE_HANDLE.READ_TIMEOUT")))
                .bufferSize(Integer.parseInt(properties.getProperty("DEFAULT.PCAP_CAPTURE_HANDLE.BUFFER_SIZE")));

        if(Integer.parseInt(properties.getProperty("DEFAULT.PCAP_CAPTURE_HANDLE.PROMISCUOUS")) == 0){
            this.builder.promiscuousMode(PcapNetworkInterface.PromiscuousMode.NONPROMISCUOUS);
        }else if(Integer.parseInt(properties.getProperty("DEFAULT.PCAP_CAPTURE_HANDLE.PROMISCUOUS")) == 1){
            this.builder.promiscuousMode(PcapNetworkInterface.PromiscuousMode.PROMISCUOUS);
        }

        if(Integer.parseInt(properties.getProperty("DEFAULT.PCAP_CAPTURE_HANDLE.TIMESTAMP_PRECISION_NANO")) == 1){
            this.builder.timestampPrecision(PcapHandle.TimestampPrecision.NANO);
        }

    }

    /**
     * @description The method CaptureNifBuilder is used to construct a CaptureNifBuilder object with nifName and builder parameters input.
     * @param nifName
     * @param builder
     * @return
     * @author umiskky
     * @date 2021/4/20-15:44
     */
    public CaptureNifBuilder(String nifName, PcapHandle.Builder builder) {
        this.nifName = nifName;
        this.builder = builder;
    }

    /**
     * @description The method build is used to build a pcap handle.
     * @param
     * @return org.pcap4j.core.PcapHandle
     * @author umiskky
     * @date 2021/4/20-15:44
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

    /**
     * @description The method build is used to build a pcap handle with filter.
     * @param filter
     * @return org.pcap4j.core.PcapHandle
     * @author umiskky
     * @date 2021/4/20-15:45
     */
    public PcapHandle build(String filter){
        PcapHandle handle = null;
        try {
            handle = this.builder.build();
            try {
                handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
            } catch (NotOpenException e) {
                log.error(e.getMessage());
            }
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
