package me.jefferey.imgur.api;


import me.jefferey.imgur.models.Gallery;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by jetpeter on 6/12/15.
 *
 * Api Definitions for Imgur
 */
public interface ImgurService {

    @GET("/3/gallery.json")
    Observable<Gallery> getGallery();

}
