package com.example.firstapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.ToDoListDetails;
import com.example.firstapp.api.ApiService;
import com.example.firstapp.model.EventJob;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventJobAdapter extends RecyclerView.Adapter<EventJobAdapter.ViewHolder>{
    private List<EventJob> listEventJob;

    private Context context;

    public void setListEventJob(List<EventJob> listEventJob) {
        this.listEventJob = listEventJob;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work, parent, false);
        return new EventJobAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameWork.setText(listEventJob.get(position).getName());

        if (listEventJob.get(position).getStatus() == 0) {
            holder.tick.setChecked(false);
        } else {
            holder.tick.setChecked(true);
        }


        if (listEventJob.get(position).getLevel() == 0) {
            holder.flag.setImageResource(R.drawable.icons_flag_gray);
        }
        if (listEventJob.get(position).getLevel() == 1) {
            holder.flag.setImageResource(R.drawable.icons_flag_green);
        }
        if (listEventJob.get(position).getLevel() == 2) {
            holder.flag.setImageResource(R.drawable.icons_flag_yellow);
        }
        if (listEventJob.get(position).getLevel() == 3) {
            holder.flag.setImageResource(R.drawable.icons_flag_red);
        }

        holder.flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_flag,popupMenu.getMenu());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    popupMenu.setForceShowIcon(true);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        EventJob eventJob = listEventJob.get(position);
                        if (id == R.id.flag_red){
                            holder.flag.setImageResource(R.drawable.icons_flag_red);
                            eventJob.setLevel(3);
                        }
                        if (id == R.id.flag_yellow){
                            holder.flag.setImageResource(R.drawable.icons_flag_yellow);
                            eventJob.setLevel(2);
                        }
                        if (id == R.id.flag_green){
                            holder.flag.setImageResource(R.drawable.icons_flag_green);
                            eventJob.setLevel(1);
                        }
                        ApiService.apiService.updateEventJob(eventJob).enqueue(new Callback<EventJob>() {
                            @Override
                            public void onResponse(Call<EventJob> call, Response<EventJob> response) {

                            }

                            @Override
                            public void onFailure(Call<EventJob> call, Throwable t) {

                            }
                        });
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        holder.nameWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ToDoListDetails.class);
                Gson gson = new Gson();

                intent.putExtra("details", gson.toJson(listEventJob.get(position)));
                context.startActivity(intent);
            }
        });

        holder.nameWork.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                listEventJob.remove(position);
                notifyDataSetChanged();
                ApiService.apiService.deleteEventJob(listEventJob.get(position).getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
                return false;
            }
        });
        holder.tick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EventJob eventJob = listEventJob.get(position);
                if(isChecked){
                    eventJob.setStatus(1);
                }
                else {
                    eventJob.setStatus(0);
                }
                ApiService.apiService.updateEventJob(eventJob).enqueue(new Callback<EventJob>() {
                    @Override
                    public void onResponse(Call<EventJob> call, Response<EventJob> response) {

                    }

                    @Override
                    public void onFailure(Call<EventJob> call, Throwable t) {

                    }
                });
            }

        });

    }

    @Override
    public int getItemCount() {
        if (listEventJob ==  null){
            return 0;
        }
        return listEventJob.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox tick;
        private TextView nameWork;

        private ImageView flag;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tick = itemView.findViewById(R.id.checkBox);
            nameWork = itemView.findViewById(R.id.name_work);
            flag = itemView.findViewById(R.id.flag);
        }
    }

}
