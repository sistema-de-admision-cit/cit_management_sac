package cr.co.ctpcit.citsacbackend.data.enums;

/**
 * This ENUM match with the DataSource status enum in tbl_enrollments, and it is used to determine
 * the status of the student along the process, in this way: P: Pending E: Eligible I: Ineligible A:
 * Approved R: Rejected Eligibility is determined by the student's academic history and
 * administrative requirements.
 */
public enum ProcessStatus {
  P, E, I, A, R
}
