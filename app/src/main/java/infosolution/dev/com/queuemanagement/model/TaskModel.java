package infosolution.dev.com.queuemanagement.model;

/**
 * Created by amit on 3/22/2018.
 */

public class TaskModel {

    String Task;
    String AssignAt;
    String Deadline;
    String TaskId;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    String Status;

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        Task = task;
    }

    public String getAssignAt() {
        return AssignAt;
    }

    public void setAssignAt(String assignAt) {
        AssignAt = assignAt;
    }

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String deadline) {
        Deadline = deadline;
    }

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }

    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String staffId) {
        StaffId = staffId;
    }

    String StaffId;
}
