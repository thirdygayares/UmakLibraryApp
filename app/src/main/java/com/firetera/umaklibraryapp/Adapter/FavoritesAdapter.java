 package com.firetera.umaklibraryapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firetera.umaklibraryapp.R;
import com.squareup.picasso.Picasso;
import com.firetera.umaklibraryapp.Model.FavoriteModel;


import java.util.ArrayList;

 public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder> {

     public final MyInterface myInterfaces;


     Context context;
     ArrayList<FavoriteModel> favoritesModels;
     String onclick;

     public FavoritesAdapter(Context context, ArrayList<FavoriteModel> favoritesModels, MyInterface myInterfaces, String onclick){
         this.context = context;
         this.favoritesModels = favoritesModels;
         this.myInterfaces = myInterfaces;
         this.onclick = onclick;
     }

     @NonNull
     @Override
     public FavoritesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
         LayoutInflater inflater = LayoutInflater.from(context);
         View view = inflater.inflate(R.layout.activity_recycler_favorites, parent, false);

         return new FavoritesAdapter.MyViewHolder(view, myInterfaces, onclick);
     }

     @Override
     public void onBindViewHolder(@NonNull FavoritesAdapter.MyViewHolder holder, int position) {

         holder.txt_count.setText(String.valueOf(position + 1));
         holder.txt_title.setText(favoritesModels.get(position).getTitle());
         holder.txt_author.setText(favoritesModels.get(position).getAuthor());

         Picasso.get().load(favoritesModels.get(position).getImage()).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).resize(720,1080).centerCrop().into(holder.img_book_cover);

     }


     @Override
     public int getItemCount() {

         return favoritesModels.size();
     }

     public static class MyViewHolder extends RecyclerView.ViewHolder{

         TextView txt_count,txt_title,txt_author;
         ImageView img_book_cover;
         public MyViewHolder(@NonNull View itemView, MyInterface myInterfaces, String onclick) {
             super(itemView);

             txt_author = itemView.findViewById(R.id.txt_author);
             txt_title = itemView.findViewById(R.id.txt_title);
             txt_count = itemView.findViewById(R.id.txt_count);
             img_book_cover = itemView.findViewById(R.id.img_book_cover);

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
