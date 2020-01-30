package in.nitjsr.cognitio.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.nitjsr.cognitio.Adapters.ExpandableListAdapter;
import in.nitjsr.cognitio.Modals.ExpandableListModal;
import in.nitjsr.cognitio.R;
import in.nitjsr.cognitio.Utils.Constants;

public class Notification extends AppCompatActivity implements View.OnClickListener {

    static ArrayList<ExpandableListModal> listData_Notifs;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    TextView hintText;
    ImageView back;
    int grp_expanded = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        back = findViewById(R.id.back_arrow);
        back.setOnClickListener(this);
//        MainActivity.isWarningShown=false;

        listData_Notifs = new ArrayList<>();

        // get the listview
        expListView = findViewById(R.id.lvExp);
        hintText = findViewById(R.id.hint_text);

        listAdapter = new ExpandableListAdapter(this, prepareListData());
        // setting list adapter
        expListView.setAdapter(listAdapter);
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (grp_expanded != -1 && grp_expanded != groupPosition) {
                    expListView.collapseGroup(grp_expanded);
                }
                grp_expanded = groupPosition;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private ArrayList<ExpandableListModal> prepareListData() {

        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child(Constants.FIREBASE_REF_NOTIFICATIONS);
            databaseReference.keepSynced(true);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        hintText.setVisibility(View.GONE);
                        expListView.setVisibility(View.VISIBLE);
                        listData_Notifs.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String header = ds.child("Header").getValue().toString();
                            String desc = ds.child("Description").getValue().toString();
//                            ExpandableListModal faqs = ds.getValue(ExpandableListModal.class);
                            listData_Notifs.add(new ExpandableListModal(header, desc, null, null));
                            listAdapter.notifyDataSetChanged();
                        }
                    } else {
                        hintText.setVisibility(View.VISIBLE);
                        expListView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
        }

        return listData_Notifs;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_arrow) finish();
    }
}
