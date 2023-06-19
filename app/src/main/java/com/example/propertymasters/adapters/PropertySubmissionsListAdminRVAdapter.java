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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.propertymasters.R;
import com.example.propertymasters.activities.PropertyDetailsActivity;
import com.example.propertymasters.activities.SubmissionDetailsAdminActivity;
import com.example.propertymasters.models.PropertySubmission;

import java.util.ArrayList;

public class PropertySubmissionsListAdminRVAdapter extends RecyclerView.Adapter<PropertySubmissionsListAdminRVAdapter.ViewHolder> {

    ArrayList<PropertySubmission> propertySubmissionArrayList;
    Context context;
    RequestOptions option;

    public PropertySubmissionsListAdminRVAdapter(ArrayList<PropertySubmission> propertySubmissionArrayList, Context context) {
        this.propertySubmissionArrayList = propertySubmissionArrayList;
        this.context = context;
        option= new RequestOptions().centerCrop().placeholder(R.drawable.placeholder).error(R.drawable.placeholder);
    }

    @NonNull
    @Override
    public PropertySubmissionsListAdminRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_submission_list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertySubmissionsListAdminRVAdapter.ViewHolder holder, int position) {
        PropertySubmission propertySubmission = propertySubmissionArrayList.get(position);
        Log.e("property id",String.valueOf(propertySubmission.getPropertyID()));
        holder.nameTV.setText(propertySubmission.getName());
        holder.locationTV.setText(propertySubmission.getLocation());
        holder.priceTV.setText("UGx."+propertySubmission.getPrice()+"/=");
        holder.statusTV.setText(propertySubmission.getStatus());
//        if(propertySubmission.isAvailable()){
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
                .load(propertySubmission.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.propertyImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubmissionDetailsAdminActivity.class);

                intent.putExtra("id",""+propertySubmission.getPropertyID());
                intent.putExtra("submissionId",""+propertySubmission.getSubmissionID());
                intent.putExtra("name",propertySubmission.getName());
                intent.putExtra("price","UGx."+propertySubmission.getPrice()+"/=");
                intent.putExtra("description",propertySubmission.getDescription());
                intent.putExtra("location",propertySubmission.getLocation());
                intent.putExtra("image",propertySubmission.getImageUrl());
                intent.putExtra("propertyType",propertySubmission.getPropertyType());
                intent.putExtra("email",propertySubmission.getEmail());
                intent.putExtra("phone",propertySubmission.getPhone());
                intent.putExtra("reply",propertySubmission.getReply());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return propertySubmissionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView propertyImg;
        TextView nameTV,locationTV,priceTV,statusTV;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV=itemView.findViewById(R.id.name);
            priceTV=itemView.findViewById(R.id.price);
            locationTV=itemView.findViewById(R.id.location);
            propertyImg=itemView.findViewById(R.id.imageView);
            statusTV=itemView.findViewById(R.id.status);
            card=itemView.findViewById(R.id.card);


        }
    }

    public void setData(ArrayList<PropertySubmission> newData) {
        propertySubmissionArrayList.clear();
        propertySubmissionArrayList.addAll(newData);
        notifyDataSetChanged();
    }
}
