package task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Scanner;

import static task.TaskType.PERSONAL;

public class Main {

    private static final Schedule SCHEDULE = new Schedule();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH.mm");

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        SCHEDULE.addTask(new SingleTask("Single-test", "test", LocalDateTime.now(), PERSONAL));
        SCHEDULE.addTask(new DailyTask("Daily-test", "test", LocalDateTime.now().plusHours(3), PERSONAL));
        SCHEDULE.addTask(new WeeklyTask("Weekly-test", "test", LocalDateTime.now().plusHours(4), PERSONAL));
        SCHEDULE.addTask(new MonthlyTask("Monthly-test", "test", LocalDateTime.now().plusHours(5), PERSONAL));
        SCHEDULE.addTask(new YearlyTask("Yearly-test", "test", LocalDateTime.now().plusHours(6), PERSONAL));
        removeTasks(scanner);
        addTask(scanner);
        printTaskForDate(scanner);
    }


    private static Task addTask(Scanner scanner) {
        String title = readString("Введите название задачи: ", scanner);
        String description = readString("Введите описание задачи: ", scanner);
        LocalDateTime taskDate = readDateTime(scanner);
        TaskType taskType = readType(scanner);
        Repeatability repeatability = readRepeatability(scanner);

        switch (repeatability) {
            case SINGLE:
                return new SingleTask(title, description, taskDate, taskType);
            case DAILY:
                return new DailyTask(title, description, taskDate, taskType);
            case WEEKLY:
                return new WeeklyTask(title, description, taskDate, taskType);
            case MONTHLY:
                return new MonthlyTask(title, description, taskDate, taskType);
            case YEARLY:
                return new YearlyTask(title, description, taskDate, taskType);
        }
        return null;
    }


    private static TaskType readType(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Выберите тип задачи: ");
                for (TaskType taskType : TaskType.values()) {
                    System.out.println(taskType.ordinal() + ". " + localizeType(taskType));
                }
                System.out.println("Введите тип: ");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return TaskType.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Введен не верный номер типа задачи");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найден");
            }
        }
    }

    private static Repeatability readRepeatability(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Выберите тип повторяемости задачи: ");
                for (Repeatability repeatability : Repeatability.values()) {
                    System.out.println(repeatability.ordinal() + ". " + localizeRepeatabilityType(repeatability));
                }
                System.out.println("Введите тип: ");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return Repeatability.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Введен не верный номер типа задачи");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найден");
            }
        }
    }

    private static LocalDateTime readDateTime(Scanner scanner) {
        LocalDate localDate = readDate(scanner);
        LocalTime localTime = readTime(scanner);
        return localDate.atTime(localTime);
    }

    private static String readString(String message, Scanner scanner) {
        while (true) {
            System.out.print(message);
            String readString = scanner.nextLine();
            if (readString == null || readString.isBlank()) {
                System.out.println("Введено пустое значение");
            } else {
                return readString;
            }
        }
    }

    private static void removeTasks(Scanner scanner) {
        System.out.println("Все задачи: ");
        for (Task task : SCHEDULE.getAllTasks()) {
            System.out.printf("%d, %s {%s}(%s)%n", task.getId(), task.getTitle(),
                    localizeType(task.getTaskType()), localizeRepeatabilityType(task.getRepeatabilityType()));
        }
        while (true) {
            try {
                System.out.print("Выберите задачу для удаления: ");
                String idLine = scanner.nextLine();
                int id = Integer.parseInt(idLine);
                SCHEDULE.removeTask(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введен не верный id задачи");
            } catch (TaskNotFoundException e) {
                System.out.println("Задача для удаления не найдена");
            }
        }
    }

    private static void printTaskForDate(Scanner scanner) {
        LocalDate localDate = readDate(scanner);
        Collection<Task> tasksForDate = SCHEDULE.getTaskForDate(localDate);
        System.out.println("Задачи на " + localDate.format(DATE_FORMAT));
        for (Task task : tasksForDate) {
            System.out.printf("{%s}%s: %s (%s)%n", localizeType(task.getTaskType()),
                    task.getTitle(), task.getTaskDateTime().format(TIME_FORMAT), task.getDescription());

        }
    }

    private static LocalDate readDate(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите дату: ");
                String dateline = scanner.nextLine();
                return LocalDate.parse(dateline, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Введена дата в неверном формате");
            }
        }
    }

    private static LocalTime readTime(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите время задачи: ");
                String dateline = scanner.nextLine();
                return LocalTime.parse(dateline, TIME_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Введено время в неверном формате");
            }
        }
    }


    private static TaskType localizeType(TaskType taskType) {
        switch (taskType) {
            case WORK:
            case PERSONAL:
        }
        return taskType;
    }

    private static Repeatability localizeRepeatabilityType(Repeatability repeatability) {
        switch (repeatability) {
            case SINGLE:
            case DAILY:
            case WEEKLY:
            case MONTHLY:
            case YEARLY:
        }
        return repeatability;
    }
}
