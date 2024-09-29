import axios from 'axios'

let initValues = {
  email_contact: "ejemplo@ctpcit.co.cr",
  email_notification_contact: "ejemplo@ctpcit.co.cr",
  whatsapp_contact: 88887777,
  office_contact: 88886666,
  instagram_contact: "complejoeducativocit",
  facebook_contact: "complejoeducativocit"
}

const getErrorMessage = (error) => {
  if (error.response) {
    if (error.response.status === 400) {
      return 'Configuraciones no validas inválidos.'
    } else if (error.response.status === 500) {
      return 'Error al guardar los cambios.'
    } else if (error.response.status === 404) {
      return 'Parece que no se pudo resolver la solicitud. Inténtalo de nuevo.'
    }
  }
  return 'Error al cargar las configuraciones prestablecidas.'
}

/**
 *
 * @param {JSON Object} formValues
 * @param {JSON Object} initValues to compare with formValues (if they are the same, the save button will be disabled)
 * @returns
 */
export const getSaveButtonState = (formValues) => {
  const isSame = Object.keys(formValues).every(key => formValues[key] === initValues[key])

  const validationError = validateForm(formValues);

  return !isSame && !validationError;
};

const validateForm = (formValues) => {
  const { email_contact, email_notification_contact, whatsapp_contact, office_contact } = formValues;

  // Validación Dominio específico
  const emailRegex = /^[a-zA-Z0-9._%+-]+@ctpcit\.co\.cr$/;
  const isEmailValid = emailRegex.test(email_contact) && emailRegex.test(email_notification_contact);

  // Validación de números de contacto (8 dígitos)
  const contactRegex = /^[0-9]{8}$/;
  const isWhatsappValid = contactRegex.test(whatsapp_contact);
  const isOfficeValid = contactRegex.test(office_contact);

  return !(isEmailValid && isWhatsappValid && isOfficeValid);
};

const getNotificationSettingsUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_GET_EXAM_PERCENTAGES_ENDPOINT}`

// mappear los datos que llegan
const mapIncomingData = (data) => {
  return {
    email_contact: data.email_contact,
    email_notification_contact: data.email_notification_contact,
    whatsapp_contact: data.whatsapp_contact,
    office_contact: data.office_contact,
    instagram_contact: data.instagram_contact || "", // Asume que podrían llegar vacíos
    facebook_contact: data.facebook_contact || ""
  };
}

// mappear los datos antes de enviarlos
const mapOutgoingData = (data) => {
  return {
    email_contact: data.email_contact,
    email_notification_contact: data.email_notification_contact,
    whatsapp_contact: data.whatsapp_contact,
    office_contact: data.office_contact,
    instagram_contact: data.instagram_contact,
    facebook_contact: data.facebook_contact
  };
}

export const getCurrentSettings = async () => {
  try {
    const response = await axios.get(getNotificationSettingsUrl, { timeout: 5000 });
    const data = mapIncomingData(response.data);

    initValues = { ...data };
    return data;
  } catch (error) {
    throw new Error('Error al cargar las configuraciones ya establecidas.');
  }
}

const saveNotificationSettingsUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_UPDATE_NOTIFICATION_SETTINGS_ENDPOINT}`

export const updateNotificationSettings = async (formValues, setFormValues, setLoading, setSuccessMessage, setErrorMessage) => {
  setLoading(true);

  try {
    const dataToSend = mapOutgoingData(formValues);
    const response = await axios.put(`${saveNotificationSettingsUrl}?${new URLSearchParams(dataToSend)}`, {}, { timeout: 5000 });
    setSuccessMessage('Configuraciones actualizadas correctamente.');

    const data = mapIncomingData(response.data);
    initValues = { ...data };
    setFormValues(data);
  } catch (error) {
    setErrorMessage(getErrorMessage(error));
  } finally {
    setLoading(false);
  }
}
