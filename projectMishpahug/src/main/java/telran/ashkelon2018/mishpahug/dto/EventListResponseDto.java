package telran.ashkelon2018.mishpahug.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class EventListResponseDto {
	List<EventDto> content;
	int totalElements;
	int totalPages;
	int size;
	int number;
	int numberOfElements;
	boolean first;
	boolean last;

	//TODO "sort": null
	public EventListResponseDto(List<EventDto> content) {
		this.content = content;
		this.totalElements = content.size();
		this.totalPages = 12;
		this.size = content.size() % 12 > 0 ? content.size() / 12 + 1 : content.size() / 12;
		this.first = true;

	}

}
