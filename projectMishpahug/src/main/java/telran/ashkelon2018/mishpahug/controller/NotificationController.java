package telran.ashkelon2018.mishpahug.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.mishpahug.domain.Notification;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.NotificationsCountResponseDto;
import telran.ashkelon2018.mishpahug.service.NotificationService;

@RestController
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;
	
	@GetMapping("/notification/list")
	public List<Notification> getNotificationsList(Principal principal){
		return notificationService.getNotificationsList(principal.getName());
	}

	@PutMapping("/notification/isRead/{notificationId}")
	public CodeResponseDto notificationIsRead(Principal principal, @PathVariable int notificationId) {
		return notificationService.notificationIsRead(principal.getName(), notificationId);
	}

	@GetMapping("/notification/count")
	public NotificationsCountResponseDto countUnreadNotifications(Principal principal) {
		return notificationService.countUnreadNotifications(principal.getName());
	}
	
	@PostMapping("/user/firebasetoken/add")
	public CodeResponseDto addFirebaseToken(@RequestHeader("Authorization")String token, @RequestBody String fireBaseToken) {
		// TODO Auto-generated method stub
		return notificationService.addFirebaseToken(token, fireBaseToken);
	}
	
	@DeleteMapping("/user/firebasetoken/delete")
	public CodeResponseDto deleteFirebaseToken(@RequestHeader("Authorization")String token, @RequestBody String fireBaseToken) {
		// TODO Auto-generated method stub
		return notificationService.addFirebaseToken(token, fireBaseToken);
	}

}
