package com.utaustin.freely;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.utaustin.freely.activities.MeetingsActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MeetingsActivityTest {

    @Rule
    public ActivityTestRule<MeetingsActivity> mActivityRule
            = new ActivityTestRule<>(MeetingsActivity.class);

    public void testActionButton() {
        onView(withId(R.id.fab)).perform(click());

        // Make sure that we have gone to the next activity
        onView(withId(R.id.activity_create_meeting_recycler_view)).check(matches(isDisplayed()));
    }

}
