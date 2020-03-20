package com.marty.track.StudentsPickAndDrop.StudentsPick;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.marty.track.R;
import com.marty.track.Students.StudentsAdapter;
import com.marty.track.StudentsPickAndDrop.StudentsDrop.GetStudentRoute;

import java.util.List;

public class StudentsPickAdapter extends RecyclerView.Adapter<StudentsPickAdapter.MyViewHolder> {

    private final ClickListener listener;
    private List<StudentsPickModel> pickStudentsList;
    private Context mCtx;

    public StudentsPickAdapter(List<StudentsPickModel> pickStudentsList, Context mCtx, ClickListener listener) {
        this.pickStudentsList = pickStudentsList;
        this.mCtx = mCtx;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_pick_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final StudentsPickModel list = pickStudentsList.get(position);
        holder.studentName.setText(list.getStudentFName() + list.getStudentLName());
        holder.className.setText(list.getClassNumber());
        holder.sectionName.setText(list.getSectionName());
        holder.pdLocation.setText(list.getPdLocation());
        holder.number.setText(list.getAp_guardian_phone());
        holder.makeACall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("tel: "+list.getAp_guardian_phone()));
                mCtx.startActivity(intent);
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
        holder.sendStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int status = (int) v.getTag();
                if (status == 1){
                    holder.sendStatus.setText("PICKED");
                } else {
                    holder.sendStatus.setText("PICK");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pickStudentsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView studentCard;
        Button sendStatus;
        ImageButton makeACall;
        TextView studentName, className, sectionName, pdLocation, number;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            studentCard = itemView.findViewById(R.id.student_card);
            studentName = itemView.findViewById(R.id.studentname);
            className = itemView.findViewById(R.id.classnumber);
            sectionName = itemView.findViewById(R.id.sectionname);
            pdLocation = itemView.findViewById(R.id.pd_location);
            makeACall = itemView.findViewById(R.id.call);
            sendStatus = itemView.findViewById(R.id.status);
            sendStatus.setTag(1);
            sendStatus.setText("PICK");
            number = itemView.findViewById(R.id.number);

        }


    }
}
