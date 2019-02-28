package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.ashkelon2018.mishpahug.dto.AddressDto;
import telran.ashkelon2018.mishpahug.dto.Location;
import telran.ashkelon2018.mishpahug.dto.ParticipantDto;

@Document(collection = "event")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"eventId"})
public class Event {
	@Id
	EventId eventId;
	String title;
	String holiday;
	Address address;
	Location location;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime dateFrom;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime dateTo;
	int duration;
	String confession;
	List<String> food;
	String description;
	String status;
	String owner;
	Set<ParticipantDto> participants;

	
	public Event(EventId eventId, String title, String holiday, AddressDto addressDto, LocalDateTime dateFrom,
			LocalDateTime dateTo, int duration, String confession, List<String> food, String description) {
		super();
		this.eventId = eventId;
		this.title = title;
		this.holiday = holiday;
		this.address = new Address(addressDto.getCity(), addressDto.getPlace_id());
		this.location = addressDto.getLocation();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.confession = confession;
		this.duration = duration;
		this.food = food;
		this.description = description;
		this.owner = eventId.getOwner();
		this.participants = new HashSet<>();
		this.status = "in progress";
	}

}
