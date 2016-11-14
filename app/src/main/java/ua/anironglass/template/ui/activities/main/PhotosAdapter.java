package ua.anironglass.template.ui.activities.main;

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
import ua.anironglass.template.R;
import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.injection.ActivityContext;


class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder>  {

    private ArrayList<Photo> mPhotos = new ArrayList<>();
    private RequestManager mGlideRequestManager;

    @Inject
    PhotosAdapter(@ActivityContext Context context) {
        setHasStableIds(true);
        mGlideRequestManager = Glide.with(context);
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

    void addPhotos(List<Photo> photos) {
        mPhotos.addAll(photos);
    }


    final class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.photo_thumbnail) ImageView imageThumbnail;
        @BindView(R.id.photo_title) TextView textTitle;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull Photo photo) {
            String title = "[" + photo.getId() + "] " + photo.getTitle();
            textTitle.setText(title);
            mGlideRequestManager.load(photo.getThumbnailUrl())
                    .into(imageThumbnail);
        }
    }

}