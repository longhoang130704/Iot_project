package project.watering.service.interfaceLayer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import project.watering.entity.Action;
import project.watering.repository.ActionRepository;

@Service
public class ActionService {
    @Autowired
    private ActionRepository actionRepository;

    public Page<Action> getAllActions(Pageable pageable) {
        return actionRepository.findAll(pageable);
    }

    public Optional<List<Action>> getActionByGardenName(String gardenName) {
        return actionRepository.findByGardenName(gardenName);
    }

    public Action saveAction(Action action) {
        return actionRepository.save(action);
    }

    public void deleteAction(String id) {
        actionRepository.deleteById(id);
    }
}
