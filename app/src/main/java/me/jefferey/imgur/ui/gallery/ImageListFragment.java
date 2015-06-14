package me.jefferey.imgur.ui.gallery;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.jefferey.imgur.ImgurApplication;
import me.jefferey.imgur.R;
import me.jefferey.imgur.api.managers.GalleryManager;
import rx.Observer;
import rx.Subscription;

public class ImageListFragment extends Fragment {

    public static ImageListFragment newInstance() {
        return new ImageListFragment();
    }

    @InjectView(R.id.ImageListFragment_recycler_view)
    RecyclerView mImageRecyclerView;

    @Inject
    GalleryManager mGalleryManager;

    private Subscription mGallerySubscription;
    private ImageListAdapter mImageListAdapter;

    public ImageListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        ImgurApplication application = (ImgurApplication) activity.getApplication();
        application.getMainComponent().inject(this);
        LayoutInflater inflater = LayoutInflater.from(activity);

        mGallerySubscription = mGalleryManager.getObserver().subscribe(mGalleryObserver);
        mImageListAdapter = new ImageListAdapter(inflater, mGalleryManager.getCurrentImages());
        mGalleryManager.fetchImages();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGallerySubscription.unsubscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View content = inflater.inflate(R.layout.fragment_image_list, parent, false);
        ButterKnife.inject(this, content);
        mImageRecyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext()));
        mImageRecyclerView.setAdapter(mImageListAdapter);
        return content;
    }

    public String getTitle() {
        return "Image List";
    }


    public final Observer<GalleryManager.GalleryChanged> mGalleryObserver = new Observer<GalleryManager.GalleryChanged>() {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(GalleryManager.GalleryChanged galleryChanged) {
            mImageListAdapter.notifyItemRangeInserted(galleryChanged.startPosition, galleryChanged.numAdded);
        }
    };
}
