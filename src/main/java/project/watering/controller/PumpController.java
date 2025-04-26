package project.watering.controller;

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
@RequestMapping("/pump")
public class PumpController {

    @Autowired
    private SmartControllerState smartControllerState;

    @Autowired
    private ActionService actionService;

    @Autowired
    @Qualifier("pumpDevice")
    private Device pump;

    @GetMapping("/on")
    public ResponseEntity<String> turnOnPump(@RequestParam String userId, @RequestParam String gardenName) {
        try {
            pump.turnOn();
            // luu lai ai thao tac
            Action newAction = new Action();
            newAction.setUserId(userId);
            newAction.setGardenName(gardenName);
            newAction.setAction(0);

            // save to database
            actionService.saveAction(newAction);
            // tat che do auto
            smartControllerState.setEnabled(false);

            if (!smartControllerState.isEnabled()) {
                System.out.println("Da tat tu dong");
            }
            return ResponseEntity.ok().body("Turn on pump success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

    @GetMapping("/off")
    public ResponseEntity<String> turnOffPump(@RequestParam String userId, @RequestParam String gardenName) {
        try {
            pump.turnOff();
            // luu lai ai thao tac

            Action newAction = new Action();
            newAction.setUserId(userId);
            newAction.setGardenName(gardenName);
            newAction.setAction(1);

            // save to database
            actionService.saveAction(newAction);

            // tat che do auto
            smartControllerState.setEnabled(false);

            if (!smartControllerState.isEnabled()) {
                System.out.println("Da tat tu dong");
            }
            return ResponseEntity.ok().body("Turn off pump success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }
}
