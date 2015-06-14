package me.jefferey.imgur.api;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.jefferey.imgur.api.managers.GalleryManager;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module
public class ImgurModule {

    private static final String API_URL = "https://api.imgur.com";

    @Provides @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides @Singleton
    RequestInterceptor provideAuthInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", "Client-ID 2427d8c3e656499");
            }
        };
    }

    @Provides @Singleton
    RestAdapter provideRestAdapter(Gson gson, RequestInterceptor authInterceptor) {
        return new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setRequestInterceptor(authInterceptor)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @Provides @Singleton
    ImgurService provideImgurService(RestAdapter restAdapter) {
        return restAdapter.create(ImgurService.class);
    }

    @Provides @Singleton
    GalleryManager provideGalleryManager(ImgurService imgurService) {
        return new GalleryManager(imgurService);
    }

}
