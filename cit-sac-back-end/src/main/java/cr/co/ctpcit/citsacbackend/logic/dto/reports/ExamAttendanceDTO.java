package cr.co.ctpcit.citsacbackend.logic.dto.reports;

import java.util.Date;

public class ExamAttendanceDTO {
  private Long studentId;
  private String fullName;
  private Date examDate;
  private String attendanceStatus; // "attended" o "not attended"

  // Constructor
  public ExamAttendanceDTO(Long studentId, String fullName, Date examDate,
      String attendanceStatus) {
    this.studentId = studentId;
    this.fullName = fullName;
    this.examDate = examDate;
    this.attendanceStatus = attendanceStatus;
  }

  // Getters y setters
  public Long getStudentId() {
    return studentId;
  }

  public void setStudentId(Long studentId) {
    this.studentId = studentId;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Date getExamDate() {
    return examDate;
  }

  public void setExamDate(Date examDate) {
    this.examDate = examDate;
  }

  public String getAttendanceStatus() {
    return attendanceStatus;
  }

  public void setAttendanceStatus(String attendanceStatus) {
    this.attendanceStatus = attendanceStatus;
  }
}
