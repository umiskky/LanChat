package org.umiskky.service.task.pcap;

import org.junit.Test;
import org.umiskky.factories.ServiceDispatcher;
import org.umiskky.service.InitService;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.task.InitTask;
import org.umiskky.service.task.pcap.sendtask.SendHelloPacketTask;

import java.util.ArrayList;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/20
 */
public class TestSendHelloPacketTask {
    @Test
    public void testSendHelloPacketTask(){
        ServiceDispatcher.submitTask(new InitService());

        ArrayList<NetworkCard> networkCards = new ArrayList<>(InitTask.networkCardsMapByName.values());
        System.out.println(networkCards);
        SendHelloPacketTask sendHelloPacketTask = new SendHelloPacketTask(networkCards.get(0));
        sendHelloPacketTask.run();
    }
}
