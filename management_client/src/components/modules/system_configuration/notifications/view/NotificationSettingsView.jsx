import { useState, useEffect } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import NotificacionsForm from '../molecules/NotificacionsForm'
import SendNotificacions from '../molecules/SendNotifications'
import useMessages from '../../../../core/global/hooks/useMessages'
import { getCurrentSettings, updateNotificationSettings } from '../helpers/handlers'

const NotificationsSettingsView = () => {
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  const [loading, setLoading] = useState(false)

  const [formValues, setFormValues] = useState({
    email_contact: '',
    email_notifications_contact: '',
    whatsapp_contact: '',
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
    <SectionLayout title='Configurar Notificaciones'>
      <div className='container'>
        {renderMessages()}
        <h1>Configurar Notificaciones</h1>
        <p className='description'>Configurar los elementos para las notificaciones en el sistema.</p>
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
