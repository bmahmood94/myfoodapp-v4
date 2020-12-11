package com.example.myfoodapp_v4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    String Aname[], Aphone[];
    Context context;

    public MyAdapter(Context ct, String name[], String phone[]){
        context=ct;
        Aname= name;
        Aphone=phone;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.mName.setText(Aname[position]);
    holder.mPhone.setText(Aphone[position]);
    }

    @Override
    public int getItemCount() {
        return Aname.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mName, mPhone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.displayname);
            mPhone = itemView.findViewById(R.id.displayphone);
        }
    }
}
