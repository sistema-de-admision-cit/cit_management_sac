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
    email_contact: "ejemplo@ctpcit.co.cr",
    email_notification_contact: "ejemplo@ctpcit.co.cr",
    whatsapp_contact: 88887777,
    office_contact: 88886666,
    instagram_contact: "complejoeducativocit",
    facebook_contact: "complejoeducativocit"
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
        <h1>Configuración de notificaciones</h1>
        <p className='description'>Configurar los elementos necesarios para las notificaciones en el sistema.</p>
        <div className='notifications-configurator'>
        <p className='description'><b>Puntos de control.</b></p>
        <NotificacionsForm
            formValues={formValues}
            handleChange={handleChange}
            onSave={() => updateNotificationSettings(formValues, setFormValues, setLoading, setSuccessMessage, setErrorMessage)}
            loading={loading}
          />
        </div>
        <div className='notifications-configurator'>
        <p className='description'><b>Notificaciones.</b></p>
        <SendNotificacions
          handleChange={handleChange}
          
          loading={loading}
        />
        </div>
      </div>
      
    </SectionLayout>
  )
}

export default NotificationsSettingsView
