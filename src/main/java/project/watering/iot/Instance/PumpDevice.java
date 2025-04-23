package project.watering.iot.Instance;

import org.springframework.stereotype.Component;

import project.watering.iot.Factory.Device;
import project.watering.iot.Factory.FactoryRegistry;
import project.watering.service.interfaceLayer.AdafruitIOClient;

@Component("pumpDevice")
public class PumpDevice implements Device {
    static {
        FactoryRegistry.registerDevice("pump", PumpDevice::new);
    }

    @Override
    public void activate() {
        System.out.println("Pump is ON.");
    }

    @Override
    public void turnOn() {
        System.out.println("gui data bat may bom ve adafruit");
        AdafruitIOClient.sendData("1", "v10");
    }

    @Override
    public void turnOff() {
        System.out.println("gui data tat may bom ve adafruit");
        AdafruitIOClient.sendData("0", "v10");
    }

}