package me.while1cry.napcat4j.entity.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OnlineStatus {
    ONLINE(10, 0, 0),
    Q_WO_BA(60, 0, 0),
    LEFT(30, 0, 0),
    BUSY(50, 0, 0),
    VANISH(40, 0, 0),
    WEAK_SIGNAL(10, 1011, 0);

    private static final ObjectMapper mapper = new ObjectMapper();

    private final int status;
    private final int extStatus;
    private final int batteryStatus;

    public ObjectNode toJson() {
        return mapper.createObjectNode()
                .put("status", status)
                .put("ext_status", extStatus)
                .put("battery_status", batteryStatus);
    }
}
