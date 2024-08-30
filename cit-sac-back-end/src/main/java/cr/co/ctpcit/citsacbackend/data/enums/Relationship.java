package cr.co.ctpcit.citsacbackend.data.enums;

/**
 * This ENUM match with the DataSource Relationship enum in tbl_parentsguardians,
 * and it is used to determine the relation between the student and its guardian, in this way:
 * M: Mother
 * F: Father
 * G: Guardian
 */
public enum Relationship {
    M, F, G
}
