package cr.co.ctpcit.citsacbackend.data.enums;

/**
 * This ENUM match with the DataSource knownThrough enum in tbl_enrollments, and it is used to
 * determine how the student knew about the program, in this way: SM: Social Media OH: Open House
 * FD: Friends FM: Family OT: Other
 */
public enum KnownThrough {
  SM, OH, FD, FM, OT
}
