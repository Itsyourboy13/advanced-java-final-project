package card.andrew.hotelreservation.repository;


import card.andrew.hotelreservation.entity.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {
}