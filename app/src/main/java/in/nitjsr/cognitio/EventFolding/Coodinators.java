package in.nitjsr.cognitio.EventFolding;


import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.nitjsr.cognitio.Activities.MainActivity1;
import in.nitjsr.cognitio.Adapters.EventHeadAdapter;
import in.nitjsr.cognitio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Coodinators extends Fragment {


    TextView heading, body;
    RecyclerView rv;

    public Coodinators() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_coodinators, container, false);
        heading = view.findViewById(R.id.heading);
        rv = view.findViewById(R.id.rv_eventhead);
        body = view.findViewById(R.id.body);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(new EventHeadAdapter(getActivity(), MainActivity1.eventsData.get(Subevent.position).getEventHead()));
        Typeface headingfont = Typeface.createFromAsset(getActivity().getAssets(), "font/Museo_Slab_500italic.otf");
        heading.setTypeface(headingfont);

        return view;
    }

}
