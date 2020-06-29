package com.practice.freelancebd.Custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.freelancebd.ModelClasses.Image;
import com.practice.freelancebd.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<Image>imageList;
    private Context context;

    public ImageAdapter(List<Image> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_select_gallery_image_layout_demo,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Image image = imageList.get(position);

        holder.userSelectedImageIV.setImageURI(image.getUserSelectUri());
        holder.fileNameTV.setText(image.getFileName());

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentPosition = imageList.indexOf(image);
                imageList.remove(currentPosition);
                notifyItemRemoved(currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView userSelectedImageIV, deleteIcon;
        private TextView fileNameTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userSelectedImageIV = itemView.findViewById(R.id.userSelectedImageIV);
            deleteIcon= itemView.findViewById(R.id.deleteIcon);
            fileNameTV = itemView.findViewById(R.id.fileNameTV);
        }
    }
}
