package com.example.propertymasters.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.propertymasters.R;
import com.example.propertymasters.activities.PropertyDetailsActivity;
import com.example.propertymasters.activities.PropertyDetailsAdminActivity;
import com.example.propertymasters.models.Property;

import java.util.ArrayList;

public class PropertyListAdminRVAdapter extends RecyclerView.Adapter<PropertyListAdminRVAdapter.ViewHolder> {

    ArrayList<Property> propertyArrayList;
    Context context;
    RequestOptions option;

    public PropertyListAdminRVAdapter(ArrayList<Property> propertyArrayList, Context context) {
        this.propertyArrayList = propertyArrayList;
        this.context = context;
        option= new RequestOptions().centerCrop().placeholder(R.drawable.placeholder).error(R.drawable.placeholder);
    }

    @NonNull
    @Override
    public PropertyListAdminRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyListAdminRVAdapter.ViewHolder holder, int position) {
        Property property = propertyArrayList.get(position);
        Log.e("property id",String.valueOf(property.getPropertyID()));
        holder.nameTV.setText(property.getName());
        holder.locationTV.setText(property.getLocation());
        holder.priceTV.setText("UGx."+property.getPrice()+"/=");
        if(property.isAvailable()){
            holder.availableTV.setText("Available");
            int color = ContextCompat.getColor(context, R.color.available); // Replace with your desired color resource
            holder.card.setCardBackgroundColor(color);
        } else {
            holder.availableTV.setText("Sold");
            int color = ContextCompat.getColor(context, R.color.sold); // Replace with your desired color resource
            holder.card.setCardBackgroundColor(color);
        }


        //Glide.with(context).load(property.getImageURL()).apply(option).into(holder.propertyImg);
        Glide.with(context)
                .load(property.getImageURL())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.propertyImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PropertyDetailsAdminActivity.class);

                intent.putExtra("id",""+property.getPropertyID());
                intent.putExtra("name",property.getName());
                intent.putExtra("price",""+property.getPrice());
                intent.putExtra("description",property.getDescription());
                intent.putExtra("location",property.getLocation());
                intent.putExtra("image",property.getImageURL());
                intent.putExtra("propertyType",property.getPropertyTypeName());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return propertyArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView propertyImg;
        TextView nameTV,locationTV,priceTV,availableTV;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV=itemView.findViewById(R.id.name);
            priceTV=itemView.findViewById(R.id.price);
            locationTV=itemView.findViewById(R.id.location);
            propertyImg=itemView.findViewById(R.id.imageView);
            availableTV=itemView.findViewById(R.id.available);
            card=itemView.findViewById(R.id.card);


        }
    }

    public void setData(ArrayList<Property> newData) {
        propertyArrayList.clear();
        propertyArrayList.addAll(newData);
        notifyDataSetChanged();
    }
}
