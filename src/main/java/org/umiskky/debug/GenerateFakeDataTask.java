package org.umiskky.debug;

import io.objectbox.BoxStore;
import org.slf4j.Logger;
import org.umiskky.model.dao.FriendDAO;
import org.umiskky.model.dao.LocalUserDAO;
import org.umiskky.model.dao.MessageDAO;
import org.umiskky.model.dao.UserDAO;
import org.umiskky.model.entity.Friend;
import org.umiskky.model.entity.Message;
import org.umiskky.model.entity.User;
import org.umiskky.service.task.InitTask;

import java.time.Instant;
import java.util.ArrayList;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/22
 * @apiNote This class is a thread to generate fake data for testing.
 */
public class GenerateFakeDataTask implements Runnable{

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GenerateFakeDataTask.class);
    private BoxStore store = InitTask.store;

    public GenerateFakeDataTask() {
    }

    private void generateLocalUser(){
        InitTask.localUser.setUuid("dae21dc726f34992b69ae0fba3ae0081");
        InitTask.localUser.setNickname("umiskky");
        InitTask.localUser.setIpAddress("10.10.10.10");
        InitTask.localUser.setAvatarId(5);
        InitTask.localUser.setKey("umiskky".getBytes());
        LocalUserDAO.putLocalUser(InitTask.localUser);
        log.debug("Generate fake local user.");
    }

    private void generateUsers(){
        User user1 = new User(0, "tee001", "tee001", "10.0.0.1", "00-00-00-00-00-01", 8888, 0, true, 0);
        User user2 = new User(0, "tee002", "tee002", "10.0.0.2", "00-00-00-00-00-02", 8888, 1, false, 0);
        User user3 = new User(0, "tee003", "tee003", "10.0.0.3", "00-00-00-00-00-03", 8888, 2, false, 0);
        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        UserDAO.putUsers(users);
        log.debug("Generate fake users.");
    }

    private void generateFriends(){
        Friend friend1 = new Friend(0, "umiskky1", "umiskky1", "abcdefg", 3, "10.0.1.1", 8888, "umiskky".getBytes(), true, Instant.now().toEpochMilli());
        Friend friend2 = new Friend(0, "umiskky2", "umiskky2", "abcdefg", 4, "10.0.1.2", 8888, "umiskky".getBytes(), false, Instant.now().toEpochMilli());
        FriendDAO.putFriend(friend1);
        FriendDAO.putFriend(friend2);
        log.debug("Generate fake friends.");
    }

    private void generateMessage(){
        Message message1 = new Message(0, "umiskky1", "dae21dc726f34992b69ae0fba3ae0081", 100, "不要摸鱼，好好干活！");
        Message message2 = new Message(0, "umiskky1", "dae21dc726f34992b69ae0fba3ae0081", 150, "不然扣你这个月的工资！");
        Message message3 = new Message(0, "dae21dc726f34992b69ae0fba3ae0081", "umiskky1", 200, "好的老板，马上做！");
        Message message4 = new Message(0, "umiskky1", "dae21dc726f34992b69ae0fba3ae0081", 250, "下次注意，搞快一点，最近时间比较紧张。");
        Message message5 = new Message(0, "dae21dc726f34992b69ae0fba3ae0081", "umiskky2", 110, "摸鱼被老板发现了，怎么办？");
        Message message6 = new Message(0, "umiskky2", "dae21dc726f34992b69ae0fba3ae0081", 120, "啊，这。。");
        Message message7 = new Message(0, "dae21dc726f34992b69ae0fba3ae0081", "umiskky2", 160, "完了，老板要扣我工资QAQ");
        Message message8 = new Message(0, "umiskky2", "dae21dc726f34992b69ae0fba3ae0081", 180, "不要怂，跟老板说不干了，回家种地！");
        Message message9 = new Message(0, "dae21dc726f34992b69ae0fba3ae0081", "umiskky2", 200, "啊，这。。");
        MessageDAO.putMessage(message1);
        MessageDAO.putMessage(message2);
        MessageDAO.putMessage(message3);
        MessageDAO.putMessage(message4);
        MessageDAO.putMessage(message5);
        MessageDAO.putMessage(message6);
        MessageDAO.putMessage(message7);
        MessageDAO.putMessage(message8);
        MessageDAO.putMessage(message9);
        log.debug("Generate fake messages.");
    }

    private void generateGroups(){}

    private void generateGroupMembers(){}

    private void generateApply(){}

    @Override
    public void run() {
        Thread.currentThread().setName(Thread.currentThread().getName() + "(Generate_Fake_Data_Thread)");
        generateLocalUser();
        generateUsers();
        generateFriends();
        generateMessage();
    }
}
