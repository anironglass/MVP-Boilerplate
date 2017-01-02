package ua.anironglass.boilerplate.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.anironglass.boilerplate.R;
import ua.anironglass.boilerplate.data.model.Photo;
import ua.anironglass.boilerplate.injection.ActivityContext;
import ua.anironglass.boilerplate.injection.PerActivity;

@PerActivity
class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder>  {

    private List<Photo> mPhotos = new ArrayList<>();
    private RequestManager mGlide;

    @Inject
    PhotosAdapter(@ActivityContext Context context) {
        setHasStableIds(true);
        mGlide = Glide.with(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mPhotos.get(position));
    }

    @Override
    public int getItemCount() {
        return mPhotos != null ? mPhotos.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return mPhotos != null
                ? mPhotos.get(position).getId()
                : -1;
    }

    void setPhotos(List<Photo> photos) {
        mPhotos = photos;
        notifyDataSetChanged();
    }


    final class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.photo_thumbnail) ImageView imageThumbnail;
        @BindView(R.id.photo_title) TextView textTitle;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull Photo photo) {
            textTitle.setText(photo.getText());
            mGlide.load(photo.getThumbnailUrl())
                    .into(imageThumbnail);
        }
    }

}