package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import telran.ashkelon2018.mishpahug.domain.EventId;

@AllArgsConstructor
@Getter
@Builder
public class SubscribedEventResponseDto {
	EventId eventId;
	String title;
	String holiday;
	String confession;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate date;
	@JsonFormat(pattern = "HH:mm:ss")
	LocalTime time;
	int duration;
	AddressDto address;
	// "city": "Tel Aviv-Yafo"

	List<String> food;
	String description;
	String status;
	OwnerDto owner;

}
