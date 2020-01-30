package in.nitjsr.cognitio.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.nitjsr.cognitio.Adapters.EventAdapterView;
import in.nitjsr.cognitio.Modals.Modal;
import in.nitjsr.cognitio.R;
import in.nitjsr.cognitio.Utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        rv = view.findViewById(R.id.rv_events_new);
        rv.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        rv.setAdapter(new EventAdapterView(getContext(), prepareEventList()));
        return view;
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


}
