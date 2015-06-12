package me.jefferey.imgur.ui.gallery;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.List;

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

    private GalleryManager mGalleryManager;

    public ImageListFragment() { }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGalleryManager = new GalleryManager(getImgurService());
        mGalleryManager.getObserver().subscribe(mGalleryObserver);
        mGalleryManager.fetchImages();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        return inflater.inflate(R.layout.fragment_image_list, parent, false);
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
            List<Image> images = mGalleryManager.getCurrentImages();
            int length = images.size();
            Log.v("Images", "Fetched " + length + " images");

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
