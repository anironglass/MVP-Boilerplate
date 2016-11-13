package ua.anironglass.template;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

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
        TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        mEventBus.observable().subscribe(testSubscriber);

        Object firstEvent = new Object();
        Object secondEvent = new Object();
        mEventBus.post(firstEvent);
        mEventBus.post(secondEvent);

        testSubscriber.assertValues(firstEvent, secondEvent);
    }

    @Test
    public void shouldPostFilteredObjects() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        mEventBus.filteredObservable(String.class).subscribe(testSubscriber);

        String stringEvent = "Event";
        Integer intEvent = 20;
        mEventBus.post(stringEvent);
        mEventBus.post(intEvent);

        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(stringEvent);
    }
}