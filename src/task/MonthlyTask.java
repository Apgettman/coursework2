package task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MonthlyTask extends Task {

    public MonthlyTask(String title, String description, LocalDateTime taskDateTime, TaskType taskType) {
        super(title, description, taskDateTime, taskType);
    }

    public boolean appearsIn(LocalDate localDate) {
        LocalDate taskDate = this.getTaskDateTime().toLocalDate();
        return localDate.equals(taskDate) || (localDate.isAfter(taskDate) &&
                localDate.getDayOfMonth() == localDate.getDayOfMonth());
    }

    public Repeatability getRepeatabilityType() {
        return Repeatability.MONTHLY;
    }
}
