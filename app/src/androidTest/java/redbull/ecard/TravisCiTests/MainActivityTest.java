package redbull.ecard.TravisCiTests;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.filters.SmallTest;

import org.junit.Rule;
import org.junit.Test;

import redbull.ecard.MainActivity;
import redbull.ecard.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> intentsTestRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void fragmentTest(){
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation_notifications)).perform(click());
        onView(withId(R.id.navigation_dashboard)).perform(click());
    }
}
