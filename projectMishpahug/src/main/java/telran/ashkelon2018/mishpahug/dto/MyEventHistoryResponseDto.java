package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import telran.ashkelon2018.mishpahug.domain.EventId;

@AllArgsConstructor
@Builder
@Getter
public class MyEventHistoryResponseDto {
	EventId eventId;
	String title;
	String holiday;
	String confession;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate date;
	List<String> food;
	String description;
	String status;
}
