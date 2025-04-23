package project.watering.iot.Instance;

import org.springframework.stereotype.Component;

import project.watering.iot.Factory.FactoryRegistry;
import project.watering.iot.Factory.Sensor;

@Component("lightLevelSensor")
public class LightLevelSensor implements Sensor {
    static {
        FactoryRegistry.registerSensor("lightlevel", LightLevelSensor::new);
    }

    @Override
    public void readData() {
        System.out.println("Reading air moisture...");
    }
}
