package cr.co.ctpcit.citsacbackend.data.enums;

/**
 * This ENUM match with the DataSource Configurations enum in tbl_system_config, and it is used to
 * determine the type of configuration that the system has, in this way: EMAIL_CONTACT: Email
 * contact of the institution. WHATSAPP_CONTACT: WhatsApp contact of the institution.
 * OFFICE_CONTACT: Office contact of the institution. FACEBOOK_CONTACT: Facebook contact of the
 * institution. INSTAGRAM_CONTACT: Instagram contact of the institution. PREV_GRADES_WEIGHT: Weight
 * of the previous grades in the final grade calculation. ACADEMIC_WEIGHT: Weight of the academic
 * grade in the final grade calculation. ENGLISH_WEIGHT: Weight of the English grade in the final
 * grade calculation.
 */
public enum Configurations {
  EMAIL_CONTACT,
  EMAIL_NOTIFICATION_CONTACT,
  WHATSAPP_CONTACT,
  OFFICE_CONTACT,
  FACEBOOK_CONTACT,
  INSTAGRAM_CONTACT,
  PREV_GRADES_WEIGHT,
  ACADEMIC_WEIGHT,
  ENGLISH_WEIGHT,
  ACADEMIC_EXAM_QUESTIONS_QUANTITY,
  DAI_EXAM_QUESTIONS_QUANTITY, EMAIL_PASSWORD, WHATSAPP_API_KEY;

  public static Configurations fromString(String value) {
    for (Configurations config : Configurations.values()) {
      if (config.name().equalsIgnoreCase(value)) {
        return config;
      }
    }
    return null;
  }

  public static boolean isSensible(Configurations configurations) {
    switch (configurations) {
      case EMAIL_PASSWORD, WHATSAPP_API_KEY -> {
        return true;
      }
      default -> {
        return false;
      }
    }
  }
}
