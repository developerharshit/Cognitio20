package in.nitjsr.cognitio.Adapters;

import android.content.Context;
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

import in.nitjsr.cognitio.Modals.TeamModal;
import in.nitjsr.cognitio.R;

public class SponsorsAdapter extends RecyclerView.Adapter<SponsorsAdapter.ViewHolder> {

    ArrayList<TeamModal> dataList;
    Context context;

    public SponsorsAdapter(Context context, ArrayList<TeamModal> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.item_sponsers_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setPicassoImage(holder, position);
        holder.tv_descrp.setText(dataList.get(position).getDescription());
    }

    private void setPicassoImage(final ViewHolder holder, final int position) {
        if (dataList.size() > 0) {
            Picasso.with(context).load(dataList.get(position).getImage()).placeholder(R.drawable.placeholder).fit().networkPolicy(NetworkPolicy.OFFLINE).into(holder.iv_icon, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(dataList.get(position).getImage()).placeholder(R.drawable.placeholder).fit().into(holder.iv_icon);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_icon;
        TextView tv_descrp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.sponsers_icon);
            tv_descrp = itemView.findViewById(R.id.sponsers_desp);
        }
    }
}