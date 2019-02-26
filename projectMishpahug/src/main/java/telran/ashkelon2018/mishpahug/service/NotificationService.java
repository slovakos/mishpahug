package telran.ashkelon2018.mishpahug.service;

import java.util.List;

import telran.ashkelon2018.mishpahug.domain.Notification;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.NotificationsCountResponseDto;


public interface NotificationService {
	List<Notification> getNotificationsList(String token);

	CodeResponseDto notificationIsRead(String token, int notificationId);

	NotificationsCountResponseDto countUnreadNotifications(String token);
	
	CodeResponseDto addFirebaseToken(String token, String fireBaseToken);
	
	CodeResponseDto deleteFirebaseToken(String token, String fireBaseToken);
	
}
