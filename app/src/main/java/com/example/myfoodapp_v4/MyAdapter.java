package com.example.myfoodapp_v4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    //the declaration of all the arrays neccessary for recycler view and for context
    String Aname[], Aphone[], Aaddress[], Arating[], Adistance[];
    Context context;

    public MyAdapter(Context ct, String name[], String phone[], String address[], String distance[], String rating[]){
     //transferring all arrays from DisplayActivity to the arrays that are created here...possible way to stream all to recyclerview but i wanted it this way
        context = ct;
        Aname = name;
        Aphone = phone;
        Aaddress = address;
        Arating = rating;
        Adistance = distance;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //this is to inflate the my row layout to push in data
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //this is where the arrays push data to layout into the designated ids
    holder.mName.setText(Aname[position]);
    holder.mPhone.setText(Aphone[position]);
    holder.mAddress.setText(Aaddress[position]);
    holder.mRating.setText(Arating[position]+" rating");
    holder.mDistance.setText(Adistance[position] + " miles");


    }
//this gets the length of the array i used Aname just because its the first 1 and if anything it would end there also you can choose any of the above arrays they have same count
    @Override
    public int getItemCount() {
        return Aname.length;
    }
///connecting variables to the respected id in the layout
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mName, mPhone, mAddress, mRating, mDistance;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.displayname);
            mPhone = itemView.findViewById(R.id.displayphone);
            mAddress = itemView.findViewById(R.id.displayaddress);
            mRating = itemView.findViewById(R.id.displayrating);
            mDistance = itemView.findViewById(R.id.displaydistance);
        }
    }
}
