package telran.ashkelon2018.mishpahug.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.configuration.AccountConfiguration;
import telran.ashkelon2018.configuration.UserCredentials;
import telran.ashkelon2018.mishpahug.dao.NotificationRepository;
import telran.ashkelon2018.mishpahug.dao.UserRepository;
import telran.ashkelon2018.mishpahug.domain.UserAccount;
import telran.ashkelon2018.mishpahug.domain.UserNotifications;
import telran.ashkelon2018.mishpahug.dto.UserRegistrationResponseDto;
import telran.ashkelon2018.mishpahug.dto.UserResponseDto;
import telran.ashkelon2018.mishpahug.dto.UserStaticFieldsDto;
import telran.ashkelon2018.mishpahug.dto.UserUpdateDto;
import telran.ashkelon2018.mishpahug.exceptions.EmptyProfileException;
import telran.ashkelon2018.mishpahug.exceptions.UserIsExistsException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AccountConfiguration accountConfiguration;

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	PasswordEncoder encoder;

	@Override
	public UserRegistrationResponseDto RegistrationUser(String token) {
		UserCredentials credentials = accountConfiguration.tokenDecode(token);
		if (userRepository.existsById(credentials.getEmail())) {
			throw new UserIsExistsException();
		}
		String hashPassword = encoder.encode(credentials.getPassword());
		UserAccount user = new UserAccount(credentials.getEmail(), hashPassword);
		userRepository.save(user);
		return new UserRegistrationResponseDto();
	}

	@Override
	public UserResponseDto updateUserProfile(UserUpdateDto userUpdateDto, String email) {
		UserAccount user = userRepository.findById(email).get();
		if (!user.isRegistered()) {
			user.setRegistered(true);
			user.setSubscribedEvents(new ArrayList<>());
			notificationRepository.save(new UserNotifications(email));
		}
		user.setFirstName(userUpdateDto.getFirstName());
		user.setLastName(userUpdateDto.getLastName());
		user.setDateOfBirth(userUpdateDto.getDateOfBirth());
		user.setGender(userUpdateDto.getGender());
		user.setMaritalStatus(userUpdateDto.getMaritalStatus());
		user.setConfession(userUpdateDto.getConfession());
		user.setPictureLink(userUpdateDto.getPictureLink());
		user.setPhoneNumber(userUpdateDto.getPhoneNumber());
		user.setFoodPreferences(userUpdateDto.getFoodPreferences());
		user.setLangueges(userUpdateDto.getLanguages());
		user.setDescription(userUpdateDto.getDescription());
		userRepository.save(user);
		return convertToUserResponseDto(user);
	}

	@Override
	public UserResponseDto getUserProfile(String email) {
		UserAccount user = userRepository.findById(email).get();
		if (user.isRegistered()) {
			return convertToUserResponseDto(user);
		}
		throw new EmptyProfileException();

	}

	@Override
	public UserResponseDto LoginUser(String email) {
		UserAccount user = userRepository.findById(email).get();
		if (user.isRegistered()) {
			return convertToUserResponseDto(user);
		}
		throw new EmptyProfileException();
	}

	@Override
	public UserStaticFieldsDto getStaticFields() {
		return new UserStaticFieldsDto();
	}

	private UserResponseDto convertToUserResponseDto(UserAccount user) {
		return new UserResponseDto(user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getGender(),
				user.getMaritalStatus(), user.getConfession(), user.getPictureLink(), user.getPhoneNumber(),
				user.getFoodPreferences(), user.getLangueges(), user.getDescription(), user.getRate(),
				user.getNumberOfVoters());
	}

}
