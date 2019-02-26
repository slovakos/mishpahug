package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(Include.NON_NULL)
public class UserRegistrationResponseDto {

	String firstName;
	String lastName;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate dateOfBirth;
	List<String> gender;
	List<String> maritalStatus;
	List<String> confession;
	List<String> pictureLink;
	// \"https://i.pinimg.com/originals/23/bf/25/23bf251cab0b742f9257506357e70f38.png\",
	// \"https://www.planwallpaper.com/static/images/Blue-Green-beautiful-nature-21891805-1920-1200_jX7pvvz.jpg\"
	String phoneNumber;
	List<String> foodPreferences;
	List<String> languages;

	String description;

	public UserRegistrationResponseDto() {
		this.confession = Arrays.asList("religious", "irreligious");

		this.gender = Arrays.asList("male", "female");

		this.maritalStatus = Arrays.asList("married", "single", "couple");

		this.foodPreferences = Arrays.asList("vegetarian", "kosher", "non-vegetarian");

		this.languages = Arrays.asList("Hebrew", "English", "Russian");

	}

}
