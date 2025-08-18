package me.while1cry.napcat4j.entity.message.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class MessageData {

    private static final ObjectMapper mapper = new ObjectMapper();

    public String toJson() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }
}
