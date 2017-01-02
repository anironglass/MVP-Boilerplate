package ua.anironglass.boilerplate.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.support.v7.widget.RecyclerView.LayoutManager;


public final class RecyclerViewHelper {

    public static int getItemCount(@Nullable RecyclerView recyclerView) {
        if (null == recyclerView) {
            return 0;
        }
        final LayoutManager manager = recyclerView.getLayoutManager();
        return null == manager
                ? 0
                : manager.getItemCount();
    }

    public static int findFirstVisibleItemPosition(@Nullable RecyclerView recyclerView) {
        if (null == recyclerView) {
            return RecyclerView.NO_POSITION;
        }
        final LayoutManager manager = recyclerView.getLayoutManager();
        if (null == manager) {
            return RecyclerView.NO_POSITION;
        }
        final View child = findChild(manager, 0, manager.getChildCount(), false, true);
        return null == child
                ? RecyclerView.NO_POSITION
                : recyclerView.getChildAdapterPosition(child);
    }

    public static int findFirstCompletelyVisibleItemPosition(@Nullable RecyclerView recyclerView) {
        if (null == recyclerView) {
            return RecyclerView.NO_POSITION;
        }
        final LayoutManager manager = recyclerView.getLayoutManager();
        if (null == manager) {
            return RecyclerView.NO_POSITION;
        }
        final View child = findChild(manager, 0, manager.getChildCount(), true, false);
        return null == child
                ? RecyclerView.NO_POSITION
                : recyclerView.getChildAdapterPosition(child);
    }

    public int findLastVisibleItemPosition(@Nullable RecyclerView recyclerView) {
        if (null == recyclerView) {
            return RecyclerView.NO_POSITION;
        }
        final LayoutManager manager = recyclerView.getLayoutManager();
        if (null == manager) {
            return RecyclerView.NO_POSITION;
        }
        final View child = findChild(manager, manager.getChildCount() - 1, -1, false, true);
        return null == child
                ? RecyclerView.NO_POSITION
                : recyclerView.getChildAdapterPosition(child);
    }

    public int findLastCompletelyVisibleItemPosition(@Nullable RecyclerView recyclerView) {
        if (null == recyclerView) {
            return RecyclerView.NO_POSITION;
        }
        final LayoutManager manager = recyclerView.getLayoutManager();
        if (null == manager) {
            return RecyclerView.NO_POSITION;
        }
        final View child = findChild(manager, manager.getChildCount() - 1, -1, true, false);
        return null == child
                ? RecyclerView.NO_POSITION
                : recyclerView.getChildAdapterPosition(child);
    }

    @Nullable
    private static View findChild(@NonNull LayoutManager manager,
                                  int fromIndex, int toIndex,
                                  boolean completelyVisible, boolean acceptPartiallyVisible) {
        OrientationHelper helper;
        if (manager.canScrollVertically()) {
            helper = OrientationHelper.createVerticalHelper(manager);
        } else {
            helper = OrientationHelper.createHorizontalHelper(manager);
        }

        final int start = helper.getStartAfterPadding();
        final int end = helper.getEndAfterPadding();
        final int next = toIndex > fromIndex ? 1 : -1;
        View partiallyVisible = null;
        for (int i = fromIndex; i != toIndex; i += next) {
            final View child = manager.getChildAt(i);
            final int childStart = helper.getDecoratedStart(child);
            final int childEnd = helper.getDecoratedEnd(child);
            if (childStart < end && childEnd > start) {
                if (completelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return child;
                    } else if (acceptPartiallyVisible && partiallyVisible == null) {
                        partiallyVisible = child;
                    }
                } else {
                    return child;
                }
            }
        }
        return partiallyVisible;
    }
}