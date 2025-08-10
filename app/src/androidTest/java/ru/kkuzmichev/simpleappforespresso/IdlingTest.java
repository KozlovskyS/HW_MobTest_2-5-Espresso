package ru.kkuzmichev.simpleappforespresso;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class IdlingTest {

    final ViewInteraction menuButton = onView(isAssignableFrom(AppCompatImageButton.class));
    final ViewInteraction gallery = onView(withId(R.id.nav_gallery));
    final ViewInteraction recyclerView = onView(withId(R.id.recycle_view));
    String number = "7";
    int listSize = 10;
    final ViewInteraction itemNumber = onView(allOf(withId(R.id.item_number), withText(number)));

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void registerIdlingResources() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource);
    }

    @After
    public void unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource);
    }

    @Test
    public void checkElementTest() {
        menuButton.check(matches(isDisplayed()));
        menuButton.perform(click());
        gallery.check(matches(isDisplayed()));
        gallery.perform(click());
        itemNumber.check(matches(isDisplayed()));

        itemNumber.check(matches(withText(number)));
    }

    @Test
    public void checkElementFaultTest() {
        menuButton.check(matches(isDisplayed()));
        menuButton.perform(click());
        gallery.check(matches(isDisplayed()));
        gallery.perform(click());
        itemNumber.check(matches(isDisplayed()));

        itemNumber.check(matches(withText("6")));
    }

    @Test
    public void openGalleryTest() {
        menuButton.check(matches(isDisplayed()));
        menuButton.perform(click());
        gallery.perform(click());

        recyclerView.check(matches(CustomViewMatcher.recyclerViewSizeMatcher(listSize)));
    }

    @Test
    public void openGalleryFaultTest() {
        menuButton.check(matches(isDisplayed()));
        menuButton.perform(click());
        gallery.perform(click());

        recyclerView.check(matches(CustomViewMatcher.recyclerViewSizeMatcher(9)));
    }

    @Test
    public void checkIsRecyclerTest() {
        menuButton.check(matches(isDisplayed()));
        menuButton.perform(click());
        gallery.perform(click());

        recyclerView.check(CustomViewAssertions.isRecyclerView());
    }
}
