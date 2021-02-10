package com.practice.freelancebd.Custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.freelancebd.ModelClasses.Message;
import com.practice.freelancebd.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_user_sent_msg_preview,parent,false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        Message msg = messageList.get(position);
        holder.messageTV.setText(msg.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView messageTV;
        private CircleImageView profileImage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            messageTV = itemView.findViewById(R.id.messageTV);
            profileImage = itemView.findViewById(R.id.profileImage);
        }
    }
}
