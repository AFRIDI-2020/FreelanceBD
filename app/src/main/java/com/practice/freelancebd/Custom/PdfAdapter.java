package com.practice.freelancebd.Custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.freelancebd.ModelClasses.Pdf;
import com.practice.freelancebd.R;

import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.ViewHolder> {

    private List<Pdf>pdfList;
    private Context context;

    public PdfAdapter(List<Pdf> pdfList, Context context) {
        this.pdfList = pdfList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_select_pdf_layout_demo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Pdf pdf = pdfList.get(position);

        holder.pdfFileNameTV.setText(pdf.getPdfName());
        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              int currentPosition = pdfList.indexOf(pdf);
              pdfList.remove(currentPosition);
              notifyItemRemoved(currentPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pdfFileNameTV;
        private ImageView deleteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pdfFileNameTV = itemView.findViewById(R.id.pdfFileNameTV);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
        }
    }
}
