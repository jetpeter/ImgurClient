package me.jefferey.imgur.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import me.jefferey.imgur.R;
import me.jefferey.imgur.ui.account.MyAccountFragment;
import me.jefferey.imgur.ui.gallery.ImageListFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.MainActivity_toolbar);
        setSupportActionBar(toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.MainActivity_view_pager);
        viewPager.setAdapter(new ImgurFragmentPagerAdapter(getFragmentManager(), ImageListFragment.newInstance(), MyAccountFragment.newInstance()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.MainActivity_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
