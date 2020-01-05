package com.example.revolut.main;


import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.revolut.MainActivity;
import com.example.revolut.R;
import com.example.revolut.service.CurrencyApiService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MainFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init(){
        activityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void testTitleOnMainScreen(){
        onView(withId(R.id.rate)).check(matches(isDisplayed()));
        onView(withId(R.id.rate)).check(matches(withText("Rates")));
    }

    @Test
    public void testRecyClerviewVisible(){
        onView(withId(R.id.rv_curr)).check(matches(isDisplayed()));
    }

    @Test
    public void testRecyclerViewItemCount(){
        onView(withId(R.id.rv_curr)).check(new RecyclerViewItemCountAssertion(33));
    }

    @Test
    public void testRecyclerViewScroll(){
        RecyclerView recyclerView = activityActivityTestRule.getActivity().findViewById(R.id.rv_curr);
        int itemCount = recyclerView.getAdapter().getItemCount();
    }
}
