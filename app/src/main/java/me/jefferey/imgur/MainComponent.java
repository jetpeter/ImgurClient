package me.jefferey.imgur;

import javax.inject.Singleton;

import dagger.Component;
import me.jefferey.imgur.api.ImgurModule;
import me.jefferey.imgur.ui.gallery.ImageListFragment;

@Singleton
@Component(modules = ImgurModule.class)
public interface MainComponent {

    void inject(ImageListFragment imageListFragment);

}
