package in.nitjsr.cognitio.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.nitjsr.cognitio.Adapters.EventAdapterView;
import in.nitjsr.cognitio.Modals.Modal;
import in.nitjsr.cognitio.R;
import in.nitjsr.cognitio.Utils.Constants;

public class Events extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        MainActivity1.isWarningShown = false;
        rv = findViewById(R.id.rv_events_new);
        back = findViewById(R.id.back_arrow);
        back.setOnClickListener(this);
        rv.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(new EventAdapterView(this, prepareEventList()));
    }

    private ArrayList<Modal> prepareEventList() {
        ArrayList<Modal> modals = new ArrayList<>();
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[0], Constants.EVENT_DESCRIPTION[0], "Prizes worth: \n\u20B99,000*", Constants.EVENT_NAMES[0]));//Radiation
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[1], Constants.EVENT_DESCRIPTION[1], "Prizes worth: \n\u20B93,000*", Constants.EVENT_NAMES[1]));//wit to veto
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[2], Constants.EVENT_DESCRIPTION[2], "Prizes worth: \n\u20B94,500*", Constants.EVENT_NAMES[2]));//Dictum Symposium
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[3], Constants.EVENT_DESCRIPTION[3], "Prizes worth: \n\u20B94,500*", Constants.EVENT_NAMES[3]));//conundrum
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[4], Constants.EVENT_DESCRIPTION[4], "Prizes worth: \n\u20B94,500*", Constants.EVENT_NAMES[4]));//TIE the KNOT
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[5], Constants.EVENT_DESCRIPTION[5], "Prizes worth: \n\u20B93,000*", Constants.EVENT_NAMES[5]));//Place Station
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[6], Constants.EVENT_DESCRIPTION[6], "Prizes worth: \n\u20B95,000*", Constants.EVENT_NAMES[6]));//Cansys
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[7], Constants.EVENT_DESCRIPTION[7], "Prizes worth: \n\u20B96,000*", Constants.EVENT_NAMES[7]));//Assemblage
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[8], Constants.EVENT_DESCRIPTION[8], "Prizes worth: \n\u20B94,500*", Constants.EVENT_NAMES[8]));//Quriosity
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[9], Constants.EVENT_DESCRIPTION[9], "Prizes worth: \n\u20B95,500*", Constants.EVENT_NAMES[9]));//Shoot at Sight
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[10], Constants.EVENT_DESCRIPTION[10], "Prizes worth: \n\u20B93,000*", Constants.EVENT_NAMES[10]));//naukar shai
        modals.add(new Modal(Constants.EVENT_IMAGE_ONLINE[11], Constants.EVENT_DESCRIPTION[11], "Prizes worth: \n\u20B93,000*", Constants.EVENT_NAMES[11]));//kalam e kalam
        return modals;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_arrow) {
            finish();
        }
    }
}
