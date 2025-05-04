package com.example.tracker.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class Event {

	
	@NotBlank( message="Event ID cannot be blank or empty. Please provide a valid value.")
	private String eventId;

	@NotNull( message="Event status must be provided. It cannot be null.")
    private EventStatus status;



    public Event(@NotBlank String eventId, @NotNull EventStatus status) {
		super();
		this.eventId = eventId;
		this.status = status;
	}

	public Event() {
		this.eventId = null;
		this.status = EventStatus.NOT_LIVE;
	}

	public enum EventStatus {
        LIVE, NOT_LIVE
    }

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;

	    Event event = (Event) o;
	    return eventId.equals(event.eventId) && status == event.status;
	}
	
	@Override
	public int hashCode() {
	    int result = eventId.hashCode();
	    result = 31 * result + status.hashCode();
	    return result;
	}

}
