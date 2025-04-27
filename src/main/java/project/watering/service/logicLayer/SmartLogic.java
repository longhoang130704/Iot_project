package project.watering.service.logicLayer;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import project.watering.bean.SmartControllerState;
import project.watering.entity.State;
import project.watering.iot.Factory.Device;
import project.watering.repository.StateRepository;

@Service
public class SmartLogic {
    private static final int MOISTURE_SOIL_THRESHOLD_MIN = 30; // % độ ẩm đất
    private static final int MOISTURE_SOIL_THRESHOLD_MAX = 50; // % độ ẩm đất
    private static final int LIGHT_THRESHOLD = 20; // lux
    // private static final int TEMP_MAX = 35; // °C
    private static final int HUMIDITY_AIR_MIN = 30; // % độ ẩm không khí
    private static final int HUMIDITY_AIR_MAX = 50; // % độ ẩm không khí

    private State savedState = new State();

    @Autowired
    private SmartControllerState smartControllerState;

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

        if (smartControllerState.isEnabled()) {
            // set light vs pum auto
            savedState.setModeLight("auto");
            savedState.setModePump("auto");
            System.out.println("da chay va thay enabled true");
        } else {
            // set light pump thu cong
            savedState.setModeLight("notAuto");
            savedState.setModePump("notAuto");
            System.out.println("da chay va thay enabled false");
        }

        // save to database
        stateRepository.save(savedState);

        System.out.println(savedState.toString());
    }

    // Gọi phương thức này để cập nhật trạng thái thiết bị pump
    public void updateStatePump(float humidityAir, float moistureSoil, int lightLevel, float temperature) {
        controlPump(moistureSoil, temperature, humidityAir);

        // update savedState and save to database
        savedState.setAirState(humidityAir);
        savedState.setSoilState(moistureSoil);
        savedState.setLightLevelState(lightLevel);
        savedState.setTemperatureState(temperature);

        if (smartControllerState.isEnabled()) {
            // set light vs pum auto
            savedState.setModeLight("auto");
            savedState.setModePump("auto");
            System.out.println("da chay va thay enabled true");
        } else {
            // set light pump thu cong
            savedState.setModeLight("notAuto");
            savedState.setModePump("notAuto");
            System.out.println("da chay va thay enabled false");
        }

        // save to database
        stateRepository.save(savedState);

        System.out.println(savedState.toString());
    }

    // Gọi phương thức này để cập nhật trạng thái thiết bị light
    public void updateStateLight(float humidityAir, float moistureSoil, int lightLevel, float temperature) {
        controlLight(lightLevel);

        // update savedState and save to database
        savedState.setAirState(humidityAir);
        savedState.setSoilState(moistureSoil);
        savedState.setLightLevelState(lightLevel);
        savedState.setTemperatureState(temperature);

        if (smartControllerState.isEnabled()) {
            // set light vs pum auto
            savedState.setModeLight("auto");
            savedState.setModePump("auto");
            System.out.println("da chay va thay enabled true");
        } else {
            // set light pump thu cong
            savedState.setModeLight("notAuto");
            savedState.setModePump("notAuto");
            System.out.println("da chay va thay enabled false");
        }

        // save to database
        stateRepository.save(savedState);

        System.out.println(savedState.toString());
    }

    private void controlPump(float moistureSoil, float temperature, float humidityAir) {

        boolean islessMinAir = humidityAir < HUMIDITY_AIR_MIN;
        boolean isMoreMaxAir = humidityAir > HUMIDITY_AIR_MAX;

        boolean isSoilWet = moistureSoil > MOISTURE_SOIL_THRESHOLD_MAX; // lon hon max soil
        boolean isSoilDry = moistureSoil < MOISTURE_SOIL_THRESHOLD_MIN; // be thua min soil
        boolean isInMiddleOfSoil = moistureSoil < MOISTURE_SOIL_THRESHOLD_MAX
                && moistureSoil > MOISTURE_SOIL_THRESHOLD_MIN; // middle ò min vs max

        if (isSoilDry) {
            // Bật máy bơm nếu đất quá khô
            System.out.println("Độ ẩm đất thấp = BẬT máy bơm");
            pump.turnOn();
            savedState.setPumpState("1");
        } else if (isInMiddleOfSoil) {
            if (islessMinAir) {
                // Bat máy bơm nếu khong khi thieu do4rfv ẩm
                System.out.println("Độ ẩm khong khi thap = BAT máy bơm");
                pump.turnOn();
                savedState.setPumpState("1");
            } else if (isMoreMaxAir) {
                // Tắt máy bơm nếu khong khi qua ẩm
                System.out.println("Độ ẩm khong khi cao = TẮT máy bơm");
                pump.turnOff();
                savedState.setPumpState("0");
            }
        } else if (isSoilWet) {
            // Tắt máy bơm nếu dat qua ẩm
            System.out.println("Độ ẩm đất cao = TẮT máy bơm");
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
