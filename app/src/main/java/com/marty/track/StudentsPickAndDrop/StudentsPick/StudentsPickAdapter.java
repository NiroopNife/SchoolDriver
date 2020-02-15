package com.marty.track.StudentsPickAndDrop.StudentsPick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marty.track.R;
import com.marty.track.Students.StudentsAdapter;

import java.util.List;

public class StudentsPickAdapter extends RecyclerView.Adapter<StudentsPickAdapter.MyViewHolder> {

    private List<StudentsPickModel> pickStudentsList;
    private Context mCtx;

    public StudentsPickAdapter(List<StudentsPickModel> pickStudentsList, Context mCtx) {
        this.pickStudentsList = pickStudentsList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_pick_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        StudentsPickModel list = pickStudentsList.get(position);
        holder.studentName.setText(list.getStudentFName() + list.getStudentLName());
        holder.className.setText(list.getClassNumber());
        holder.sectionName.setText(list.getSectionName());
        holder.rollNumber.setText(list.getRollNumber());

    }

    @Override
    public int getItemCount() {
        return pickStudentsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView studentName, className, sectionName, rollNumber;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName = itemView.findViewById(R.id.studentname);
            className = itemView.findViewById(R.id.classnumber);
            sectionName = itemView.findViewById(R.id.sectionname);
            rollNumber = itemView.findViewById(R.id.rollnumber);
        }
    }
}
