package ua.anironglass.template.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import ua.anironglass.template.data.model.Photo;


final class Database {

    private Database() {
        throw new AssertionError("No instances!");
    }

    abstract static class PhotosTable {

        static final String TABLE_NAME = "anironglass_photos";

        static final String COLUMN_ID = "id";
        static final String COLUMN_ALBUM_ID = "album_id";
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_URL = "url";
        static final String COLUMN_THUMBNAIL_URL = "thumbnailUrl";

        static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_ALBUM_ID + " INTEGER NOT NULL, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_URL + " TEXT NOT NULL, " +
                        COLUMN_THUMBNAIL_URL + " TEXT NOT NULL" +
                        "); ";

        static ContentValues toContentValues(Photo photo) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, photo.getId());
            values.put(COLUMN_ALBUM_ID, photo.getAlbumId());
            values.put(COLUMN_TITLE, photo.getTitle());
            values.put(COLUMN_URL, photo.getUrl());
            values.put(COLUMN_THUMBNAIL_URL, photo.getThumbnailUrl());
            return values;
        }

        static Photo parseCursor(Cursor cursor) {
            return Photo.builder()
                    .setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                    .setAlbumId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ALBUM_ID)))
                    .setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)))
                    .setUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL)))
                    .setThumbnailUrl(
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THUMBNAIL_URL)))
                    .build();
        }
    }
}