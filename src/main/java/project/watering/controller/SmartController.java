package project.watering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.watering.service.interfaceLayer.AdafruitIOClient;
import project.watering.service.logicLayer.SmartLogic;
import project.watering.util.DataFromFeed;

@RestController
@RequestMapping("/smart-controller")
public class SmartController {
    @Autowired
    private SmartLogic smartLogic;

    public static float moistureSoilState;
    public static float moistureAirState;
    public static int lightLevel;
    public static float temperatureState;

    private boolean enabled = false; // controller scheduling

    // @Scheduled(fixedRate = 300000) 5 phut
    @Scheduled(fixedRate = 10000) // 10s
    public void smartController() {
        if (!enabled)
            return;
        // get all state
        getStateOfAll();

        smartLogic.updateState(
                moistureAirState,
                moistureSoilState,
                lightLevel,
                temperatureState);

        // return
    }

    @GetMapping("/start")
    public ResponseEntity<String> start() {
        enabled = true;
        return ResponseEntity.ok().body("start sucess");
    }

    @GetMapping("/stop")
    public ResponseEntity<String> stop() {
        enabled = false;
        return ResponseEntity.ok().body("stop sucess");
    }

    private void getStateOfAll() {
        System.out.println("---------------------------------------------");
        System.out.println("get all state");

        // temperature state
        String temperatureSensor = AdafruitIOClient.getLatestData("v1");
        System.out.println(temperatureSensor);
        temperatureState = Float.parseFloat(DataFromFeed.getOnlyValue(temperatureSensor));

        // light level state
        String lightSensor = AdafruitIOClient.getLatestData("v4");
        System.out.println(lightSensor);
        lightLevel = Integer.parseInt(DataFromFeed.getOnlyValue(lightSensor));

        // moisture soil state
        String soilSensor = AdafruitIOClient.getLatestData("v3");
        System.out.println(soilSensor);
        moistureSoilState = Float.parseFloat(DataFromFeed.getOnlyValue(soilSensor));

        // moisture air state
        String airSensor = AdafruitIOClient.getLatestData("v2");
        System.out.println(airSensor);
        moistureAirState = Float.parseFloat(DataFromFeed.getOnlyValue(airSensor));
    }
}
