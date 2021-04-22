package org.umiskky.service.task;

import org.umiskky.factories.ServiceDispatcher;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.task.pcap.PcapCaptureTask;
import org.umiskky.service.task.pcap.ScheduledPacketTask;
import org.umiskky.service.task.pcap.sendtask.SendHelloPacketTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/20
 */
public class NetworkCardTask implements Runnable{

    private final NetworkCard networkCard;

    public NetworkCardTask(NetworkCard networkCard) {
        this.networkCard = networkCard;
    }

    @Override
    public void run() {
        ServiceDispatcher.submitTask(new PcapCaptureTask(networkCard));

        ServiceDispatcher.submitTask(new SendHelloPacketTask(networkCard));

        ServiceDispatcher.submitTask(new ScheduledPacketTask(networkCard));
    }
}
