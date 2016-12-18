package ua.anironglass.template;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;
import ua.anironglass.template.common.TestDataFactory;
import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.ui.activities.main.MainActivity;
import ua.anironglass.template.utils.TestComponentRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private final TestComponentRule mComponentRule =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());

    private final ActivityTestRule<MainActivity> mMainActivityTestRule =
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

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public final TestRule chain = RuleChain.outerRule(mComponentRule).around(mMainActivityTestRule);


    @Test
    public void shouldShowsListOfPhotos() {
        List<Photo> testDataPhotos = TestDataFactory.getRandomPhotos(20);
        when(mComponentRule.getMockDataManager().getPhotos())
                .thenReturn(Observable.just(testDataPhotos));

        mMainActivityTestRule.launchActivity(null);

        int position = 0;
        for (Photo photo : testDataPhotos) {
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.scrollToPosition(position));
            onView(withText(photo.getText()))
                    .check(matches(isDisplayed()));
            position++;
        }
    }

}