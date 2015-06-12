package me.jefferey.imgur.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import me.jefferey.imgur.ui.account.MyAccountFragment;
import me.jefferey.imgur.ui.gallery.ImageListFragment;

/**
 * Created by jetpeter on 6/11/15.
 */
public class ImgurFragmentPagerAdapter extends FragmentPagerAdapter {

    public static final int FRAGMENT_COUNT = 2;

    private final ImageListFragment mImageListFragment;
    private final MyAccountFragment mMyAccountFragment;

    public ImgurFragmentPagerAdapter(FragmentManager fragmentManager,
                                     ImageListFragment imageListFragment,
                                     MyAccountFragment myAccountFragment) {
        super(fragmentManager);
        mImageListFragment = imageListFragment;
        mMyAccountFragment = myAccountFragment;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mImageListFragment;
            case 1:
                return mMyAccountFragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mImageListFragment.getTitle();
            case 1:
                return mMyAccountFragment.getTitle();
            default:
                return "";
        }
    }
}
