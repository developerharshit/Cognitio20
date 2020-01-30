package in.nitjsr.cognitio.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.nitjsr.cognitio.Activities.Sponsors;
import in.nitjsr.cognitio.EventFolding.Subevent;
import in.nitjsr.cognitio.Modals.Modal;
import in.nitjsr.cognitio.R;

import static in.nitjsr.cognitio.Utils.Constants.EVENT_FLAG;
import static in.nitjsr.cognitio.Utils.Constants.SPONSORS_FLAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<Modal> datalist;
    int FLAG;

    public RecyclerViewAdapter(Context context, ArrayList<Modal> datalist, int FLAG) {
        this.context = context;
        this.datalist = datalist;
        this.FLAG = FLAG;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_sec_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textView.setText(datalist.get(position).getEventName());
        Picasso.with(context).load(datalist.get(position).getImage()).placeholder(R.drawable.placeholder_square).fit().networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load(datalist.get(position).getImage()).placeholder(R.drawable.placeholder_square).fit().into(holder.imageView);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (FLAG) {
                    case EVENT_FLAG:
                        Intent intent = new Intent(context, Subevent.class);
                        intent.putExtra("EventName", "" + datalist.get(position).getEventName());
                        if (position == 0) intent.putExtra("EventPosition", 0);
                        else if (position == 1) intent.putExtra("EventPosition", 7);
                        else if (position == 2) intent.putExtra("EventPosition", 9);
                        else if (position == 3) intent.putExtra("EventPosition", 6);
                        else if (position == 4) intent.putExtra("EventPosition", 8);
                        context.startActivity(intent);
                        break;
                    case SPONSORS_FLAG:
                        context.startActivity(new Intent(context, Sponsors.class));
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.event_image);
            textView = itemView.findViewById(R.id.event_name);
        }
    }
}
