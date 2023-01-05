package com.firetera.umaklibraryapp.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firetera.umaklibraryapp.Model.Model1;
import com.firetera.umaklibraryapp.R;

import java.util.ArrayList;

public class Adapter1 extends RecyclerView.Adapter<Adapter1.MyViewHolder> {

    public final MyInterface myInterfaces;


    Context context;
    ArrayList<Model1> model1;
    String onclick;

    public Adapter1(Context context, ArrayList<Model1> model1, MyInterface myInterfaces, String onclick){
        this.context = context;
        this.model1 = model1;
        this.myInterfaces = myInterfaces;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public Adapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_adapter1, parent, false);

        return new Adapter1.MyViewHolder(view, myInterfaces, onclick);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter1.MyViewHolder holder, int position) {
        holder.text.setText(model1.get(position).getTitle());
        holder.image.setImageResource(model1.get(position).getImage());

    }


    @Override
    public int getItemCount() {

        return model1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text;
        ImageView image;
        public MyViewHolder(@NonNull View itemView, MyInterface myInterfaces, String onclick) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(view -> {
                if(myInterfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        myInterfaces.onItemClick(pos, onclick);
                    }

                }
            });
        }
    }


}
