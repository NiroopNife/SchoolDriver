package com.marty.track.StudentsPickAndDrop.StudentsDrop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marty.track.R;
import com.marty.track.StudentsPickAndDrop.StudentsPick.StudentsPickModel;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        StudentsDropModel list = dropStudentsList.get(position);
        holder.studentName.setText(list.getStudentFName() + list.getStudentLName());
        holder.className.setText(list.getClassNumber());
        holder.sectionName.setText(list.getSectionName());
        holder.rollNumber.setText(list.getRollNumber());
    }

    @Override
    public int getItemCount() {
        return dropStudentsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView studentName, className, sectionName, rollNumber;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.dstudentname);
            className = itemView.findViewById(R.id.dclassnumber);
            sectionName = itemView.findViewById(R.id.dsectionname);
            rollNumber = itemView.findViewById(R.id.drollnumber);
        }

    }
}
