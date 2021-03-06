package telran.ashkelon2018.mishpahug.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.domain.EventId;
import telran.ashkelon2018.mishpahug.dto.Location;

public interface EventRepository extends MongoRepository<Event, EventId> {
	List<Event> findByDateFromBetweenAndStatusIn(LocalDate fromDate, LocalDate toDate, String... statuses);

	List<Event> findByDateFromBetweenAndOwnerIn(LocalDate fromDate, LocalDate toDate, String email);

	List<Event> findByStatus(String stasus);

	List<Event> findByOwnerAndStatusIn(String email, String... statuses);

	List<Event> findByOwnerAndDateFromIn(String email, LocalDate date);

	// List<Event> findByAll(Predicate<Event> predicate);

	// // {'geoNear' : 'location', 'near' : [x, y] }
	GeoResults<Event> findByLocationNear(Location location);
	//
	// // No metric: {'geoNear' : 'person', 'near' : [x, y], maxDistance : distance
	// }

	/*
	 * {'geoNear' : 'person', 'near' : [x, y], 'maxDistance' : distance,
	 * 'distanceMultiplier' : metric.multiplier, 'spherical' : true }
	 */
	GeoResults<Event> findByLocationNear(Location location, Distance distance);

}
