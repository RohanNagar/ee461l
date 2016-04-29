package com.utaustin.freely;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.utaustin.freely.activities.CreateMeetingChooseTimeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class CreateMeetingChooseTimeActivityTest {

    @Rule
    public ActivityTestRule<CreateMeetingChooseTimeActivity> mActivityRule
            = new ActivityTestRule<>(CreateMeetingChooseTimeActivity.class);

    @Test
    public void testElementsDisplayed() {
        onView(withId(R.id.activity_create_meeting_choose_time_name)).check(matches(isDisplayed()));
        onView(withId(R.id.create_meeting_choose_activity_open_begin_date_picker)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickDatePickerDialog() {
        onView(withId(R.id.create_meeting_choose_activity_open_begin_date_picker)).perform(click());

        // Make sure it shows up
        onView(withId(android.R.id.button1)).check(matches(isDisplayed()));

        // Dismiss it
        onView(withId(android.R.id.button1)).perform(click());

        // Make sure it's gone
        onView(withId(android.R.id.button1)).check(doesNotExist());
    }
}
