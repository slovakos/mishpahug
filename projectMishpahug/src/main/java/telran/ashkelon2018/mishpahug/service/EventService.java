package telran.ashkelon2018.mishpahug.service;

import java.util.List;

import telran.ashkelon2018.mishpahug.domain.EventId;
import telran.ashkelon2018.mishpahug.dto.AddEventRequestDto;
import telran.ashkelon2018.mishpahug.dto.ChangeEventResponseDto;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventListResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventsListForCalendarResponseDto;
import telran.ashkelon2018.mishpahug.dto.InviteToEventResponseDto;
import telran.ashkelon2018.mishpahug.dto.MyEventHistoryResponseDto;
import telran.ashkelon2018.mishpahug.dto.MyEventResponseDto;
import telran.ashkelon2018.mishpahug.dto.ParticipationListResponseDto;
import telran.ashkelon2018.mishpahug.dto.SubscribedEventResponseDto;

public interface EventService {

	EventListResponseDto listOfEventsInProgress();

	CodeResponseDto addEvent(AddEventRequestDto addEventRequestDto, String email);

	EventsListForCalendarResponseDto getEventListForCalendar(int month, String email);

	MyEventResponseDto getMyEventInfo(EventId eventId, String email);

	SubscribedEventResponseDto getSubscribedEventInfo(EventId eventId, String email);

	List<MyEventResponseDto> getMyEventsList(String email);

	List<MyEventHistoryResponseDto> getMyEventsHistory(String email);

	List<ParticipationListResponseDto> getParticipationList(String email);

	CodeResponseDto voteForEvent(String event, EventId eventId, double voteCount);

	CodeResponseDto subscribeToEvent(EventId eventId, String email);

	CodeResponseDto unsubscribeFromEvent(EventId eventId, String email);

	InviteToEventResponseDto inviteToEvent(String email, EventId eventId, String userId);

	ChangeEventResponseDto changeEventStatus(String email, EventId eventId);

}
