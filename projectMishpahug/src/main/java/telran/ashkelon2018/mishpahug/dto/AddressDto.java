package telran.ashkelon2018.mishpahug.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonInclude(Include.NON_NULL)
public class AddressDto {

	String city;
	String place_id;
	Location location;

	public AddressDto(String city) {
		super();
		this.city = city;
	}

}
