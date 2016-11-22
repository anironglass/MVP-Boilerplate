package ua.anironglass.template.data.model;

import android.os.Parcelable;
import android.support.annotation.IntRange;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

@AutoValue
public abstract class Photo implements Parcelable {

    @SerializedName("albumId") public abstract int getAlbumId();
    @SerializedName("id") public abstract int getId();
    @SerializedName("title") public abstract String getTitle();
    @SerializedName("url") public abstract String getUrl();
    @SerializedName("thumbnailUrl") public abstract String getThumbnailUrl();

    public static Builder builder() {
        return new AutoValue_Photo.Builder();
    }

    public static TypeAdapter<Photo> typeAdapter(Gson gson) {
        return new AutoValue_Photo.GsonTypeAdapter(gson);
    }

    public String getText() {
        return String.format(
                Locale.getDefault(),
                "[%d] %s",
                getId(),
                getTitle());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setAlbumId(@IntRange(from = 1, to = 100) int albumId);
        public abstract Builder setId(@IntRange(from = 1, to = 5000) int id);
        public abstract Builder setTitle(String title);
        public abstract Builder setUrl(String url);
        public abstract Builder setThumbnailUrl(String url);
        public abstract Photo build();
    }

}