package in.nitjsr.cognitio.EventFolding;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.nitjsr.cognitio.R;

public class Subevent extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static int position;
    private BottomNavigationView bottomNavigationView;
    private TextView event_title;
    private About fragment_about;
    private Details fragment_details;
    private Coodinators fragment_coordinator;
    private FrameLayout parent_layout;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subevent);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        event_title = findViewById(R.id.spinner);
        parent_layout = findViewById(R.id.parent_layout);

        fragment_about = new About();
        fragment_coordinator = new Coodinators();
        fragment_details = new Details();
        menu = bottomNavigationView.getMenu();

        event_title.setText(getIntent().getExtras().getString("EventName"));
        position = getIntent().getExtras().getInt("EventPosition");

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.parent_layout, fragment_about).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_menu:
                setFragment(fragment_about);
                return true;
            case R.id.details_menu:
                setFragment(fragment_details);
                return true;
            case R.id.coordinator_menu:
                setFragment(fragment_coordinator);
                return true;
            default:
                return true;
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.parent_layout,fragment).commit();
        FragmentTransactionExtended fragmentTransactionExtended =
                new FragmentTransactionExtended(this, fragmentTransaction, new About(), fragment, R.id.parent_layout);
        fragmentTransactionExtended.addTransition(FragmentTransactionExtended.TABLE_HORIZONTAL);
        fragmentTransactionExtended.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
