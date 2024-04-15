package com.example.firstapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.InformationEvent;
import com.example.firstapp.ListEvent;
import com.example.firstapp.R;
import com.example.firstapp.model.Event;
import com.example.firstapp.model.User;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter <EventAdapter.ViewHolder> {
    private List<Event> listEvent ;

    public EventAdapter() {
    }

    public List<Event> getListEvent() {
        return listEvent;
    }

    private Context context;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setListEvent(List<Event> listEvent) {
        this.listEvent = listEvent;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameEvent.setText(listEvent.get(position).getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        holder.beginTime.setText(simpleDateFormat.format(listEvent.get(position).getBeginTime()));
        holder.endTime.setText(simpleDateFormat.format(listEvent.get(position).getEndTime()));
        boolean check = false;

        if (listEvent.get(position).getUser().getId() == user.getId()){
            holder.backgroundItemEvent.setBackground(context.getDrawable(R.drawable.custom_item_event_own));
            check = true;
        }



        boolean finalCheck = check;
        holder.backgroundItemEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InformationEvent.class);
                Gson gson = new Gson();
                intent.putExtra("event",gson.toJson(listEvent.get(position)));
                intent.putExtra("check_own", finalCheck);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listEvent == null){
            return 0;
        }
        return listEvent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameEvent, beginTime, endTime;
        private ConstraintLayout backgroundItemEvent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameEvent = itemView.findViewById(R.id.name_event);
            beginTime = itemView.findViewById(R.id.begin_time);
            endTime = itemView.findViewById(R.id.end_time);
            backgroundItemEvent = itemView.findViewById(R.id.background_item_event);
        }

    }
}
