package telran.ashkelon2018.mishpahug.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class EventsListForCalendarResponseDto {
	List<EventForCalendarDto> myEvents;
	List<EventForCalendarDto> subscribedEvents;

	public EventsListForCalendarResponseDto() {
		this.myEvents = new ArrayList<>();
		this.subscribedEvents = new ArrayList<>();
	}

}
