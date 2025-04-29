import axios from '../../../../../config/axiosConfig'

// eslint-disable-next-line no-unused-vars
let initValues = {
  email_contact: 'ejemplo@ctpcit.co.cr',
  email_notifications_contact: 'ejemplo@ctpcit.co.cr',
  whatsapp_contact: 88887777,
  office_contact: 88886666,
  instagram_contact: 'complejoeducativocit',
  facebook_contact: 'complejoeducativocit',
  email_password: 'password',
  api_whatsapp: 'apiKeyWhatsapp'
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
  const validationError = validateForm(formValues)

  return !validationError
}

const validateForm = (formValues) => {
  // eslint-disable-next-line camelcase
  const { email_contact, email_notifications_contact, whatsapp_contact, office_contact } = formValues

  // Validación Dominio específico
  const emailRegex = /^[a-zA-Z0-9._%+-]+@ctpcit\.co\.cr$/
  const isEmailValid = emailRegex.test(email_contact) && emailRegex.test(email_notifications_contact)

  // Validación de números de contacto (8 dígitos)
  const contactRegex = /^[0-9]{8}$/
  const isWhatsappValid = contactRegex.test(whatsapp_contact)
  const isOfficeValid = contactRegex.test(office_contact)

  return !(isEmailValid && isWhatsappValid && isOfficeValid)
}

const getNotificationSettingsUrl = `${import.meta.env.VITE_GET_CONFIGURATION_SETTINGS_ENDPOINT}`

// mappear los datos que llegan
const mapIncomingData = (data) => {
  const configMapping = {
    EMAIL_CONTACT: 'email_contact',
    EMAIL_NOTIFICATION_CONTACT: 'email_notifications_contact',
    WHATSAPP_CONTACT: 'whatsapp_contact',
    OFFICE_CONTACT: 'office_contact',
    INSTAGRAM_CONTACT: 'instagram_contact',
    FACEBOOK_CONTACT: 'facebook_contact',
    EMAIL_PASSWORD: 'email_password',
    WHATSAPP_API_KEY: 'whatsapp_api_key'
  }

  return data.reduce((acc, { configName, configValue }) => {
    const key = configMapping[configName]
    if (key) {
      acc[key] = configValue
    }
    return acc
  }, {})
}

export const getCurrentSettings = async () => {
  try {
    const response = await axios.get(getNotificationSettingsUrl, { timeout: 5000 })
    const data = mapIncomingData(response.data)
    initValues = { ...data }
    return data
  } catch (error) {
    throw new Error('Error al cargar las configuraciones ya establecidas.')
  }
}

const saveNotificationSettingsUrl = `${import.meta.env.VITE_UPDATE_NOTIFICATION_SETTINGS_ENDPOINT}`

export const updateNotificationSettings = async (formValues, setFormValues, setLoading, setSuccessMessage, setErrorMessage) => {
  setLoading(true)

  try {
    const dataToSend = {
      emailContact: formValues.email_contact,
      emailNotificationsContact: formValues.email_notifications_contact,
      whatsappContact: formValues.whatsapp_contact,
      officeContact: formValues.office_contact,
      instagramContact: formValues.instagram_contact,
      facebookContact: formValues.facebook_contact,
      emailPassword: formValues.email_password,
      whatsappApiKey: formValues.whatsapp_api_key
    }
    await axios.put(saveNotificationSettingsUrl, dataToSend)
    setSuccessMessage('Configuraciones actualizadas correctamente.')
    const updatedData = await getCurrentSettings()
    setFormValues(updatedData)
  } catch (error) {
    setErrorMessage(getErrorMessage(error))
  } finally {
    setLoading(false)
  }
}
