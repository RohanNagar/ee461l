package com.utaustin.freely;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.utaustin.freely.activities.CreateMeetingActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class CreateMeetingActivityTest {

    @Rule
    public ActivityTestRule<CreateMeetingActivity> mActivityRule
            = new ActivityTestRule<>(CreateMeetingActivity.class);

    @Test
    public void testContactsVisible() {
        onView(withId(R.id.activity_create_meeting_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickNextButton() {
        onView(withId(R.id.menu_create_group_next)).perform(click());

        onView(withId(R.id.activity_create_meeting_choose_time_name)).check(matches(isDisplayed()));
    }

}
