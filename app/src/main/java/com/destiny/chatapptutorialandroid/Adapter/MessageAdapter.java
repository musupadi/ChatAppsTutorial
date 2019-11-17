package com.destiny.chatapptutorialandroid.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.destiny.chatapptutorialandroid.Model.AllMethod;
import com.destiny.chatapptutorialandroid.Model.Message;
import com.destiny.chatapptutorialandroid.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder> {

    Context context;
    List<Message> messages;
    DatabaseReference messageDb;

    public MessageAdapter(Context context,List<Message> messages,DatabaseReference messageDb){
        this.context=context;
        this.messageDb = messageDb;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message,parent,false);
        return new MessageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapterViewHolder holder, int position) {
        Message message = messages.get(position);
        if (message.getName().equals(AllMethod.class)){
            holder.tvTittle.setText("You : "+message.getMessage());
            holder.tvTittle.setGravity(Gravity.LEFT);
        }else{
            holder.tvTittle.setText(message.getName()+ " : "+message.getMessage());
            holder.ibDelete.setVisibility(View.GONE);
            holder.tvTittle.setGravity(Gravity.RIGHT);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView tvTittle;
        ImageButton ibDelete;
        LinearLayout li;
        public MessageAdapterViewHolder(View itemView){
            super(itemView);
            tvTittle = itemView.findViewById(R.id.tvTittle);
            ibDelete = itemView.findViewById(R.id.delete);
            li = itemView.findViewById(R.id.liMessage);
            ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    messageDb.child(messages.get(getAdapterPosition()).getKey()).removeValue();
                }
            });
        }
    }
}
