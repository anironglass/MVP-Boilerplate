package ua.anironglass.template.test.common;

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

    private static final int TEST_ALBUM_ID = 1;
    private static final int TEST_FIRST_ID = 1;
    private static final AtomicInteger NEXT_ID = new AtomicInteger(TEST_FIRST_ID);
    private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur " +
            "adipiscing elit, sed do eiusmod tempor incididunt";
    private static final String TEST_URL = "http://placehold.it/600/";
    private static final String TEST_THUMBNAIL_URL = "http://placehold.it/150/";


    public static List<Photo> getRandomPhotos(int number) {
        List<Photo> photos = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            photos.add(getRandomPhoto());
        }
        return photos;
    }

    private static Photo getRandomPhoto() {
        Random random = new Random();
        int randomColor = random.nextInt(0xFFFFFF + 1);
        String randomColorCode = String.format(Locale.getDefault(), "#%06x", randomColor);
        return Photo.builder()
                .setAlbumId(TEST_ALBUM_ID)
                .setId(NEXT_ID.getAndIncrement())
                .setTitle(LOREM_IPSUM)
                .setUrl(TEST_URL + randomColorCode)
                .setThumbnailUrl(TEST_THUMBNAIL_URL + randomColorCode)
                .build();
    }

}