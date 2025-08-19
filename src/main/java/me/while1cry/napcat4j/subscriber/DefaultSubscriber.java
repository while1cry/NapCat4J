package me.while1cry.napcat4j.subscriber;

import lombok.AllArgsConstructor;
import me.while1cry.napcat4j.event.message.GroupMessageEvent;
import me.while1cry.napcat4j.event.message.PrivateMessageEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.SubscriberExceptionEvent;
import org.slf4j.Logger;

@AllArgsConstructor
public class DefaultSubscriber {

    private final Logger logger;

    @Subscribe
    public void onGroupMessage(GroupMessageEvent ignore) {
    }

    @Subscribe
    public void onPrivateMessage(PrivateMessageEvent ignore) {
    }

    @Subscribe
    public void onSubscriberException(SubscriberExceptionEvent event) {
        logger.error("Subscriber exception occurred", event.throwable);
    }

}
