package com.example.mareu.model;

import java.util.List;
import java.util.Objects;

public class Meeting {

    /** Identifier */
    private long id;

    /** About the meeting */
    private String name;

    /** Date of the meeting */
    private String day;

    /** Hour of the meeting */
    private String hour;

    /** Room of the meeting */
    private String room;

    /** List of participants */
    private List<String> participants;

    /** Duration of meeting */
    private String duration;

    public Meeting(long id, String name, String day, String hour, String room, List<String> participants, String duration) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.hour = hour;
        this.room = room;
        this.participants = participants;
        this.duration = duration;
    }

    public long getId() { return id; }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() { return day; }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() { return hour; }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getRoom() { return room; }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Meeting meeting = (Meeting) obj;
        return Objects.equals(id, meeting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
