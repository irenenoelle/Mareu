package com.example.mareu;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.di.DI;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.ui.meeting.MainActivity;
import com.example.mareu.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Test class for list of meetings
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class MeetingListTest {

    private int currentMeetingsSize = -1;
    private static final int ITEMS_COUNT = 10;
    private final String SELECTED_ROOM = "Newton";
    private final int mPosition = 0;

    private final Date now = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    String formattedDate = formatter.format(now);

    private MainActivity mActivity;
    private MeetingApiService mApiService;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mainActivityTestRule.getActivity();
        assertThat(mActivity, notNullValue());
        mApiService = DI.getMeetingApiService();
        currentMeetingsSize = mApiService.getMeetings().size();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void checkIfRecyclerViewIsNotEmpty() {
        onView(allOf(withId(R.id.list_meeting),isDisplayed()))
                .check(matches(hasMinimumChildCount(currentMeetingsSize)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void checkIfDeleteMeetingIsWorking()
    {
        onView(allOf(withId(R.id.list_meeting), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));
        onView(allOf(withId(R.id.list_meeting), isDisplayed()))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        onView(allOf(withId(R.id.list_meeting), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * We ensure that the meetings displayed is for the selected date
     */
    @Test
    public void checkIfFilterByDateIsWorking() {
        onView(withContentDescription(R.string.menu)).perform(click());
        onView(withText(R.string.byDate)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.list_meeting)).check(withItemCount(0));
        checkOnlyTodayMeetings();
    }

    /**
     * We ensure that the meetings displayed is for the selected room
     */
    @Test
    public void checkIfFilterByRoomIsWorking() {
        onView(withContentDescription(R.string.menu)).perform(click());
        onView(withText(R.string.byMeetingRoom)).perform(click());
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(SELECTED_ROOM))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString(SELECTED_ROOM))));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.list_meeting)).check(withItemCount(3));
    }

    /**
     * We ensure that the list of meetings is reset
     */
    @Test
    public void checkIfReinitializeFilterIsWorking() {
        onView(withContentDescription(R.string.menu)).perform(click());
        onView(withText(R.string.byMeetingRoom)).perform(click());
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(SELECTED_ROOM))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withContentDescription(R.string.menu)).perform(click());
        onView(withText(R.string.resetList)).perform(click());
        onView(withId(R.id.list_meeting)).check(withItemCount(currentMeetingsSize));
    }

    private void checkOnlyTodayMeetings() {
        boolean noTodayMeetings = false;
        try {
            onView(withId(R.id.list_meeting)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(formattedDate)))); //must throw PerformException

        } catch (PerformException expected) {
            noTodayMeetings = true;
        }
        assertTrue(noTodayMeetings);
    }
}
