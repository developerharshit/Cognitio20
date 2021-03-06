package in.nitjsr.cognitio.EventFolding;


import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.nitjsr.cognitio.Activities.MainActivity1;
import in.nitjsr.cognitio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Details extends Fragment {

    TextView heading, body;

    public Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_details, container, false);
        heading = view.findViewById(R.id.heading);
        body = view.findViewById(R.id.body);
        Typeface comic = Typeface.createFromAsset(getActivity().getAssets(), "font/Code New Roman.otf");
        Typeface headingfont = Typeface.createFromAsset(getActivity().getAssets(), "font/Museo_Slab_500italic.otf");
        body.setTypeface(comic);
        heading.setTypeface(headingfont);
        body.setText(Html.fromHtml(MainActivity1.eventsData.get(Subevent.position).getDetails()));
        //body.setText(MainActivity.eventsData.get(SubEvent.position).getDetails());
        return view;
    }

}
