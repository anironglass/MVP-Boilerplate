package ua.anironglass.boilerplate;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;
import ua.anironglass.boilerplate.data.DataManager;
import ua.anironglass.boilerplate.data.model.Photo;
import ua.anironglass.boilerplate.ui.main.MainActivity;
import ua.anironglass.boilerplate.utils.TestDataFactory;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;


@RunWith(AndroidJUnit4.class)
public final class MainActivityTest {

    @Rule public final ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class, false, false) {
                @Override
                protected Intent getActivityIntent() {
                    // Override the default intent so we pass a false flag for syncing so it doesn't
                    // start a sync service in the background that would affect  the behaviour of
                    // this test.
                    return MainActivity.getStartIntent(
                            InstrumentationRegistry.getTargetContext(), false);
                }
            };

    @Test
    public void shouldShowsListOfPhotos() {
        // Initialize: prepare test photos
        List<Photo> testPhotos = TestDataFactory.getRandomPhotos();
        DataManager mockedDataManager = TestApp.get(InstrumentationRegistry.getTargetContext())
                .getComponent()
                .dataManager();
        when(mockedDataManager.getPhotos())
                .thenReturn(Observable.just(testPhotos));

        // Run: launch MainActivity
        mainActivityTestRule.launchActivity(null);

        // Check: should show all photos
        int position = 0;
        for (Photo photo : testPhotos) {
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.scrollToPosition(position));
            onView(withText(photo.getText()))
                    .check(matches(isDisplayed()));
            position++;
        }
    }

}