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

import in.nitjsr.cognitio.EventFolding.Subevent;
import in.nitjsr.cognitio.Modals.Modal;
import in.nitjsr.cognitio.R;

public class EventAdapterView extends RecyclerView.Adapter<EventAdapterView.ViewHolder> {

    private static Context context;
    private ArrayList<Modal> eventList;

    public EventAdapterView(Context context, ArrayList<Modal> eventList) {
        EventAdapterView.context = context;
        this.eventList = eventList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Modal event = eventList.get(position);
        holder.tvEventName.setText(event.getEventName());
        holder.tvEventPrize.setText(event.getEventPrize());

        Picasso.with(context).load(event.getImage()).placeholder(R.drawable.placeholder2).fit().networkPolicy(NetworkPolicy.OFFLINE).into(holder.ivEventSrc, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load(event.getImage()).placeholder(R.drawable.placeholder2).fit().into(holder.ivEventSrc);
            }
        });

        holder.tvEventDescription.setText(event.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Subevent.class);
                intent.putExtra("EventName", "" + event.getEventName());
                intent.putExtra("EventPosition", position);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEventName, tvEventPrize, tvEventDescription;
        ImageView ivEventSrc;

        public ViewHolder(View itemView) {
            super(itemView);
            tvEventName = itemView.findViewById(R.id.tv_event_name_new);
            tvEventPrize = itemView.findViewById(R.id.tv_event_prize_new);
            ivEventSrc = itemView.findViewById(R.id.event_image);
            tvEventDescription = itemView.findViewById(R.id.tv_event_descrip_new);
        }
    }
}