package es.quark.fullstatckangularspring.repository;

import es.quark.fullstatckangularspring.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<RoomEntity, Long> {

    RoomEntity findById(Long id);
}
