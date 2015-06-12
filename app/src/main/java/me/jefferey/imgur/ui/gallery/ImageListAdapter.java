package me.jefferey.imgur.ui.gallery;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }
}
