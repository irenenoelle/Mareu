package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    /**
     * Get all meetings occurring on a specific day
     *
     * @param meetingDate the specific day timeStamp in ms
     * @return a list of meetings
     */
    @Override
    public List<Meeting> getMeetingsForGivenDate(String meetingDate) {
        List<Meeting> meetingsByDate = new ArrayList<>();
        for (Meeting n : meetings) {
            if (n.getDay().contentEquals(meetingDate)) {
                meetingsByDate.add(n);
            }
        }
        return meetingsByDate;
    }

    /**
     * Get all meetings taking place in given meeting room
     *
     * @param meetingRoom the given meeting room id
     * @return a list of meetings
     */
    @Override
    public List<Meeting> getMeetingsForGivenMeetingRoom(String meetingRoom) {
        List<Meeting> meetingsRoom = new ArrayList<>();
        for(Meeting n : meetings) {
            if (n.getRoom().equalsIgnoreCase(meetingRoom)) {
                meetingsRoom.add(n);
            }
        }
        return meetingsRoom;
    }

}
