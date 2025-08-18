package me.while1cry.napcat4j.subscriber;

import me.while1cry.napcat4j.event.message.GroupMessageEvent;
import me.while1cry.napcat4j.event.message.PrivateMessageEvent;
import org.greenrobot.eventbus.Subscribe;

public class DefaultSubscriber {
    @Subscribe
    public void onGroupMessage(GroupMessageEvent ignore) {}

    @Subscribe
    public void onPrivateMessage(PrivateMessageEvent ignore) {}
}
