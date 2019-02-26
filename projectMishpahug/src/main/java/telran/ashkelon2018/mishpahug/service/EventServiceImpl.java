package telran.ashkelon2018.mishpahug.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.configuration.AccountConfiguration;
import telran.ashkelon2018.mishpahug.dao.EventRepository;
import telran.ashkelon2018.mishpahug.dao.UserRepository;
import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.domain.EventId;
import telran.ashkelon2018.mishpahug.domain.UserAccount;
import telran.ashkelon2018.mishpahug.dto.AddEventRequestDto;
import telran.ashkelon2018.mishpahug.dto.AddressDto;
import telran.ashkelon2018.mishpahug.dto.ChangeEventResponseDto;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventDto;
import telran.ashkelon2018.mishpahug.dto.EventForCalendarDto;
import telran.ashkelon2018.mishpahug.dto.EventListResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventsListForCalendarResponseDto;
import telran.ashkelon2018.mishpahug.dto.InviteToEventResponseDto;
import telran.ashkelon2018.mishpahug.dto.MyEventHistoryResponseDto;
import telran.ashkelon2018.mishpahug.dto.MyEventResponseDto;
import telran.ashkelon2018.mishpahug.dto.OwnerDto;
import telran.ashkelon2018.mishpahug.dto.ParticipantDto;
import telran.ashkelon2018.mishpahug.dto.ParticipationListResponseDto;
import telran.ashkelon2018.mishpahug.dto.SubscribedEventResponseDto;
import telran.ashkelon2018.mishpahug.exceptions.BusyDateException;
import telran.ashkelon2018.mishpahug.exceptions.InviteException;
import telran.ashkelon2018.mishpahug.exceptions.NotAssociatedEventException;
import telran.ashkelon2018.mishpahug.exceptions.RevoteException;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AccountConfiguration accountConfiguration;

	@Override
	public CodeResponseDto addEvent(AddEventRequestDto addEventRequestDto, String email) {
		// TODO
		LocalDateTime dateFrom = addEventRequestDto.getDate().atTime(addEventRequestDto.getTime());
		System.out.println(dateFrom);
		EventId eventId = new EventId(email, dateFrom);
		if (eventRepository.findById(eventId).orElse(null) != null) {
			throw new BusyDateException();
		}
		Event event = new Event(eventId, addEventRequestDto.getTitle(), addEventRequestDto.getHoliday(),
				addEventRequestDto.getAddress(), dateFrom, dateFrom.plusHours(addEventRequestDto.getDuration()),
				addEventRequestDto.getDuration(), addEventRequestDto.getConfession(), addEventRequestDto.getFood(),
				addEventRequestDto.getDescription());
		eventRepository.save(event);
		return new CodeResponseDto(200, "Event is created");
	}

	@Override
	public EventsListForCalendarResponseDto getEventListForCalendar(int month, String email) {
		int year = Year.now().getValue();
		LocalDate from = LocalDate.of(year, month, 1);
		LocalDate to = from.with(TemporalAdjusters.firstDayOfNextMonth());
		List<Event> events = eventRepository.findByDateFromBetweenAndStatusIn(from, to, "pending", "in progress");
		List<EventForCalendarDto> myEvents = new ArrayList<>();
		List<EventForCalendarDto> subscribedEvents = new ArrayList<>();
		for (Event event : events) {
			if (event.getOwner().equals(email)) {
				myEvents.add(convertEventToEventForCalendarDto(event));
			}
			if (isSubscribed(event.getParticipants(), email)) {
				subscribedEvents.add(convertEventToEventForCalendarDto(event));

			}
		}

		return new EventsListForCalendarResponseDto(myEvents, subscribedEvents);
	}

	@Override
	public MyEventResponseDto getMyEventInfo(EventId eventId, String email) {
		Event event = eventRepository.findById(eventId).get();
		if (!event.getOwner().equals(email)) {
			throw new NotAssociatedEventException();
		}
		Set<ParticipantDto> participants = new HashSet<>();
		if (event.getStatus().equals("in progress")) {
			participants = event.getParticipants();
		} else {
			for (ParticipantDto participantDto : event.getParticipants()) {
				if (participantDto.isInvited()) {
					participants.add(participantDto);
				}
			}
		}
		return MyEventResponseDto.builder().eventId(eventId).title(event.getTitle()).holiday(event.getHoliday())
				.confession(event.getConfession()).date(event.getDateFrom().toLocalDate())
				.time(event.getDateFrom().toLocalTime()).duration(event.getDuration()).food(event.getFood())
				.description(event.getDescription()).status(event.getStatus()).participants(participants).build();
	}

	@Override
	public SubscribedEventResponseDto getSubscribedEventInfo(EventId eventId, String email) {
		Event event = eventRepository.findById(eventId).get();
		if (!isSubscribed(event.getParticipants(), email)) {
			throw new NotAssociatedEventException();
		}

		AddressDto address;
		if (event.getStatus().equals("in progress")) {
			address = new AddressDto(event.getAddress().getCity());
		} else {
			address = event.getAddress();
		}
		OwnerDto owner = convertUserToOwner(userRepository.findById(eventId.getOwner()).get());
		return SubscribedEventResponseDto.builder().eventId(eventId).title(event.getTitle()).holiday(event.getHoliday())
				.confession(event.getConfession()).date(event.getDateFrom().toLocalDate())
				.time(event.getDateFrom().toLocalTime()).duration(event.getDuration()).address(address)
				.food(event.getFood()).description(event.getDescription()).status(event.getStatus()).owner(owner)
				.build();
	}

	@Override
	public List<MyEventResponseDto> getMyEventsList(String email) {
		List<Event> list = eventRepository.findByOwnerAndStatusIn(email, "in progress", "pending");
		return list.stream().map(e -> convertEventToMyEventResponseDto(e)).collect(Collectors.toList());
	}

	@Override
	public List<MyEventHistoryResponseDto> getMyEventsHistory(String email) {
		return eventRepository.findByOwnerAndStatusIn(email, "done").stream()
				.map(e -> convertEventToEventHistory(e.getEventId()))
				.sorted((x, y) -> y.getDate().compareTo(x.getDate())).collect(Collectors.toList());
	}

	@Override
	public List<ParticipationListResponseDto> getParticipationList(String email) {
		//TODO
		UserAccount user = userRepository.findById(email).get();
		List<Event> listEvent = new ArrayList<>();
		List<EventId> list = user.getSubscribedEvents();
		for (EventId eventId : list) {
			listEvent.add(eventRepository.findById(eventId).get());
		}
		
		List<ParticipationListResponseDto> listResponse = new ArrayList<>();
		for (Event event : listEvent) {
			OwnerDto owner = convertUserToOwner(userRepository.findById(email).get());
			if (event.getStatus().equals("in progress")) {
				listResponse.add(new ParticipationListResponseDto(event.getEventId(), event.getTitle(),
						event.getHoliday(), event.getConfession(), event.getDateFrom().toLocalDate(),
						event.getDateFrom().toLocalTime(), event.getDuration(),
						new AddressDto(event.getAddress().getCity()), event.getFood(), event.getDescription(),
						event.getStatus(), owner));
			}
			if (event.getStatus().equals("pending")) {
				listResponse.add(new ParticipationListResponseDto(event.getEventId(), event.getTitle(),
						event.getHoliday(), event.getConfession(), event.getDateFrom().toLocalDate(),
						event.getDateFrom().toLocalTime(), event.getDuration(), event.getAddress(), event.getFood(),
						event.getDescription(), event.getStatus(), owner));
			}
			if (event.getStatus().equals("done")) {
				listResponse.add(new ParticipationListResponseDto(event.getEventId(), event.getTitle(),
						event.getHoliday(), event.getConfession(), event.getDateFrom().toLocalDate(),
						event.getDescription(), event.getStatus(), owner));
			}
		}
		listResponse.sort((x, y) -> y.getDate().compareTo(x.getDate()));
		return listResponse;
	}

	@Override
	public CodeResponseDto subscribeToEvent(EventId eventId, String email) {

		Event event = eventRepository.findById(eventId).get();
		ParticipantDto participant = new ParticipantDto(email);
		if (event.getOwner().equals(email) || !event.getParticipants().add(participant)) {
			return new CodeResponseDto(409, "User is the owner of the event or already subscribed to it!");
		}
		UserAccount user = userRepository.findById(participant.getEmail()).get();
		participant.setFullName(user.getFirstName() + " " + user.getLastName());
		participant.setConfession(user.getConfession());
		participant.setGender(user.getGender());
		participant.setAge(Period.between(user.getDateOfBirth(), LocalDate.now()).getYears());
		participant.setPictureLink(user.getPictureLink());
		participant.setPhoneNumber(user.getPhoneNumber());
		participant.setMaritalStatus(user.getMaritalStatus());
		participant.setFoodPreferences(user.getFoodPreferences());
		participant.setLanguages(user.getLangueges());
		participant.setRate(user.getRate());
		participant.setNumberOfVoters(user.getNumberOfVoters());
		participant.setVoted(false);
		participant.setInvited(false);
		user.getSubscribedEvents().add(eventId);
		userRepository.save(user);
		eventRepository.save(event);
		return new CodeResponseDto(200, "User subscribed to the event!");
	}

	@Override
	public CodeResponseDto unsubscribeFromEvent(EventId eventId, String email) {
		Event event = eventRepository.findById(eventId).get();
		if (!event.getStatus().equals("in progress")) {
			return new CodeResponseDto(409, "User can't unsubscribe from the event!");
		}
		event.getParticipants().removeIf(p -> p.getEmail().equals(email));
		UserAccount user = userRepository.findById(email).get();
		user.getSubscribedEvents().removeIf(e -> e.equals(eventId));
		userRepository.save(user);
		eventRepository.save(event);
		return new CodeResponseDto(200, "User unsubscribed from the event!");
	}

	@Override
	public CodeResponseDto voteForEvent(String email, EventId eventId, double voteCount) {
		Event event = eventRepository.findById(eventId).get();
		ParticipantDto participant = event.getParticipants().stream().filter(p -> p.getEmail().equals(email)).findFirst()
				.orElse(null);
		if (event.getStatus() != "done" || participant == null || participant.isVoted()) {
			throw new RevoteException();
		}
		participant.setVoted(true);
		UserAccount  user = userRepository.findById(email).get();
		user.getSubscribedEvents().removeIf(e -> e.equals(eventId));
		UserAccount owner = userRepository.findById(event.getOwner()).get();
		double rateOwner = owner.getRate();
		int numberOfVoters = owner.getNumberOfVoters() + 1;
		double newRate = (rateOwner * numberOfVoters + voteCount) / numberOfVoters;
		owner.setRate(newRate);
		owner.setNumberOfVoters(numberOfVoters);
		eventRepository.save(event);
		userRepository.save(user);
		userRepository.save(owner);
		return new CodeResponseDto(200, "User vote is accepted!");
	}

	@Override
	public InviteToEventResponseDto inviteToEvent(String email, EventId eventId, String userId) {
		// TODO Auto-generated method stub
		// check owner?
		Event event = eventRepository.findById(eventId).get();
		if (!email.equals(event.getOwner())) {
			throw new NotAssociatedEventException();
		}
		ParticipantDto user = event.getParticipants().stream().filter(p -> p.getEmail().equals(userId)).findFirst()
				.orElse(null);
		if (user == null || user.isInvited()) {
			throw new InviteException();
		}
		user.setInvited(true);
		eventRepository.save(event);
		return new InviteToEventResponseDto(userId);
	}

	@Override
	public EventListResponseDto listOfEventsInProgress() {
		if (eventRepository == null) {
			return null;
		}
		List<Event> listEventsInProgress = eventRepository.findByStatus("in progress");
		List<EventDto> content = new ArrayList<>();
		listEventsInProgress.forEach(x -> content.add(convertEventToEventDto(x)));
		content.sort((x, y) -> y.getDate().compareTo(x.getDate()));
		return new EventListResponseDto(content);
	}

	private EventDto convertEventToEventDto(Event event) {
		UserAccount user = userRepository.findById(event.getEventId().getOwner()).get();
		return EventDto.builder().eventId(event.getEventId()).title(event.getTitle()).holiday(event.getHoliday())
				.confession(event.getConfession()).date(event.getDateFrom().toLocalDate())
				.time(event.getDateFrom().toLocalTime()).duration(event.getDuration()).address(event.getAddress())
				.food(event.getFood()).description(event.getDescription()).owner(convertUserToOwner(user)).build();
	}

	private OwnerDto convertUserToOwner(UserAccount user) {
		return OwnerDto.builder().fullName(user.getFirstName() + " " + user.getLastName())
				.confession(user.getConfession()).gender(user.getGender())
				.age(LocalDate.now().getYear() - user.getDateOfBirth().getYear()).pictureLink(user.getPictureLink())
				.maritalStatus(user.getMaritalStatus()).foodPreferences(user.getFoodPreferences())
				.languages(user.getLangueges()).rate(user.getRate()).numberOfVoters(user.getNumberOfVoters()).build();
	}

	private MyEventResponseDto convertEventToMyEventResponseDto(Event event) {
		return MyEventResponseDto.builder().eventId(event.getEventId()).title(event.getTitle())
				.holiday(event.getHoliday()).confession(event.getConfession()).date(event.getDateFrom().toLocalDate())
				.time(event.getDateFrom().toLocalTime()).duration(event.getDuration()).food(event.getFood())
				.description(event.getDescription()).status(event.getStatus()).participants(event.getParticipants())
				.build();
	}

	private EventForCalendarDto convertEventToEventForCalendarDto(Event event) {
		return EventForCalendarDto.builder().eventId(event.getEventId()).title(event.getTitle())
				.date(event.getDateFrom().toLocalDate()).time(event.getDateFrom().toLocalTime())
				.duration(event.getDuration()).status(event.getStatus()).build();
	}

	@Override
	public ChangeEventResponseDto changeEventStatus(String email, EventId eventId) {
		Event event = eventRepository.findById(eventId).get();
		if (!email.equals(event.getOwner())) {
			throw new NotAssociatedEventException();
		}
		event.setStatus("pending");
		eventRepository.save(event);
		return new ChangeEventResponseDto(eventId, event.getStatus());
	}

	private boolean isSubscribed(Set<ParticipantDto> set, String email) {
		for (ParticipantDto participant : set) {
			if (participant.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	private MyEventHistoryResponseDto convertEventToEventHistory(EventId eventId) {
		Event event = eventRepository.findById(eventId).get();
		return MyEventHistoryResponseDto.builder().eventId(eventId).title(event.getTitle()).holiday(event.getHoliday())
				.confession(event.getConfession()).date(event.getDateFrom().toLocalDate()).food(event.getFood())
				.description(event.getDescription()).status(event.getStatus()).build();
	}

	// private Predicate<Event> predicateParticipant(String email) {
	// UserAccount user = userRepository.findById(email).get();
	// return e -> user.getSubscribedEvents().contains(e.getEventId());
	//
	// }

}
