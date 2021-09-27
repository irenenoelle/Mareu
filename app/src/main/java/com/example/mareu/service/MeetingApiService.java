package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.List;


/**
 * Meeting API client
 */
public interface MeetingApiService {

    /**
     * Get all my meetings
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Deletes a meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a meeting
     * @param meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Get all meetings occurring on a specific day
     *
     * @param meetingDate the specific day timeStamp in ms
     * @return a list of meetings
     */
    List<Meeting> getMeetingsForGivenDate(String meetingDate);

    /**
     * Get all meetings taking place in given meeting room
     *
     * @param meetingRoom the given meeting room id
     * @return a list of meetings
     */
    List<Meeting> getMeetingsForGivenMeetingRoom(String meetingRoom);


}
