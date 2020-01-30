package in.nitjsr.cognitio.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import in.nitjsr.cognitio.Fragments.EventsFragment;
import in.nitjsr.cognitio.Fragments.GalleryFragment;
import in.nitjsr.cognitio.Fragments.HomeFragment;
import in.nitjsr.cognitio.Fragments.ProfileFragment;

public class MainActivityAdapter extends FragmentStatePagerAdapter {

    public MainActivityAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new EventsFragment();
            case 2:
                return new GalleryFragment();
            case 3:
                return new ProfileFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
