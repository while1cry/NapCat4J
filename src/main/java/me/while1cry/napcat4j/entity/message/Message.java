package me.while1cry.napcat4j.entity.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.while1cry.napcat4j.entity.message.data.MessageData;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MessageDeserializer.class)
public class Message {
    public String type;
    public MessageData data;
}
