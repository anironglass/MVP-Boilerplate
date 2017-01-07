## ReactiveX specific rules ##

# RxAndroid will soon ship with rules so this may not be needed in the future
# https://github.com/ReactiveX/RxAndroid/issues/219
-dontwarn sun.misc.**
-dontwarn retrofit2.adapter.rxjava.CompletableHelper$**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}