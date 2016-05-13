package me.mustakimov.scetovod.MainScreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mikhail on 06/05/16.
 */
public class MainPageFragmentAdapter extends FragmentPagerAdapter {

    public MainPageFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PurchasesFragment();
            case 1:
                return new ChartsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
