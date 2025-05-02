package project.watering.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "action")
public class Action {
    @Id
    private String Id;

    private String userId;
    private String gardenName;
    private int action;

    private LocalDateTime time = LocalDateTime.now();
    private LocalTime endTime;

}
