package com.example.todoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.model.Task;

import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;
    private OnTaskLongClickListener onTaskLongClickListener;

    private OnTaskClickListener onTaskClickListener;

    public interface OnTaskLongClickListener {
        void onTaskLongClick(Task task);
    }

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    public void setOnTaskLongClickListener(OnTaskLongClickListener listener) {
        this.onTaskLongClickListener = listener;
    }

    public void setOnTaskClickListener(OnTaskClickListener listener) {
        this.onTaskClickListener = listener;
    }

    public void setTasks(List<Task> tasks) {
        taskList = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        if (taskList != null) {
            Task currentTask = taskList.get(position);
            holder.tvTitle.setText(currentTask.getTitle());
            holder.tvDescription.setText(currentTask.getDescription());
            holder.tvDate.setText(currentTask.getDate());

            // Configura el listener para el evento de clic largo
            holder.itemView.setOnLongClickListener(view -> {
                if (onTaskLongClickListener != null) {
                    onTaskLongClickListener.onTaskLongClick(currentTask);
                    return true;
                }
                return false;
            });

            // Configura el listener para el evento de clic
            holder.itemView.setOnClickListener(view -> {
                if (onTaskClickListener != null) {
                    onTaskClickListener.onTaskClick(currentTask);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return taskList != null ? taskList.size() : 0;
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvDescription;
        private final TextView tvDate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
