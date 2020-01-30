package in.nitjsr.cognitio.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

import in.nitjsr.cognitio.Activities.Events;
import in.nitjsr.cognitio.Activities.Guest;
import in.nitjsr.cognitio.Activities.Itinerary;
import in.nitjsr.cognitio.Activities.Sponsors;
import in.nitjsr.cognitio.Adapters.PosterAdapter;
import in.nitjsr.cognitio.Adapters.RecyclerViewAdapter;
import in.nitjsr.cognitio.Modals.Modal;
import in.nitjsr.cognitio.R;
import in.nitjsr.cognitio.Utils.Constants;
import me.relex.circleindicator.CircleIndicator;

import static in.nitjsr.cognitio.Utils.Constants.EVENT_FLAG;
import static in.nitjsr.cognitio.Utils.Constants.FIREBASE_REF_SPONSORS;
import static in.nitjsr.cognitio.Utils.Constants.SPONSORS_FLAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private static final int BANNER_DELAY_TIME = 5 * 1000;
    private static final int BANNER_TRANSITION_DELAY = 1200;
    ViewPager viewPager;
    private Runnable runnable;
    private Handler handler;
    private boolean firstScroll = true;
    private TextView seeAll_events;
    private TextView seeAll_sponsors;
    private ImageView itinerary, guest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setPosterImage(view);
        viewPager = view.findViewById(R.id.viewpager_poster);
        seeAll_events = view.findViewById(R.id.tv_events_see_all);
        seeAll_sponsors = view.findViewById(R.id.tv_sponsors_see_all);
        itinerary = view.findViewById(R.id.itinerary);
        guest = view.findViewById(R.id.guest);

        seeAll_events.setOnClickListener(this);
        seeAll_sponsors.setOnClickListener(this);
        itinerary.setOnClickListener(this);
        guest.setOnClickListener(this);

        setEventRv(view);
        prepareSponsorList(view);
        setPosterImage(view);
        return view;
    }

    private void setPosterImage(View view) {
        final ViewPager viewPager = view.findViewById(R.id.viewpager_poster);
        final CircleIndicator indicator = view.findViewById(R.id.indicator_slider);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_REF_POSTER_IMAGE);
        databaseReference.keepSynced(true);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    try {
                        final int img_count = (int) dataSnapshot.getChildrenCount();
                        String[] data = new String[(int) dataSnapshot.getChildrenCount()];
                        int currindex = 0;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            data[currindex] = ds.getValue().toString();
                            currindex++;
                        }
                        viewPager.setAdapter(new PosterAdapter(getActivity(), data));
                        indicator.setViewPager(viewPager);

                        handler = new Handler(Looper.getMainLooper());
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                int currItem = viewPager.getCurrentItem();
                                if (currItem == img_count - 1) {
                                    viewPager.setCurrentItem(0);
                                } else {
                                    viewPager.setCurrentItem(++currItem);
                                }
                            }
                        };
                        handler.postDelayed(runnable, BANNER_DELAY_TIME);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        viewPager.setOnPageChangeListener(this);
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, new CustomScroller(viewPager.getContext(), BANNER_TRANSITION_DELAY));
        } catch (Exception e) {
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (firstScroll) {
            firstScroll = false;
        } else {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            handler.postDelayed(runnable, BANNER_DELAY_TIME);
        }
    }

    public void setEventRv(View v) {
        RecyclerView rv = v.findViewById(R.id.rv_events);
        LinearLayoutManager ll = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(ll);
        rv.setHasFixedSize(true);
        ArrayList<Modal> eventList = prepareEventList();
        rv.setAdapter(new RecyclerViewAdapter(getContext(), eventList, EVENT_FLAG));
    }

    private ArrayList<Modal> prepareEventList() {
        ArrayList<Modal> modals = new ArrayList<>();
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[0], Constants.EVENT_NAMES[0]));//Radiation
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[7], Constants.EVENT_NAMES[7]));//Assemblage
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[9], Constants.EVENT_NAMES[9]));//Shoot at Sight
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[6], Constants.EVENT_NAMES[6]));//Cansys
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[8], Constants.EVENT_NAMES[8]));//Quriosity
        return modals;
    }

    private void prepareSponsorList(View v) {
        final RecyclerView rv = v.findViewById(R.id.rv_sponsors);
        LinearLayoutManager ll = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(ll);
        rv.setHasFixedSize(true);
        final ArrayList<Modal> dataList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(FIREBASE_REF_SPONSORS);
        ref.keepSynced(true);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String image, desc;
                dataList.clear();
                int p = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (p == 5) break;
                    image = ds.child("Image").getValue().toString();
                    desc = ds.child("Desc").getValue().toString();
                    dataList.add(new Modal(image, desc));
                    p++;
                }
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), dataList, SPONSORS_FLAG);
                adapter.notifyDataSetChanged();
                rv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_events_see_all:
                startActivity(new Intent(getContext(), Events.class));
                break;
            case R.id.tv_sponsors_see_all:
                startActivity(new Intent(getContext(), Sponsors.class));
                break;
            case R.id.guest:
                startActivity(new Intent(getContext(), Guest.class));
                break;
            case R.id.itinerary:
                startActivity(new Intent(getContext(), Itinerary.class));
                break;

        }
    }

    private class CustomScroller extends Scroller {

        private int mDuration;

        CustomScroller(Context context, int mDuration) {
            super(context);
            this.mDuration = mDuration;
        }

        public CustomScroller(Context context, Interpolator interpolator, int mDuration) {
            super(context, interpolator);
            this.mDuration = mDuration;
        }

        public CustomScroller(Context context, Interpolator interpolator, boolean flywheel, int mDuration) {
            super(context, interpolator, flywheel);
            this.mDuration = mDuration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }

}
