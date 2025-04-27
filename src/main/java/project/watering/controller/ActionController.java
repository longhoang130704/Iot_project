package project.watering.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.watering.entity.Action;
import project.watering.service.interfaceLayer.ActionService;

@RestController
@RequestMapping("action")
public class ActionController {
    @Autowired
    private ActionService actionService;

    @GetMapping
    public ResponseEntity<Page<Action>> getAllActions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Action> actions = actionService.getAllActions(PageRequest.of(page, size));
        return ResponseEntity.ok(actions);
    }

    @GetMapping("/{gardenName}")
    public ResponseEntity<List<Action>> getActionByUserId(@PathVariable String gardenName) {
        Optional<List<Action>> actions = actionService.getActionByGardenName(gardenName);
        return actions.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
