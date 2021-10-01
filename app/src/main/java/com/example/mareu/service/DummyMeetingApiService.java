package com.example.mareu.service;

import static com.example.mareu.service.DummyMeetingGenerator.MEETINGS_ROOMS;

import com.example.mareu.model.Meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    /**
     * Get all the meetings
     *
     * @return list of meetings
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * Delete the given meeting of the list
     *
     * @param meeting the given meeting
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    /**
     * Add the given meeting to the List of meetings
     *
     * @param meeting the given meeting
     */
    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    /**
     * Get all meetings occurring on a specific day
     *
     * @param meetingDate the specific day in string
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
     * @param meetingRoom the given meeting room
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

    /**
     * Get meetings available for a given time interval
     *
     * @param meetingStartTime the startTime of the meeting to create
     * @param meetingEndTime the endTime of the meeting to create
     * @param meetingDate day of the meeting create
     * @return list of meetingRooms
     */

    @Override
    public List<String> getFreeMeetingRooms(String meetingStartTime, String meetingEndTime, String meetingDate) {
        List<String> freeMeetingRooms = new ArrayList<>();
        for (int i = 0; i < MEETINGS_ROOMS.size(); i++) {
            List<Meeting> meetingsInCurrentMeetingRoom = this.getMeetingsForGivenMeetingRoom(MEETINGS_ROOMS.get(i));
            if (meetingsInCurrentMeetingRoom.isEmpty()) {
                freeMeetingRooms.add(MEETINGS_ROOMS.get(i));
            } else {
                boolean loop = true;
                int j = 0;
                while (loop && j < meetingsInCurrentMeetingRoom.size() && meetingDate.equalsIgnoreCase(meetingsInCurrentMeetingRoom.get(j).getDay())) {
                    String endTime = meetingDate + " " + meetingsInCurrentMeetingRoom.get(j).getEndTime();
                    String startTime = meetingDate + " " + meetingsInCurrentMeetingRoom.get(j).getStartTime();
                    String temp = meetingDate + " " + meetingStartTime;
                    String temp2 = meetingDate + " " + meetingEndTime;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date1 = null;
                    Date date2 = null;
                    Date date3 = null;
                    Date date4 = null;
                    try {
                        date1 = sdf.parse(endTime);
                        date2 = sdf.parse(startTime);
                        date3 = sdf.parse(temp);
                        date4 = sdf.parse(temp2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long endTimeMillis = date1.getTime();
                    long startTimeMillis = date2.getTime();
                    long mStartTime = date3.getTime();
                    long mEndTime = date4.getTime();

                    if (mStartTime >= endTimeMillis || mEndTime <= startTimeMillis) {
                        if (j == meetingsInCurrentMeetingRoom.size() - 1) {
                            freeMeetingRooms.add(MEETINGS_ROOMS.get(i));
                        }
                        j++;
                    } else {
                        loop = false;
                    }

                }
            }
        }
        return freeMeetingRooms;
    }

}
