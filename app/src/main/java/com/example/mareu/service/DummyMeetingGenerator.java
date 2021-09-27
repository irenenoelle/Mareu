package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {
    public static final List<String> DUMMY_MEETING_PARTICIPANTS = Arrays.asList("ndele23@gmail.com", "noelledele@gmail.com", "jmKepato@gmail.com", "idadzeukang@gmail.com", "nellykenne@yahoo.fr");

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(1, "Planning", "20/10/2021", "",
                    "Newton",  DUMMY_MEETING_PARTICIPANTS, "30 minutes"),
            new Meeting(2, "Entretien", "20/09/2021", "",
                    "Curie",  DUMMY_MEETING_PARTICIPANTS, "30 minutes"),
            new Meeting(3, "Prejob briefing", "20/10/2021", "",
                    "Newton",  DUMMY_MEETING_PARTICIPANTS, "30 minutes"),
            new Meeting(4, "Budget", "02/10/2021", "",
                    "Pasteur",  DUMMY_MEETING_PARTICIPANTS, "30 minutes"),
            new Meeting(5, "Brainstorming", "16/10/2021", "",
                    "Tesla",  DUMMY_MEETING_PARTICIPANTS, "30 minutes"),
            new Meeting(6, "Mêlée", "05/10/2021", "",
                    "Einstein",  DUMMY_MEETING_PARTICIPANTS, "30 minutes"),
            new Meeting(7, "Planning", "05/11/2021", "",
                    "Einstein",  DUMMY_MEETING_PARTICIPANTS, "30 minutes"),
            new Meeting(8, "Expression du besoin", "22/10/2021", "",
                    "Descartes",  DUMMY_MEETING_PARTICIPANTS, "30 minutes"),
            new Meeting(9, "Revue Backlog", "20/10/2021", "",
                    "Curie",  DUMMY_MEETING_PARTICIPANTS, "30 minutes"),
            new Meeting(10, "Avancement", "05/10/2021", "",
                    "Newton",  DUMMY_MEETING_PARTICIPANTS, "30 minutes")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
