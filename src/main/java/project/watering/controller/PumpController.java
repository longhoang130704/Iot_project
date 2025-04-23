package project.watering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.watering.iot.Factory.Device;

@RestController
@RequestMapping("/pump")
public class PumpController {

    @Autowired
    @Qualifier("pumpDevice")
    private Device pump;

    @GetMapping("/on")
    public ResponseEntity<String> turnOnPump() {
        try {
            pump.turnOn();
            return ResponseEntity.ok().body("Turn on pump success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

    @GetMapping("/off")
    public ResponseEntity<String> turnOffPump() {
        try {
            pump.turnOff();
            return ResponseEntity.ok().body("Turn off pump success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }
}
