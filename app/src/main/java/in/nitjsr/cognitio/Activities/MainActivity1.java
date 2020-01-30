package in.nitjsr.cognitio.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import in.nitjsr.cognitio.Adapters.MainActivityAdapter;
import in.nitjsr.cognitio.Modals.EventHeadModal;
import in.nitjsr.cognitio.Modals.EventsModals;
import in.nitjsr.cognitio.R;
import in.nitjsr.cognitio.Utils.Constants;
import in.nitjsr.cognitio.Utils.CustomViewPager;
import in.nitjsr.cognitio.Utils.SharedPrefManager;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final String SCREEN_NUMBER = "screen_num";
    private static final String urlOfApp = "https://play.google.com/store/apps/details?id=in.nitjsr.cognitio&hl=en";
    public static boolean isWarningShown;
    public static ArrayList<EventsModals> eventsData;
    public static String[] Itinary_Images;
    public static String[] Itinary_Images_Posters;
    public static String payment_amt_nit;
    public static String payment_amt_outside;
    public static String switch_payment;
    public static String instruction;
    public static boolean isSchool;
    private static CustomViewPager viewPager;
    private ImageView menu, notif, qr_code;
    private ImageView home_icon, gallery_icon, event_icon, profile_icon;
    private TextView home_text, gallery_text, event_text, profile_text;
    private RelativeLayout home_view, gallery_view, event_view, profile_view;
    private ProgressDialog progressDialog;
    private String currentVersion;
    private SlidingUpPanelLayout slidePanel;
    private LinearLayout ll_teams, ll_guest, ll_maps, ll_sponsors, ll_contact_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        OneSignal.sendTag("Admin", "Admin");

        isWarningShown = false;

        menu = findViewById(R.id.icon);
        notif = findViewById(R.id.notif);
        qr_code = findViewById(R.id.qr_code);

        home_icon = findViewById(R.id.home_icon);
        home_text = findViewById(R.id.home_text);
        home_view = findViewById(R.id.home_view);
        gallery_icon = findViewById(R.id.gallery_icon);
        gallery_text = findViewById(R.id.gallery_text);
        gallery_view = findViewById(R.id.gallery_view);
        event_icon = findViewById(R.id.event_icon);
        event_text = findViewById(R.id.event_text);
        event_view = findViewById(R.id.event_view);
        profile_icon = findViewById(R.id.profile_icon);
        profile_text = findViewById(R.id.profile_text);
        profile_view = findViewById(R.id.profile_view);
        slidePanel = findViewById(R.id.slide_panel);
        ll_contact_us = findViewById(R.id.ll_contact_us_menu);
        ll_guest = findViewById(R.id.ll_guest_menu);
        ll_maps = findViewById(R.id.ll_maps_menu);
        ll_teams = findViewById(R.id.ll_team_menu);
        ll_sponsors = findViewById(R.id.ll_sponsors_menu);

        menu.setOnClickListener(this);
        notif.setOnClickListener(this);
        qr_code.setOnClickListener(this);

        home_view.setOnClickListener(this);
        event_view.setOnClickListener(this);
        gallery_view.setOnClickListener(this);
        profile_view.setOnClickListener(this);
        ll_contact_us.setOnClickListener(this);
        ll_guest.setOnClickListener(this);
        ll_maps.setOnClickListener(this);
        ll_teams.setOnClickListener(this);
        ll_sponsors.setOnClickListener(this);

        viewPager = findViewById(R.id.body_view_pager);
        viewPager.setAdapter(new MainActivityAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPagingEnabled(false);

        try {
            if (getIntent().getExtras().getInt(SCREEN_NUMBER, -1) != -1) {
                viewPager.setCurrentItem(getIntent().getExtras().getInt(SCREEN_NUMBER));
            }
        } catch (Exception e) {
        }
        slidePanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        compareAppVersion();
        setEventData();
//        setItinerary();
        setPaymentAmt();
        setSwitchData();
        setInstructionPage();
    }

    private void setInstructionPage() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child(Constants.FIREBASE_REF_INSTRUCTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                instruction = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setSwitchData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child(Constants.FIREBASE_REF_SWITCH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                switch_payment = dataSnapshot.child("payment").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setItinerary() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(Constants.FIREBASE_REF_IMAGES).child(Constants.FIREBASE_REF_ITINERARY_POSTERS);
        databaseReference.keepSynced(true);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Itinary_Images_Posters = new String[(int) dataSnapshot.getChildrenCount()];
                int count = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Itinary_Images_Posters[count] = ds.getValue().toString();
                    count++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        slidePanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        isWarningShown = false;
        if (view == ll_teams) {
            startActivity(new Intent(this, CoreTeam.class));
        } else if (view == ll_guest) {
            startActivity(new Intent(this, Guest.class));
        } else if (view == ll_maps) {
            startActivity(new Intent(this, MapsActivity.class));
        } else if (view == ll_sponsors) {
            startActivity(new Intent(this, Sponsors.class));
        } else if (view == ll_contact_us) {
            startActivity(new Intent(this, ContactUs.class));
        }

        switch (view.getId()) {
            case R.id.icon:
                startActivity(new Intent(this, AboutUs.class));
                break;
            case R.id.notif:
                startActivity(new Intent(this, Notification.class));
                break;
            case R.id.home_view:
                changeNavIconColor(home_view);
                viewPager.setCurrentItem(0);
                break;
            case R.id.event_view:
                changeNavIconColor(event_view);
                viewPager.setCurrentItem(1);
                break;
            case R.id.gallery_view:
                changeNavIconColor(gallery_view);
                viewPager.setCurrentItem(2);
                break;
            case R.id.profile_view:
                changeNavIconColor(profile_view);
                viewPager.setCurrentItem(3);
                break;
            case R.id.qr_code:
                showQrDialog();
                break;
        }
    }

    private boolean checkAndClosePanel() {
        if (slidePanel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED ||
                slidePanel.getPanelState() == SlidingUpPanelLayout.PanelState.DRAGGING) {
            isWarningShown = false;
            slidePanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return true;
        } else return false;
    }

    public void showQrDialog() {
        final Dialog QRDialog = new Dialog(this);
        QRDialog.setContentView(R.layout.dialog_qr);
        final TextView tvCongnitioId = QRDialog.findViewById(R.id.tv_cognitio_id);
        final ImageView ivQR = QRDialog.findViewById(R.id.iv_qr_code);
        QRDialog.getWindow().getAttributes().windowAnimations = R.style.pop_up_anim;
        QRDialog.show();
        QRDialog.findViewById(R.id.rl_qr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvCongnitioId.getText().toString().equals(Constants.NOT_REGISTERED)) {
                    QRDialog.dismiss();
                }
            }
        });
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_REF_USERS).child(FirebaseAuth.getInstance().getUid());
            ref.keepSynced(true);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Picasso.with(MainActivity1.this).load("https://api.qrserver.com/v1/create-qr-code/?data=" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "&size=240x240&margin=10").placeholder(R.drawable.placeholder_square).fit().networkPolicy(NetworkPolicy.OFFLINE).into(ivQR, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(MainActivity1.this).load("https://api.qrserver.com/v1/create-qr-code/?data=" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "&size=240x240&margin=10").placeholder(R.drawable.placeholder_square).fit().into(ivQR);
                            }
                        });
                        int cogId = Integer.parseInt(dataSnapshot.child(Constants.FIREBASE_REF_COGNITIO_ID).getValue().toString());
                        if (cogId < 10) {
                            tvCongnitioId.setText("COG00" + cogId);
                        } else if (cogId < 100) {
                            tvCongnitioId.setText("COG0" + cogId);
                        } else {
                            tvCongnitioId.setText("COG" + cogId);
                        }
                        tvCongnitioId.setTextColor(getResources().getColor(R.color.forest_green));
                    } else {
                        ivQR.setImageResource(R.drawable.placeholder_square);
                        tvCongnitioId.setText(Constants.NOT_REGISTERED);
                        tvCongnitioId.setTextColor(Color.RED);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            ivQR.setImageResource(R.drawable.placeholder_square);
            tvCongnitioId.setText(Constants.NOT_REGISTERED);
            tvCongnitioId.setTextColor(Color.RED);
        }
    }


    public void changeNavIconColor(View view) {
        switch (view.getId()) {
            case R.id.home_view:
                home_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGreen));
                event_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGrey));
                gallery_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGrey));
                profile_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGrey));

                home_text.setTextColor(getResources().getColor(R.color.navGreen));
                event_text.setTextColor(getResources().getColor(R.color.navGrey));
                gallery_text.setTextColor(getResources().getColor(R.color.navGrey));
                profile_text.setTextColor(getResources().getColor(R.color.navGrey));

                break;

            case R.id.event_view:
                event_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGreen));
                home_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGrey));
                gallery_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGrey));
                profile_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGrey));

                event_text.setTextColor(getResources().getColor(R.color.navGreen));
                home_text.setTextColor(getResources().getColor(R.color.navGrey));
                gallery_text.setTextColor(getResources().getColor(R.color.navGrey));
                profile_text.setTextColor(getResources().getColor(R.color.navGrey));

                break;

            case R.id.gallery_view:
                gallery_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGreen));
                event_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGrey));
                home_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGrey));
                profile_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGrey));

                gallery_text.setTextColor(getResources().getColor(R.color.navGreen));
                event_text.setTextColor(getResources().getColor(R.color.navGrey));
                home_text.setTextColor(getResources().getColor(R.color.navGrey));
                profile_text.setTextColor(getResources().getColor(R.color.navGrey));

                break;

            case R.id.profile_view:
                profile_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGreen));
                event_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGrey));
                gallery_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGrey));
                home_icon.setColorFilter(ContextCompat.getColor(this, R.color.navGrey));

                profile_text.setTextColor(getResources().getColor(R.color.navGreen));
                event_text.setTextColor(getResources().getColor(R.color.navGrey));
                gallery_text.setTextColor(getResources().getColor(R.color.navGrey));
                home_text.setTextColor(getResources().getColor(R.color.navGrey));

                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                changeNavIconColor(home_view);
                break;
            case 1:
                changeNavIconColor(event_view);
                break;
            case 2:
                changeNavIconColor(gallery_view);
                break;
            case 3:
                changeNavIconColor(profile_view);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setEventData() {
        eventsData = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Events");
        database.keepSynced(true);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                eventsData.clear();
                try {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String about = ds.child("About").getValue().toString();
                        String details = ds.child("Details").getValue().toString();
                        ArrayList<EventHeadModal> eventsHead = new ArrayList<>();
                        eventsHead.clear();
                        for (DataSnapshot d : ds.child("EventHead").getChildren()) {
                            String name = d.child("name").getValue().toString();
                            String phone = d.child("phone").getValue().toString();
                            eventsHead.add(new EventHeadModal(name, phone));
                        }
                        eventsData.add(new EventsModals(about, details, eventsHead));
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(MainActivity1.this, "Problem Loading Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (progressDialog.isShowing()) progressDialog.dismiss();
                Toast.makeText(MainActivity1.this, "Problem Loading Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (checkAndClosePanel()) {
        } else {
            if (viewPager.getCurrentItem() == 0) {
                if (!isWarningShown) {
                    Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                    isWarningShown = true;
                } else finish();
            } else {
                viewPager.setCurrentItem(0);
                isWarningShown = false;
            }
        }

    }

    public void setPaymentAmt() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        ref.child(Constants.FIREBASE_REF_PAYMENT_AMT).keepSynced(true);
        ref.child(Constants.FIREBASE_REF_PAYMENT_AMT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                payment_amt_nit = dataSnapshot.child("NIT").getValue().toString();
                payment_amt_outside = dataSnapshot.child("outside").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (mAuth.getCurrentUser() != null) {
            ref.child(Constants.FIREBASE_REF_USERS).child(mAuth.getCurrentUser().getUid()).child(Constants.FIREBASE_REF_COLLEGE_CODE).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        isSchool = dataSnapshot.getValue().equals("1");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void compareAppVersion() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
            currentVersion = pInfo.versionName;
            new GetCurrentVersion().execute();
        } catch (PackageManager.NameNotFoundException e1) {
            Toast.makeText(this, e1.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New update");
        builder.setMessage("We have changed since we last met. Let's get the updates.");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=in.nitjsr.cognitio")));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

//    class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
//        @Override
//        public void notificationOpened(OSNotificationOpenResult result) {
//            startActivity(new Intent(MainActivity1.this, Notification.class));
//        }
//    }

    private class GetCurrentVersion extends AsyncTask<Void, Void, Void> {

        Document doc;
        private String latestVersion;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(urlOfApp)
                        .timeout(30000)
//                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                latestVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!TextUtils.isEmpty(currentVersion) && !TextUtils.isEmpty(latestVersion)) {
//                Log.d("hello", doc.toString());
                Log.d("hello", "Current : " + currentVersion + " Latest : " + latestVersion);
                if (currentVersion.compareTo(latestVersion) < 0) {
                    if (!isFinishing()) {
                        showUpdateDialog();
                    }
                }
            }
            super.onPostExecute(aVoid);
        }
    }
}
