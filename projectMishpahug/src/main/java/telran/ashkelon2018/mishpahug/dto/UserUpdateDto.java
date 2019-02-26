package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
@Getter
public class UserUpdateDto {
	String firstName;
	String lastName;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate dateOfBirth;
	String gender;
	String maritalStatus;
	String confession;
	List<String> pictureLink;
	// \"https://i.pinimg.com/originals/23/bf/25/23bf251cab0b742f9257506357e70f38.png\",
	// \"https://www.planwallpaper.com/static/images/Blue-Green-beautiful-nature-21891805-1920-1200_jX7pvvz.jpg\"
	String phoneNumber;
	List<String> foodPreferences;
	List<String> languages;

	String description;

}
