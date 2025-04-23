package project.watering.iot.Instance;

import org.springframework.stereotype.Component;

import project.watering.iot.Factory.FactoryRegistry;
import project.watering.iot.Factory.Sensor;

@Component("temperatureSensor")
public class TemperatureSensor implements Sensor {
    static {
        FactoryRegistry.registerSensor("temperature", TemperatureSensor::new);
    }

    @Override
    public void readData() {
        System.out.println("Reading temperature TemperatureSensor...");
    }

}
