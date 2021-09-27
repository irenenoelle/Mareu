package com.example.mareu.events;

import com.example.mareu.model.Meeting;

public class DetailMeetingEvent {

    /**
     * Meeting to see details
     */
    public Meeting meeting;

    /**
     * Constructor.
     * @param meeting
     */
    public DetailMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
