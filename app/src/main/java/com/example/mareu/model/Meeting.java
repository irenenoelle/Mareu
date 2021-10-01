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

    /** Time of the meeting */
    private String startTime;

    /** Room of the meeting */
    private String room;

    /** List of participants */
    private List<String> participants;

    /** End Time of meeting */
    private String endTime;

    /**
     * Constructor
     * @param id id of the meeting
     * @param name subject of the meeting
     * @param day day of the meeting
     * @param hour time of the meeting
     * @param room room of the meeting
     * @param participants particpants to the meeting
     * @param endTime end time of the meeting
     */
    public Meeting(long id, String name, String day, String hour, String room, List<String> participants, String endTime) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.startTime = hour;
        this.room = room;
        this.participants = participants;
        this.endTime = endTime;
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

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
