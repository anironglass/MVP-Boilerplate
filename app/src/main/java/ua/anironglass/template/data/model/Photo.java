package ua.anironglass.template.data.model;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

@SuppressWarnings("unused, WeakerAccess")
public class Photo {

    @SerializedName("albumId") @Expose private int mAlbumId;
    @SerializedName("id") @Expose private int mId;
    @SerializedName("title") @Expose private String mTitle;
    @SerializedName("url") @Expose private String mUrl;
    @SerializedName("thumbnailUrl") @Expose private String mThumbnailUrl;

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    public int getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(int albumId) {
        mAlbumId = albumId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        mThumbnailUrl = thumbnailUrl;
    }

    public String getText() {
        return String.format(
                Locale.getDefault(),
                "[%d] %s",
                getId(),
                getTitle());
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) {
            return true;
        }
        if (null == another || getClass() != another.getClass()) {
            return false;
        }

        Photo anotherPhoto = (Photo) another;

        if (getAlbumId() != anotherPhoto.getAlbumId()) {
            return false;
        }
        if (getId() != anotherPhoto.getId()) {
            return false;
        }
        if (null == getTitle()
                ? anotherPhoto.getTitle() != null
                : !getTitle().equals(anotherPhoto.getTitle())) {
            return false;
        }
        return null == getUrl()
                ? anotherPhoto.getUrl() == null
                : getUrl().equals(anotherPhoto.getUrl());

    }

    @Override
    public int hashCode() {
        int result = getAlbumId();
        result = 31 * result + getId();
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        return result;
    }

    public static class Builder {

        private int mAlbumId;
        private int mId;
        private String mTitle;
        private String mUrl;
        private String mThumbnailUrl;

        public Builder setAlbumId(@IntRange(from = 1, to = 100) int albumId) {
            mAlbumId = albumId;
            return this;
        }

        public Builder setId(@IntRange(from = 1, to = 5000) int id) {
            mId = id;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setUrl(String url) {
            mUrl = url;
            return this;
        }

        public Builder setThumbnailUrl(String thumbnailUrl) {
            mThumbnailUrl = thumbnailUrl;
            return  this;
        }

        public Photo build() {
            Photo photo = new Photo();
            photo.setAlbumId(mAlbumId);
            photo.setId(mId);
            photo.setTitle(mTitle);
            photo.setUrl(mUrl);
            photo.setThumbnailUrl(mThumbnailUrl);
            return photo;
        }
    }

}