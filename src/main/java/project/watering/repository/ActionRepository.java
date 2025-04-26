package project.watering.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import project.watering.entity.Action;

@Repository
public interface ActionRepository extends MongoRepository<Action, String> {
    Page<Action> findAll(Pageable pageable);

    Optional<Action> findByUserId(String userId);
}
