package me.jefferey.imgur.ui.gallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.jefferey.imgur.R;
import me.jefferey.imgur.models.Image;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    private final LayoutInflater mInflater;
    private final List<Image> mImageList;
    private final CacheWarmer mCacheWarmer;

    public ImageListAdapter(@NonNull LayoutInflater inflater, @NonNull List<Image> imageList) {
        mInflater = inflater;
        mImageList = imageList;
        Picasso picasso = Picasso.with(inflater.getContext());
        mCacheWarmer = new CacheWarmer(picasso);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = mInflater.inflate(R.layout.adapter_row_image_list, parent, false);
        return new ImageViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Image image = mImageList.get(position);
        holder.messageView.setText(image.getTitle());
        String pointsStr = holder.scoreView.getResources().getString(R.string.ImageList_points, image.getScore());
        holder.scoreView.setText(pointsStr);
        Picasso.with(holder.imageView.getContext())
                .load(image.getThumbnailSize(Image.LARGE_THUMBNAIL))
                .into(holder.imageView);
        mCacheWarmer.setCurrentPosition(position);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    protected static class ImageViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.ImageList_description) TextView messageView;
        @InjectView(R.id.ImageList_image_view) ImageView imageView;
        @InjectView(R.id.ImageList_score) TextView scoreView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    /**
     * Updated with the current position every time a row is binded this class decides if it should
     * fetch an image to warm up the cache.
     */
    private class CacheWarmer {

        final Picasso picasso;
        int lastPosition = 0;

        public CacheWarmer(Picasso picasso) {
            this.picasso = picasso;
        }

        public void setCurrentPosition(int currentPosition) {
            if (currentPosition > lastPosition) {
                fetchDown(currentPosition);
            } else {
                fetchUp(currentPosition);
            }
            lastPosition = currentPosition;
        }

        private void fetchUp(int currentPosition) {
            int nextFetchPosition = currentPosition - 1;
            if (nextFetchPosition >= 0 && getItemCount() > 0) {
                Image image = mImageList.get(nextFetchPosition);
                picasso.load(image.getThumbnailSize(Image.LARGE_THUMBNAIL)).fetch();
            }
        }

        private void fetchDown(int currentPosition) {
            int nextFetchPosition = currentPosition + 1;
            if (nextFetchPosition < getItemCount() && getItemCount() > 0) {
                Image image = mImageList.get(nextFetchPosition);
                picasso.load(image.getThumbnailSize(Image.LARGE_THUMBNAIL)).fetch();
            }
        }
    }
}
