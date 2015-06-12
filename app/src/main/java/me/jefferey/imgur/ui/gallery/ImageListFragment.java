package me.jefferey.imgur.ui.gallery;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.jefferey.imgur.R;
import me.jefferey.imgur.api.ImgurService;
import me.jefferey.imgur.api.managers.GalleryManager;
import me.jefferey.imgur.models.Image;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observer;

public class ImageListFragment extends Fragment {

    public static ImageListFragment newInstance() {
        return new ImageListFragment();
    }

    @InjectView(R.id.ImageListFragment_recycler_view)
    RecyclerView mImageRecyclerView;


    private GalleryManager mGalleryManager;
    private ImageListAdapter mImageListAdapter;

    public ImageListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGalleryManager = new GalleryManager(getImgurService());
        mGalleryManager.getObserver().subscribe(mGalleryObserver);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        mImageListAdapter = new ImageListAdapter(inflater, mGalleryManager.getCurrentImages());
        mGalleryManager.fetchImages();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    public ImgurService getImgurService() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", "Client-ID 2427d8c3e656499");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.imgur.com")
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(new Gson()))
                .build();
        return restAdapter.create(ImgurService.class);
    }
}
