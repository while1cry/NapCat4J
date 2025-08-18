package me.while1cry.napcat4j.entity.message.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtData extends MessageData {

    @NotNull
    public String qq;

    public static AtData at(String qqNumber) {
        return new AtData(qqNumber);
    }

    public static AtData atAll() {
        return new AtData("all");
    }
}
