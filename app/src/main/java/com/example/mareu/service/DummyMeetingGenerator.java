package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {
    public static final List<String> DUMMY_MEETING_PARTICIPANTS = Arrays.asList("ndele23@gmail.com", "noelledele@gmail.com", "jmKepato@gmail.com", "idadzeukang@gmail.com", "nellykenne@yahoo.fr");
    public static List<String> MEETINGS_ROOMS = Arrays.asList("Curie", "Newton", "Einstein", "Tesla", "Edison", "Archimède", "Aristote", "Copernic", "Pasteur", "Descartes");

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(1, "Planning", "20/10/2021", "09:00",
                    "Newton",  DUMMY_MEETING_PARTICIPANTS, "10:00"),
            new Meeting(2, "Entretien", "20/09/2021", "08:00",
                    "Curie",  DUMMY_MEETING_PARTICIPANTS, "08:30"),
            new Meeting(3, "Prejob briefing", "20/10/2021", "15:00",
                    "Newton",  DUMMY_MEETING_PARTICIPANTS, "16:00"),
            new Meeting(4, "Budget", "2/10/2021", "16:00",
                    "Pasteur",  DUMMY_MEETING_PARTICIPANTS, "17:00"),
            new Meeting(5, "Brainstorming", "16/10/2021", "14:30",
                    "Tesla",  DUMMY_MEETING_PARTICIPANTS, "15:30"),
            new Meeting(6, "Mêlée", "5/10/2021", "10:00",
                    "Einstein",  DUMMY_MEETING_PARTICIPANTS, "10:45"),
            new Meeting(7, "Planning", "5/11/2021", "11:15",
                    "Einstein",  DUMMY_MEETING_PARTICIPANTS, "12:15"),
            new Meeting(8, "Expression du besoin", "22/10/2021", "10:00",
                    "Descartes",  DUMMY_MEETING_PARTICIPANTS, "11:30"),
            new Meeting(9, "Revue Backlog", "20/10/2021", "10:00",
                    "Curie",  DUMMY_MEETING_PARTICIPANTS, "11:00"),
            new Meeting(10, "Avancement", "5/10/2021", "13:30",
                    "Newton",  DUMMY_MEETING_PARTICIPANTS, "14:00")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
