package ua.anironglass.template.data.local;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IntRange;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.utils.LogHelper;


@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDatabase;

    @Inject
    @SuppressWarnings("WeakerAccess")  // Used in global singleton
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        SqlBrite.Builder briteBuilder = new SqlBrite.Builder();
        mDatabase = briteBuilder.build().wrapDatabaseHelper(dbOpenHelper, Schedulers.immediate());
    }

    public BriteDatabase getDatabase() {
        return mDatabase;
    }

    public Observable<Photo> setPhotos(final Collection<Photo> newPhotos) {
        return Observable.create(new SavePhotosObservable(newPhotos))
                .doOnNext(photos -> Timber.d(LogHelper.attachThreadName("Saved local photos")));
    }

    public Observable<List<Photo>> getPhotos(@IntRange(from = 1, to = 100) int albumId) {
        return mDatabase.createQuery(
                Database.PhotosTable.TABLE_NAME,
                "SELECT * FROM " + Database.PhotosTable.TABLE_NAME
                        + " WHERE " + Database.PhotosTable.COLUMN_ALBUM_ID + "=" + albumId)
                .mapToList(Database.PhotosTable::parseCursor)
                .doOnNext(photos -> Timber.d(
                        LogHelper.attachThreadName("Loaded local photos, albumId = %d"),
                        albumId));
    }


    private class SavePhotosObservable implements Observable.OnSubscribe<Photo> {

        private final Collection<Photo> mNewPhotos;

        private SavePhotosObservable(final Collection<Photo> newPhotos) {
            mNewPhotos = newPhotos;
        }

        @Override
        public void call(Subscriber<? super Photo> subscriber) {
            if (subscriber.isUnsubscribed()) return;
            BriteDatabase.Transaction transaction = mDatabase.newTransaction();
            try {
                mDatabase.delete(Database.PhotosTable.TABLE_NAME, null);
                for (Photo photo : mNewPhotos) {
                    long result = mDatabase.insert(Database.PhotosTable.TABLE_NAME,
                            Database.PhotosTable.toContentValues(photo),
                            SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) {
                        subscriber.onNext(photo);
                    }
                }
                transaction.markSuccessful();
                subscriber.onCompleted();
            } finally {
                transaction.end();
            }
        }
    }

}