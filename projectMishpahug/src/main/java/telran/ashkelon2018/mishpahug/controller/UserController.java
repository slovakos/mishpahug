package telran.ashkelon2018.mishpahug.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.mishpahug.dto.UserRegistrationResponseDto;
import telran.ashkelon2018.mishpahug.dto.UserResponseDto;
import telran.ashkelon2018.mishpahug.dto.UserStaticFieldsDto;
import telran.ashkelon2018.mishpahug.dto.UserUpdateDto;
import telran.ashkelon2018.mishpahug.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService service;

	@PostMapping("/user/login")
	public UserResponseDto LoginUser(Principal principal) {
		return service.LoginUser(principal.getName());

	}

	@PostMapping("/user/registration")
	public UserRegistrationResponseDto RegistrationUser(@RequestHeader("Authorization") String token) {
		return service.RegistrationUser(token);

	}

	@PostMapping("/user/profile")
	public UserResponseDto updateUserProfile(@RequestBody UserUpdateDto userUpdateDto,
			Principal principal) {
		return service.updateUserProfile(userUpdateDto, principal.getName());
	}

	@GetMapping("/user/profile")
	public UserResponseDto getUserProfile(Principal principal) {
		return service.getUserProfile(principal.getName());
	}

	@GetMapping("/user/staticfields")
	public UserStaticFieldsDto getStaticFields() {
		return service.getStaticFields();

	}

}
