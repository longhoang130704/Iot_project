package project.watering.iot.Instance;

import org.springframework.stereotype.Component;

import project.watering.iot.Factory.FactoryRegistry;
import project.watering.iot.Factory.Sensor;

@Component("moistureAir")
public class MoistureAirSensor implements Sensor {
    static {
        FactoryRegistry.registerSensor("air", MoistureAirSensor::new);
    }

    @Override
    public void readData() {
        System.out.println("Reading air moisture...");
    }

}
