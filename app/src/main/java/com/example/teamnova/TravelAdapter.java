package com.example.teamnova;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {

    private ArrayList<TravelData> travelData = null;

    public TravelAdapter(ArrayList<TravelData> tdata) { travelData = tdata;}

    interface OnItemClickListener{
        void onItemClick(View v, int tpos);
        void onEditClick(View v, int tpos); //수정
        void onDeleteClick(View v, int tpos);//삭제
    }
    // 커스텀 리스너 인터페이스 정의

    private TravelAdapter.OnItemClickListener tListener = null;
    //전달된 객체를 저장할 변수 dListener 추가함.

    public void setOnItemClickListener(TravelAdapter.OnItemClickListener listener){
        this.tListener = listener;
    }
    //리스너 객체를 전달하는 매서드

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.traveldiaryitem, parent,false);
        TravelAdapter.ViewHolder tvh = new TravelAdapter.ViewHolder(view);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TravelData item = travelData.get(position);

        holder.date.setText(item.getDate());
        holder.time.setText(item.getTime());
        holder.spot.setText(item.getSpot());
        holder.content.setText(item.getContent());
        holder.content.setEllipsize(TextUtils.TruncateAt.END);
        holder.address.setText(item.getAddress());

        String imageUrl = item.getImage();
        //Log.e("imageUrl",imageUrl);
        Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return travelData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView time;
        TextView spot;
        TextView content;
        TextView address;
        ImageView image;
        Button edit_travel, delete_travel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            spot = itemView.findViewById(R.id.spot);
            content = itemView.findViewById(R.id.content);
            address = itemView.findViewById(R.id.address);
            image = itemView.findViewById(R.id.image);

            edit_travel = itemView.findViewById(R.id.edit_travel);
            delete_travel = itemView.findViewById(R.id.delete_travel);

            itemView.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    int tpos = getAdapterPosition ();
                    if (tpos!=RecyclerView.NO_POSITION){
                        //리스너 객체의 호출
                        if (tListener!=null){
                            tListener.onItemClick (view,tpos);

                        }
                    }
                }
            });
            // 아이템 클릭 이벤트 핸들러 메서드에서 리스너 객체 (onitemclick) 매서드 호출

            edit_travel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tpos = getAdapterPosition();
                    if(tpos != RecyclerView.NO_POSITION){
                        if(tListener != null){
                            tListener.onEditClick(view, tpos);
                        }
                    }
                }
            });

            delete_travel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tpos = getAdapterPosition();
                    if(tpos != RecyclerView.NO_POSITION){
                        if(tListener != null){
                            tListener.onDeleteClick(view, tpos);
                        }
                    }
                }
            });

        }
    }
}
