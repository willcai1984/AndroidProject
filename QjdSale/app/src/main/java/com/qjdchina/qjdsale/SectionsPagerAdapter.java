package com.qjdchina.qjdsale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // return MemberInfoActivity.PlaceholderFragment.newInstance(position + msn);
        switch (position) {
            case 0:
                return new MemberPictureFragment();
            case 1:
                return new MemberMarkFragment();
            case 2:
                return new MemberPicturePlusFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show skype total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "客户资料";
            case 1:
                return "客户评分";
            case 2:
                return "客户资料plus";
        }
        return null;
    }
}
