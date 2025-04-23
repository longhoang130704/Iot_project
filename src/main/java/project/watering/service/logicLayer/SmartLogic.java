package project.watering.service.logicLayer;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import project.watering.entity.State;
import project.watering.iot.Factory.Device;
import project.watering.repository.StateRepository;

@Service
public class SmartLogic {
    private static final int MOISTURE_SOIL_THRESHOLD = 40; // % độ ẩm đất
    private static final int LIGHT_THRESHOLD = 300; // lux
    private static final int TEMP_MAX = 35; // °C
    private static final int HUMIDITY_AIR_MAX = 75; // % độ ẩm không khí

    private static State savedState = new State();

    @Autowired
    @Qualifier("pumpDevice") // tên bean trong @Component
    private Device pump;

    @Autowired
    @Qualifier("lightDevice") // tên bean trong @Component
    private Device light;

    @Autowired
    private StateRepository stateRepository;

    // Gọi phương thức này để cập nhật trạng thái thiết bị
    public void updateState(float humidityAir, float moistureSoil, int lightLevel, float temperature) {
        controlPump(moistureSoil, temperature, humidityAir);
        controlLight(lightLevel);

        // update savedState and save to database
        savedState.setAirState(humidityAir);
        savedState.setSoilState(moistureSoil);
        savedState.setLightLevelState(lightLevel);
        savedState.setTemperatureState(temperature);

        // save to database
        stateRepository.save(savedState);

        System.out.println(savedState.toString());
    }

    private void controlPump(float moistureSoil, float temperature, float humidityAir) {
        boolean isSoilDry = moistureSoil < MOISTURE_SOIL_THRESHOLD;
        boolean isTempOkay = temperature < TEMP_MAX;
        boolean isAirDry = humidityAir < HUMIDITY_AIR_MAX;

        if (isSoilDry && isTempOkay && isAirDry) {
            System.out.println(" Đo am dat thap, nhiet đo phu hop, khong khi kho = BẬT máy bơm");
            pump.turnOn();
            savedState.setPumpState("1");
        } else {
            System.out.println(" Khong can tuoi nuoc = TaT may bom");
            pump.turnOff();
            savedState.setPumpState("0");
        }
    }

    private void controlLight(int lightLevel) {
        int currentHour = LocalTime.now().getHour();
        boolean isDayTime = currentHour >= 6 && currentHour <= 18;
        boolean isLightLow = lightLevel < LIGHT_THRESHOLD;

        if (isDayTime && isLightLow) {
            System.out.println(" anh sang yeu vao ban ngay = bat đen");
            light.turnOn();
            savedState.setLightState("1");
        } else {
            System.out.println(" anh sang đu hoac đang la ban đem = tat đen");
            light.turnOff();
            savedState.setLightState("0");
        }
    }

}
