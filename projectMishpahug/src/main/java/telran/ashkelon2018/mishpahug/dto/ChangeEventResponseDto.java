package telran.ashkelon2018.mishpahug.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import telran.ashkelon2018.mishpahug.domain.EventId;

@AllArgsConstructor
@Getter
public class ChangeEventResponseDto {
	EventId eventId;
	String status;

}
