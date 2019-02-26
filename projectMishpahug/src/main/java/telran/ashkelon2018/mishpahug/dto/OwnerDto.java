package telran.ashkelon2018.mishpahug.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@AllArgsConstructor
@Builder
@Getter
public class OwnerDto {
	String fullName;
	String confession;
	String gender;
	int age;
	List<String> pictureLink;
	// "https://i.pinimg.com/originals/23/bf/25/23bf251cab0b742f9257506357e70f38.png",
	// "https://www.planwallpaper.com/static/images/Blue-Green-beautiful-nature-21891805-1920-1200_jX7pvvz.jpg"
	String maritalStatus;
	List<String> foodPreferences;
	List<String> languages;
	double rate;
	int numberOfVoters;

}
