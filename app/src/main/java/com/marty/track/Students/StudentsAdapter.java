package com.marty.track.Students;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marty.track.R;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.MyViewHolder> {

    private List<StudentsModel> studentsLists;
    private Context mCtx;

    public StudentsAdapter(List<StudentsModel> studentsLists, Context mCtx) {
        this.studentsLists = studentsLists;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        StudentsModel list = studentsLists.get(position);
        holder.admissionNumber.setText(list.getAdmissionNumber());
        holder.classNumber.setText(list.getClassNumber());
        holder.guardianAddress.setText(list.getAddress());
        holder.guardianName.setText(list.getGuardianName());
        holder.guardianNumber.setText(list.getGuardianPhone());
        holder.rollNumber.setText(list.getRollNumber());
        holder.sectionName.setText(list.getSectionName());
        holder.studentName.setText(list.getStudentFName() + list.getStudentLName());

    }

    @Override
    public int getItemCount() {
        return studentsLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView admissionNumber, classNumber, guardianAddress, guardianName, guardianNumber, rollNumber,
                sectionName, studentName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            admissionNumber = itemView.findViewById(R.id.admissionnumber);
            classNumber = itemView.findViewById(R.id.classnumber);
            guardianAddress = itemView.findViewById(R.id.guardianaddress);
            guardianName = itemView.findViewById(R.id.guardianname);
            guardianNumber = itemView.findViewById(R.id.guardianmobilenumber);
            rollNumber = itemView.findViewById(R.id.rollnumber);
            sectionName = itemView.findViewById(R.id.sectionname);
            studentName = itemView.findViewById(R.id.studentname);

        }


    }
}
