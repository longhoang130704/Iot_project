package project.watering.iot.Instance;

import org.springframework.stereotype.Component;

import project.watering.iot.Factory.Device;
import project.watering.iot.Factory.FactoryRegistry;
import project.watering.service.interfaceLayer.AdafruitIOClient;

@Component("lightDevice")
public class LightDevice implements Device {
    static {
        FactoryRegistry.registerDevice("light", LightDevice::new);
    }

    @Override
    public void activate() {
        System.out.println("Light is ON.");
    }

    @Override
    public void turnOn() {
        System.out.println("gui data bat den ve adafruit");
        AdafruitIOClient.sendData("1", "v11");
    }

    @Override
    public void turnOff() {
        System.out.println("gui data tat den ve adafruit");
        AdafruitIOClient.sendData("0", "v11");
    }

}
