import { useState, useEffect } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import NotificacionsForm from '../molecules/NotificacionsForm'
import useMessages from '../../../../core/global/hooks/useMessages'
import { getCurrentSettings, updateNotificationSettings } from '../helpers/handlers'

const NotificationsSettingsView = () => {
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  const [loading, setLoading] = useState(false)

  const [formValues, setFormValues] = useState({
    email_contact: '',
    email_notifications_contact: '',
    email_password: '',
    whatsapp_contact: '',
    whatsapp_api_key: '',
    office_contact: '',
    instagram_contact: '',
    facebook_contact: ''
  })

  useEffect(() => {
    const fetchInitialData = async () => {
      setLoading(true)
      try {
        const data = await getCurrentSettings()
        setFormValues(data)
      } catch (error) {
        setErrorMessage(error.message)
      } finally {
        setLoading(false)
      }
    }

    fetchInitialData()
  }, [])

  const handleChange = (field, value) => {
    setFormValues({
      ...formValues,
      [field]: value
    })
  }

  return (
    <SectionLayout title='Configurar Porcentajes'>
      <div className='container'>
        {renderMessages()}
        <h1>Configuración de Contactos</h1>
        <div className='notifications-configurator'>
          <NotificacionsForm
            formValues={formValues}
            handleChange={handleChange}
            onSave={() => updateNotificationSettings(formValues, setFormValues, setLoading, setSuccessMessage, setErrorMessage)}
            loading={loading}
          />
        </div>
      </div>

    </SectionLayout>
  )
}

export default NotificationsSettingsView
