package ua.anironglass.template.common;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import ua.anironglass.template.data.model.Photo;

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
public class TestDataFactory {

    private static final int DEFAULT_QUANTITY = 50;
    private static final int TEST_ALBUM_ID = 1;
    private static final int TEST_FIRST_ID = 1;
    private static final AtomicInteger NEXT_ID = new AtomicInteger(TEST_FIRST_ID);
    private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur " +
            "adipiscing elit, sed do eiusmod tempor incididunt";
    private static final String TEST_URL = "http://placehold.it/600/";
    private static final String TEST_THUMBNAIL_URL = "http://placehold.it/150/";


    public static List<Photo> getRandomPhotos() {
        return getRandomPhotos(DEFAULT_QUANTITY);
    }

    public static List<Photo> getRandomPhotos(int quantity) {
        List<Photo> photos = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            photos.add(getRandomPhoto());
        }
        return photos;
    }

    public static Photo getRandomPhoto(@NonNull String title,
                                       @IntRange(from = 1, to = 100) int albumId) {
        Random random = new Random();
        int randomColor = random.nextInt(0xFFFFFF + 1);
        String randomColorCode = String.format(Locale.getDefault(), "#%06x", randomColor);
        return Photo.builder()
                .setAlbumId(albumId)
                .setId(NEXT_ID.getAndIncrement())
                .setTitle(title)
                .setUrl(TEST_URL + randomColorCode)
                .setThumbnailUrl(TEST_THUMBNAIL_URL + randomColorCode)
                .build();
    }

    public static Photo getRandomPhoto() {
        return getRandomPhoto(LOREM_IPSUM, TEST_ALBUM_ID);
    }

}