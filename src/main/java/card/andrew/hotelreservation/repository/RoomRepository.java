package card.andrew.hotelreservation.repository;

import java.util.List;

import card.andrew.hotelreservation.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;



public interface RoomRepository extends CrudRepository<RoomEntity, Long> {

	//RoomEntity findById(Long id);
}
