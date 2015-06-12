package me.jefferey.imgur.api.managers;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import me.jefferey.imgur.api.ImgurService;
import me.jefferey.imgur.models.Gallery;
import me.jefferey.imgur.models.Image;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Class that manages and caches the list of images in a specific gallery
 */
public class GalleryManager {

    private final ImgurService mImgurService;
    private final List<Image> mCurrentGalleryList = new ArrayList<>();
    private final PublishSubject<GalleryChanged> onListChanged = PublishSubject.create();

    public GalleryManager(@NonNull ImgurService imgurService) {
        mImgurService = imgurService;
    }

    public void addImages(@NonNull List<Image> images) {
        int startPos = mCurrentGalleryList.size();
        mCurrentGalleryList.addAll(images);
        onListChanged.onNext(new GalleryChanged(startPos, images.size()));
    }

    public void replaceImages(@NonNull List<Image> images) {
        mCurrentGalleryList.clear();
        mCurrentGalleryList.addAll(images);
        mCurrentGalleryList.addAll(images);
        onListChanged.onNext(new GalleryChanged(0, images.size()));
    }

    public void clearImages() {
        mCurrentGalleryList.clear();
        onListChanged.onNext(new GalleryChanged(0, 0));
    }

    public List<Image> getCurrentImages() {
        return mCurrentGalleryList;
    }

    public void fetchImages() {
        Observable<Gallery> galleryObserver = mImgurService.getGallery();
        galleryObserver.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mGalleryRequestObserver);
    }

    public Observable<GalleryChanged> getObserver() {
        return onListChanged;
    }

    private final Observer<Gallery> mGalleryRequestObserver = new Observer<Gallery>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Gallery gallery) {
            addImages(gallery.data);
        }
    };

    public static class GalleryChanged {
        public final int numAdded;
        public final int startPosition;

        public GalleryChanged(int startPosition, int numAdded) {
            this.numAdded = numAdded;
            this.startPosition = startPosition;
        }
    }
}
