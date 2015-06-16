package me.jefferey.imgur.api;


import me.jefferey.imgur.models.Gallery;
import me.jefferey.imgur.models.GalleryItem;
import me.jefferey.imgur.models.Image;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by jetpeter on 6/12/15.
 *
 * Api Definitions for Imgur
 */
public interface ImgurService {

    @GET("/3/gallery/{section}/{sort}/{page}.json")
    Observable<Gallery> getGallery(@Path("section") String section,
                                   @Path("sort") String sort,
                                   @Path("page") int page,
                                   @Query("showViral") boolean showViral);

    @GET("gallery/image/{imageId}.json")
    Observable<GalleryItem> getGalleryImage(@Path("imageId") String imageId);

    @GET("gallery/album/{albumId}.json")
    Observable<GalleryItem> getGalleryAlbum(@Path("albumId") String albumId);
}
