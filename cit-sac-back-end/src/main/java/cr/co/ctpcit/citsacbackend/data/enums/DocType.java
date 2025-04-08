package cr.co.ctpcit.citsacbackend.data.enums;

/**
 * This ENUM match with the DataSource DocType enum in tbl_documents, and it is used to determine
 * the type of document that the student has, in this way: AC: Accommodations Certificate OT: Other
 */
public enum DocType {
  AC, OT;

  public static DocType fromString(String value) {
    for (DocType docType : DocType.values()) {
      if (docType.name().equalsIgnoreCase(value)) {
        return docType;
      }
    }
    return null;
  }
}
