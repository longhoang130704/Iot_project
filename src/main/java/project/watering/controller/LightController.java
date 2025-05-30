package project.watering.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.watering.bean.SmartControllerState;
import project.watering.entity.Action;
import project.watering.iot.Factory.Device;
import project.watering.iot.Factory.FactoryRegistry;
import project.watering.service.interfaceLayer.ActionService;

@RestController
@RequestMapping("/light")
public class LightController {
    @Autowired
    private SmartControllerState smartControllerState;

    @Autowired
    private ActionService actionService;

    // @Autowired
    // @Qualifier("lightDevice")
    // private Device light;
    private Device light = FactoryRegistry.createDevice("light");

    @GetMapping("/on")
    public ResponseEntity<String> turnOnPump(@RequestParam String userId,
            @RequestParam String gardenName) {
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
