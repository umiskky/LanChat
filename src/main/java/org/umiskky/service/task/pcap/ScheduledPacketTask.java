package org.umiskky.service.task.pcap;

import org.slf4j.Logger;
import org.umiskky.factories.ServiceDispatcher;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.task.InitTask;
import org.umiskky.service.task.pcap.sendtask.SendNotifyPacketTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/21
 */
public class ScheduledPacketTask implements Runnable{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ScheduledPacketTask.class);
    private final NetworkCard networkCard;

    public ScheduledPacketTask(NetworkCard networkCard) {
        this.networkCard = networkCard;
    }

    public ScheduledPacketTask() {
        this.networkCard = InitTask.networkCardSelected;
    }

    @Override
    public void run() {
        SendNotifyPacketTask sendNotifyPacketTask = new SendNotifyPacketTask(networkCard);
        ServiceDispatcher.submitTask(sendNotifyPacketTask);
    }
}
