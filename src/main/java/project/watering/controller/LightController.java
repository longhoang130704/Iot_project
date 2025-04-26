package project.watering.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.watering.bean.SmartControllerState;
import project.watering.entity.Action;
import project.watering.iot.Factory.Device;
import project.watering.service.interfaceLayer.ActionService;

@RestController
@RequestMapping("/light")
public class LightController {
    @Autowired
    private SmartControllerState smartControllerState;

    @Autowired
    private ActionService actionService;

    @Autowired
    @Qualifier("lightDevice")
    private Device light;

    @GetMapping("/on")
    public ResponseEntity<String> turnOnPump(@RequestParam String userId,
            @RequestParam String gardenName,
            @RequestParam int minute) {
        try {
            light.turnOn();
            // luu lai action

            Action newAction = new Action();
            newAction.setUserId(userId);
            newAction.setGardenName(gardenName);
            newAction.setAction(2);

            // save to database
            actionService.saveAction(newAction);

            // tat che do auto
            smartControllerState.setEnabled(false);

            if (!smartControllerState.isEnabled()) {
                System.out.println("Da tat tu dong");
            }

            // hen gio tat den
            // chuyển phút -> giây
            int durationSeconds = minute * 60;

            // hẹn giờ tắt
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(() -> {
                try {
                    light.turnOff();
                    System.out.println("Đã tự động tắt đèn sau " + minute + " phút");
                } catch (Exception e) {
                    System.out.println("Lỗi khi tắt đèn tự động: " + e.getMessage());
                }
            }, durationSeconds, java.util.concurrent.TimeUnit.SECONDS);

            return ResponseEntity.ok().body("Turn on light success");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

    @GetMapping("/off")
    public ResponseEntity<String> turnOffPump(@RequestParam String userId, @RequestParam String gardenName) {
        try {
            light.turnOff();
            // luu lai action
            Action newAction = new Action();
            newAction.setUserId(userId);
            newAction.setGardenName(gardenName);
            newAction.setAction(3);

            // save to database
            actionService.saveAction(newAction);

            // tat che do tu dong
            // TO DO
            smartControllerState.setEnabled(false);

            if (!smartControllerState.isEnabled()) {
                System.out.println("Da tat tu dong");
            }
            return ResponseEntity.ok().body("Turn off light success");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }
}
