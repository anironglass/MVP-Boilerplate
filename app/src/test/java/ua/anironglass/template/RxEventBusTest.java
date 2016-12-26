package ua.anironglass.template;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import rx.observers.TestSubscriber;
import ua.anironglass.template.utils.RxEventBus;
import ua.anironglass.template.utils.RxSchedulersRule;

@RunWith(MockitoJUnitRunner.class)
public final class RxEventBusTest {

    private RxEventBus mEventBus;

    @Rule public final RxSchedulersRule mRxSchedulersRule = new RxSchedulersRule();

    @Before
    public void initialize() {
        mEventBus = new RxEventBus();
    }

    @Test
    public void shouldPostObjects() {
        // Initialize: create wide event bus
        TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        mEventBus.observable()
                .subscribe(testSubscriber);

        // Run: emit 2 objects
        Object firstEvent = new Object();
        mEventBus.post(firstEvent);

        Object secondEvent = new Object();
        mEventBus.post(secondEvent);

        // Check: should receive 2 objects in order
        testSubscriber.assertValues(firstEvent, secondEvent);

        // Finalize: close subscription
        testSubscriber.unsubscribe();
    }

    @Test
    public void shouldPostFilteredObjects() {
        // Initialize: create filtered event bus
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        mEventBus.filteredObservable(String.class)
                .subscribe(testSubscriber);

        // Run: emit string and integer objects
        String stringEvent = "Event";
        mEventBus.post(stringEvent);

        Integer intEvent = 20;
        mEventBus.post(intEvent);

        // Check: should receive only 1 string object
        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(stringEvent);

        // Finalize: close subscription
        testSubscriber.unsubscribe();
    }
}