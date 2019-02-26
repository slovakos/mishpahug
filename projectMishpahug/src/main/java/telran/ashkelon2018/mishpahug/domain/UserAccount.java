package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "user")
@EqualsAndHashCode(of = { "email" })
public class UserAccount {
	@Id
	String email;
	String password;
	String firstName;
	String lastName;
	String phoneNumber;
	LocalDate dateOfBirth;
	String maritalStatus;
	String gender;
	String confession;
	List<String> langueges;
	List<String> foodPreferences;
	String description;
	List<String> pictureLink;
	List<EventId> subscribedEvents;
	double rate;
	int numberOfVoters;
	boolean isRegistered;

	public UserAccount(String email, String password) {
		this.email = email;
		this.password = password;
			
	}

}
