package project.watering.iot.Factory;

import java.util.HashMap;
import java.util.Map;

public class FactoryRegistry {
    private static final Map<String, SensorFactory> sensorFactories = new HashMap<>();
    private static final Map<String, DeviceFactory> deviceFactories = new HashMap<>();

    public static void registerSensor(String type, SensorFactory factory) {
        sensorFactories.put(type.toLowerCase(), factory);
    }

    public static void registerDevice(String type, DeviceFactory factory) {
        deviceFactories.put(type.toLowerCase(), factory);
    }

    public static Sensor createSensor(String type) {
        SensorFactory factory = sensorFactories.get(type.toLowerCase());
        if (factory == null)
            throw new IllegalArgumentException("No such sensor: " + type);
        return factory.create();
    }

    public static Device createDevice(String type) {
        DeviceFactory factory = deviceFactories.get(type.toLowerCase());
        if (factory == null)
            throw new IllegalArgumentException("No such device: " + type);
        return factory.create();
    }
}
