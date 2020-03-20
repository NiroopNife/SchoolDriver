package com.marty.track.StudentsPickAndDrop.StudentsDrop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.marty.track.R;

import java.util.List;

public class StudentsDropAdapter extends RecyclerView.Adapter<StudentsDropAdapter.MyViewHolder> {

    private List<StudentsDropModel> dropStudentsList;
    private Context mCtx;

    public StudentsDropAdapter(List<StudentsDropModel> dropStudentsList, Context mCtx) {
        this.dropStudentsList = dropStudentsList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_drop_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final StudentsDropModel list = dropStudentsList.get(position);
        holder.studentName.setText(list.getStudentFName() + list.getStudentLName());
        holder.className.setText(list.getClassNumber());
        holder.sectionName.setText(list.getSectionName());
        holder.pdLocation.setText(list.getPdLocation());
        holder.makeACall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("tel: "+list.getAp_guardian_phone()));
                mCtx.startActivity(intent);
            }
        });
        holder.sendStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.sendStatus.setText("DROPPED");
            }
        });
        holder.studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, GetStudentRoute.class);
                intent.putExtra("latitude", list.getPdloc_latitude());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("longitude", list.getPdloc_longitude());
                intent.putExtra("location", list.getPdLocation());
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dropStudentsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView studentCard;
        Button sendStatus;
        ImageButton makeACall;
        TextView studentName, className, sectionName, pdLocation;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentCard = itemView.findViewById(R.id.student_card);
            studentName = itemView.findViewById(R.id.dstudentname);
            className = itemView.findViewById(R.id.dclassnumber);
            sectionName = itemView.findViewById(R.id.dsectionname);
            pdLocation = itemView.findViewById(R.id.pd_location);
            makeACall = itemView.findViewById(R.id.call);
            sendStatus = itemView.findViewById(R.id.status);

            sendStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendStatus.setText("Dropped");
                }
            });
        }

    }
}
