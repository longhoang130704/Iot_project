package project.watering.configuration;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DeviceInitializer {
    @PostConstruct
    public void init() {
        try {
            // Ép JVM load class => chạy static block
            Class.forName("project.watering.iot.Instance.LightDevice");
            Class.forName("project.watering.iot.Instance.PumpDevice");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load LightDevice class", e);
        }
    }
}
