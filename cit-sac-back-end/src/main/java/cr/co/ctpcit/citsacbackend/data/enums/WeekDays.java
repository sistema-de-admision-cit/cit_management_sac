package cr.co.ctpcit.citsacbackend.data.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * This ENUM match with the DataSource WeekDays enum in tbl_examdays, and it is used to determine
 * the days of the week that the exam is going to be applied.
 */
public enum WeekDays {
  M, K, W, T, F, S, SS;

  public static List<WeekDays> getWeekDays(List<String> strings) {
    List<WeekDays> weekDays = new ArrayList<>();
    for (String string : strings) {
      weekDays.add(WeekDays.valueOf(string));
    }
    return weekDays;
  }
}
