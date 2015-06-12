package me.jefferey.imgur.ui.account;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jefferey.imgur.R;

/**
 * Created by jetpeter on 6/11/15.
 */
public class MyAccountFragment extends Fragment {

    public static MyAccountFragment newInstance() {
        return new MyAccountFragment();
    }

    public MyAccountFragment() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        return inflater.inflate(R.layout.fragment_my_account, parent, false);
    }

    public String getTitle() {
        return "My Account";
    }
}
