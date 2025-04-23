package project.watering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.watering.entity.State;
import project.watering.repository.StateRepository;

@RestController
@RequestMapping
public class StateController {
    @Autowired
    private StateRepository stateRepository;

    @GetMapping("/state")
    public Page<State> getAllState(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        //
        PageRequest pageable = PageRequest.of(page, size);
        return stateRepository.findAll(pageable);
    }

}
