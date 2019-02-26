package telran.ashkelon2018.mishpahug.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "email" })
public class ParticipantDto {
	String email;
	String fullName;
	String confession;
	String gender;
	int age;
	List<String> pictureLink;
	String phoneNumber;
	String maritalStatus;
	List<String> foodPreferences;
	List<String> languages;
	double rate;
	int numberOfVoters;
	boolean isInvited;
	boolean voted;

	public ParticipantDto(String email) {
		super();
		this.email = email;
	}

}
