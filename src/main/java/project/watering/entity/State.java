package project.watering.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Document(collection = "state")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class State {
    @Id
    private String id;

    private Float soilState;
    private Float airState;
    private int lightLevelState;
    private Float temperatureState;
    private String pumpState;
    private String lightState;

}
