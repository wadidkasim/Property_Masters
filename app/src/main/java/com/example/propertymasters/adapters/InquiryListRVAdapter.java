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
import com.example.propertymasters.models.Inquiry;
import com.example.propertymasters.models.Property;

import java.util.ArrayList;

public class InquiryListRVAdapter extends RecyclerView.Adapter<InquiryListRVAdapter.ViewHolder> {

    ArrayList<Inquiry> inquiryArrayList;
    Context context;
    RequestOptions option;

    public InquiryListRVAdapter(ArrayList<Inquiry> inquiryArrayList, Context context) {
        this.inquiryArrayList = inquiryArrayList;
        this.context = context;
        option= new RequestOptions().centerCrop().placeholder(R.drawable.placeholder).error(R.drawable.placeholder);
    }

    @NonNull
    @Override
    public InquiryListRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inquiry_list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InquiryListRVAdapter.ViewHolder holder, int position) {
        Inquiry inquiry = inquiryArrayList.get(position);
        Log.e("property id",String.valueOf(inquiry.getInquiryID()));
        holder.nameTV.setText(inquiry.getName());
        holder.locationTV.setText(inquiry.getLocation());
        holder.priceTV.setText("UGx."+inquiry.getPrice()+"/=");
        holder.message.setText(inquiry.getMessage());
        holder.email.setText(inquiry.getEmail());
        holder.phone.setText(inquiry.getPhone());
        holder.status.setText(inquiry.getStatus());
//        if(property.isAvailable()){
//            holder.availableTV.setText("Available");
//            int color = ContextCompat.getColor(context, R.color.available); // Replace with your desired color resource
//            holder.card.setCardBackgroundColor(color);
//        } else {
//            holder.availableTV.setText("Sold");
//            int color = ContextCompat.getColor(context, R.color.sold); // Replace with your desired color resource
//            holder.card.setCardBackgroundColor(color);
//        }


        //Glide.with(context).load(property.getImageURL()).apply(option).into(holder.propertyImg);
        Glide.with(context)
                .load(inquiry.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.propertyImg);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, PropertyDetailsActivity.class);
//
//                intent.putExtra("id",""+inquiry.getPropertyID());
//                intent.putExtra("name",property.getName());
//                intent.putExtra("price","UGx."+property.getPrice()+"/=");
//                intent.putExtra("description",property.getDescription());
//                intent.putExtra("location",property.getLocation());
//                intent.putExtra("image",property.getImageURL());
//                intent.putExtra("propertyType",property.getPropertyTypeName());
//
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return inquiryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView propertyImg;
        TextView nameTV,locationTV,priceTV,availableTV,status,email,phone,message;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV=itemView.findViewById(R.id.name);
            priceTV=itemView.findViewById(R.id.price);
            locationTV=itemView.findViewById(R.id.location);
            propertyImg=itemView.findViewById(R.id.imageView);
            availableTV=itemView.findViewById(R.id.available);
            card=itemView.findViewById(R.id.card);
            status=itemView.findViewById(R.id.status);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            message= itemView.findViewById(R.id.message);



        }
    }

    public void setData(ArrayList<Inquiry> newData) {
        inquiryArrayList.clear();
        inquiryArrayList.addAll(newData);
        notifyDataSetChanged();
    }
}
