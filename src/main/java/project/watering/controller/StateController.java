package project.watering.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.watering.bean.SmartControllerState;
import project.watering.entity.State;
import project.watering.repository.StateRepository;
import project.watering.service.interfaceLayer.AdafruitIOClient;
import project.watering.util.DataFromFeed;

@RestController
@RequestMapping
public class StateController {
    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private SmartControllerState smartControllerState;

    @GetMapping("/state")
    public Page<State> getAllState(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        //
        PageRequest pageable = PageRequest.of(page, size);
        return stateRepository.findAll(pageable);
    }

    @GetMapping("/state/last-7-days")
    public ResponseEntity<List<State>> getLast7DaysState() {
        List<State> result = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.minusDays(i);
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

            List<State> states = stateRepository
                    .findByTimeBetweenOrderByTimeDesc(startOfDay, endOfDay);

            if (!states.isEmpty()) {
                result.add(states.get(0)); // lấy bản ghi mới nhất trong ngày
            }
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/last-state")
    public State lastState() {
        State lastState = new State();

        // temperature state
        String temperatureSensor = AdafruitIOClient.getLatestData("v1");
        System.out.println(temperatureSensor);
        lastState.setTemperatureState(Float.parseFloat(DataFromFeed.getOnlyValue(temperatureSensor)));

        // light level state
        String lightSensor = AdafruitIOClient.getLatestData("v4");
        System.out.println(lightSensor);
        lastState.setLightLevelState(Integer.parseInt(DataFromFeed.getOnlyValue(lightSensor)));

        // moisture soil state
        String soilSensor = AdafruitIOClient.getLatestData("v3");
        System.out.println(soilSensor);
        lastState.setSoilState(Float.parseFloat(DataFromFeed.getOnlyValue(soilSensor)));

        // moisture air state
        String airSensor = AdafruitIOClient.getLatestData("v2");
        System.out.println(airSensor);
        lastState.setAirState(Float.parseFloat(DataFromFeed.getOnlyValue(airSensor)));

        // pump
        String pumpState = AdafruitIOClient.getLatestData("v10");
        System.out.println(pumpState);
        lastState.setPumpState(DataFromFeed.getOnlyValue(pumpState));

        // led
        String ledState = AdafruitIOClient.getLatestData("v11");
        System.out.println(DataFromFeed.getOnlyValue(ledState));
        lastState.setLightState(DataFromFeed.getOnlyValue(ledState));

        if (smartControllerState.isEnabled()) {
            // set light vs pum auto
            lastState.setModeLight("auto");
            lastState.setModePump("auto");
        } else {
            // set light pump thu cong
            lastState.setModeLight("notAuto");
            lastState.setModePump("notAuto");
        }

        return lastState;
    }
}
