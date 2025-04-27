package project.watering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.watering.bean.SmartControllerState;
import project.watering.entity.Action;
import project.watering.service.interfaceLayer.ActionService;
import project.watering.service.interfaceLayer.AdafruitIOClient;
import project.watering.service.logicLayer.SmartLogic;
import project.watering.util.DataFromFeed;

@RestController
@RequestMapping("/smart-controller")
public class SmartController {
    @Autowired
    private SmartLogic smartLogic;

    @Autowired
    private ActionService actionService;

    @Autowired
    private SmartControllerState smartControllerState;

    public static float moistureSoilState;
    public static float moistureAirState;
    public static int lightLevel;
    public static float temperatureState;

    // @Scheduled(fixedRate = 300000) 5 phut
    @Scheduled(fixedRate = 10000) // 10s
    public void smartController() {
        if (!smartControllerState.isEnabled())
            return;
        // get all state
        getStateOfAll();

        smartLogic.updateState(
                moistureAirState,
                moistureSoilState,
                lightLevel,
                temperatureState);

    }

    @GetMapping("/start")
    public ResponseEntity<String> start(@RequestParam String userId, @RequestParam String gardenName) {
        smartControllerState.setEnabled(true);
        // luu lai ai da thao tacs
        Action newAction = new Action();
        newAction.setUserId(userId);
        newAction.setGardenName(gardenName);
        newAction.setAction(4);

        // save to database
        actionService.saveAction(newAction);

        return ResponseEntity.ok().body("start sucess");
    }

    @GetMapping("/stop")
    public ResponseEntity<String> stop(@RequestParam String userId, @RequestParam String gardenName) {
        smartControllerState.setEnabled(false);
        // luu lai ai da thao tac
        Action newAction = new Action();
        newAction.setUserId(userId);
        newAction.setGardenName(gardenName);
        newAction.setAction(5);

        // save to database
        actionService.saveAction(newAction);

        return ResponseEntity.ok().body("stop sucess");
    }

    // ________________________PUMP_______________________________________
    // @Scheduled(fixedRate = 300000) 5 phut
    @Scheduled(fixedRate = 10000) // 10s
    public void smartControllerPump() {
        if (!smartControllerState.isEnabledPump())
            return;
        // get all state
        getStateOfAll();

        smartLogic.updateStatePump(
                moistureAirState,
                moistureSoilState,
                lightLevel,
                temperatureState);

    }

    @GetMapping("/pump/start")
    public ResponseEntity<String> startPump(@RequestParam String userId, @RequestParam String gardenName) {
        smartControllerState.setEnabledPump(true);
        // luu lai ai da thao tacs
        Action newAction = new Action();
        newAction.setUserId(userId);
        newAction.setGardenName(gardenName);
        newAction.setAction(6);

        // save to database
        actionService.saveAction(newAction);

        return ResponseEntity.ok().body("start pump auto sucess");
    }

    @GetMapping("/pump/stop")
    public ResponseEntity<String> stopPump(@RequestParam String userId, @RequestParam String gardenName) {
        smartControllerState.setEnabledPump(false);
        // luu lai ai da thao tac
        Action newAction = new Action();
        newAction.setUserId(userId);
        newAction.setGardenName(gardenName);
        newAction.setAction(7);

        // save to database
        actionService.saveAction(newAction);

        return ResponseEntity.ok().body("stop pump auto sucess");
    }

    // ________________________LIGHT_______________________________________
    // @Scheduled(fixedRate = 300000) 5 phut
    @Scheduled(fixedRate = 10000) // 10s
    public void smartControllerPumpLight() {
        if (!smartControllerState.isEnabledLight())
            return;
        // get all state
        getStateOfAll();

        smartLogic.updateStateLight(
                moistureAirState,
                moistureSoilState,
                lightLevel,
                temperatureState);

    }

    @GetMapping("/light/start")
    public ResponseEntity<String> startLight(@RequestParam String userId, @RequestParam String gardenName) {
        smartControllerState.setEnabledLight(true);
        // luu lai ai da thao tacs
        Action newAction = new Action();
        newAction.setUserId(userId);
        newAction.setGardenName(gardenName);
        newAction.setAction(8);

        // save to database
        actionService.saveAction(newAction);

        return ResponseEntity.ok().body("start light auto sucess");
    }

    @GetMapping("/light/stop")
    public ResponseEntity<String> stopLight(@RequestParam String userId, @RequestParam String gardenName) {
        smartControllerState.setEnabledLight(false);
        // luu lai ai da thao tac
        Action newAction = new Action();
        newAction.setUserId(userId);
        newAction.setGardenName(gardenName);
        newAction.setAction(9);

        // save to database
        actionService.saveAction(newAction);

        return ResponseEntity.ok().body("stop light auto sucess");
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
