package project.watering.iot.Instance;

import org.springframework.stereotype.Component;

import project.watering.iot.Factory.FactoryRegistry;
import project.watering.iot.Factory.Sensor;

@Component("moistureSoil")
public class MoistureSoilSensor implements Sensor {
    static {
        FactoryRegistry.registerSensor("soil", MoistureSoilSensor::new);
    }

    @Override
    public void readData() {
        System.out.println("Reading soil moisture...");
    }
}
