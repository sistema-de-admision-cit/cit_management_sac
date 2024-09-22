package cr.co.ctpcit.citsacbackend.data.enums;

/**
 * This ENUM match with the DataSource IdType enum in tbl_students and tbl_parentsguardians, and it
 * is used to determine the type of ID that the student or its guardian has, in this way: CC:
 * National ID Document (cédula) DI: DIMEX PA: Passport
 */
public enum IdType {
  CC, DI, PA
}
