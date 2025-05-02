package project.watering.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import project.watering.entity.State;

@Repository
public interface StateRepository extends MongoRepository<State, String> {
    Page<State> findAll(Pageable pageable);

    List<State> findByTimeBetweenOrderByTimeDesc(LocalDateTime start, LocalDateTime end);
}
