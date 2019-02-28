package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import telran.ashkelon2018.mishpahug.domain.Address;
import telran.ashkelon2018.mishpahug.domain.EventId;

@AllArgsConstructor
@Getter
@Builder
public class ParticipationListResponseDto {
	EventId eventId;
	String title;
	String holiday;
	String confession;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate date;
	@JsonFormat(pattern = "HH:mm:ss")
	LocalTime time;
	int duration;
	Address address;
	List<String> food;
	String description;
	String status;
	OwnerDto owner;
	public ParticipationListResponseDto(EventId eventId, String title, String holiday, String confession,
			LocalDate date, String description, String status, OwnerDto owner) {
		super();
		this.eventId = eventId;
		this.title = title;
		this.holiday = holiday;
		this.confession = confession;
		this.date = date;
		this.description = description;
		this.status = status;
		this.owner = owner;
	}
	
	
}
