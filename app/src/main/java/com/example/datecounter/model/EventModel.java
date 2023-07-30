package com.example.datecounter.model;

import java.io.Serializable;

/**
 * Represents an Event that a user need to finish within a time period
 * @author Niros
 */

public class EventModel implements Serializable {
    /**
     * Event contains a title and a end date;
     */
    String eventTitle;
    String eventDate;

    /**
     * Empty constructor for the event model
     */
    public EventModel() {
    }

    /**
     * get the event title
     * @return the event title
     */
    public String getEventTitle() {
        return eventTitle;
    }

    /**
     * changes the title of the event
     * @param eventTitle name of the event to be set
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     * get the date of a particular event
     * @return the date string
     */
    public String getEventDate() {
        return eventDate;
    }

    /**
     * changes the event date string
     * @param eventDate date of the event to be set
     */
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
