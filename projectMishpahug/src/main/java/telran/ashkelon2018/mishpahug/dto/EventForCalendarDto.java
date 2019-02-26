package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import telran.ashkelon2018.mishpahug.domain.EventId;

@Builder
@AllArgsConstructor
@Getter
public class EventForCalendarDto {
	EventId eventId;
	String title;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate date;
	@JsonFormat(pattern = "HH:mm:ss")
	LocalTime time;
	int duration;
	String status;
}
