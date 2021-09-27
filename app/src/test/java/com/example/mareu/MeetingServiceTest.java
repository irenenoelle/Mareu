package com.example.mareu;

import static com.example.mareu.service.DummyMeetingGenerator.DUMMY_MEETING_PARTICIPANTS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.DummyMeetingGenerator;
import com.example.mareu.service.MeetingApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

/**
 * Unit test on Meeting service
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private MeetingApiService service;

    @Before
    public void setup() {service = DI.getNewInstanceApiService();}

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void createMeetingWithSuccess() {
        Meeting meetingToCreate = new Meeting(1, "Planning", "20/10/2021", "",
                "Newton",  DUMMY_MEETING_PARTICIPANTS, "30 minutes");
        service.createMeeting(meetingToCreate);
        assertTrue(service.getMeetings().contains(meetingToCreate));
    }


    @Test
    public void getMeetingsForGivenMeetingRoomWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        String expectedRoom = meetings.get(0).getDay();
        List<Meeting> expectedList = service.getMeetingsForGivenDate(expectedRoom);
        assertEquals(3, expectedList.size());
    }

    @Test
    public void getMeetingsForGivenDateWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        String expectedDate = meetings.get(0).getDay();
        List<Meeting> expectedList = service.getMeetingsForGivenDate(expectedDate);
        assertEquals(3, expectedList.size());
    }
}
