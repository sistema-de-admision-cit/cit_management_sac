import { useState, useEffect } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import NotificacionsForm from '../molecules/NotificacionsForm'
import useMessages from '../../../../core/global/hooks/useMessages'
import { getCurrentPercentages, updateExamPercentages } from '../helpers/handlers'

const PercentagesConfiguratorView = () => {
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  const [loading, setLoading] = useState(false)
  const [formValues, setFormValues] = useState({
    queryEmail: "ejemplo@ctpcit.com",
    notificationsEmail: "ejemplo@ctpcit.com",
    whatsapp: 88887777,
    contactPhoneNumber: 88886666,
    instagram: "complejoeducativocit",
    facebook: 'complejoeducativocit'
  })

  useEffect(() => {
    const fetchInitialData = async () => {
      setLoading(true)
      try {
        const data = await getCurrentPercentages()
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
      <div className='container percentages-configurator'>
        {renderMessages()}
        <h1>Configuración de notificaciones</h1>
        <p className='description'>Configurar los elementos necesarios para las notificaciones en el sistema.</p>
        <div className='notifications-configurator'>
        <NotificacionsForm
            formValues={formValues}
            handleChange={handleChange}
            onSave={() => updateExamPercentages(formValues, setFormValues, setLoading, setSuccessMessage, setErrorMessage)}
            loading={loading}
          />
        </div>
      </div>
    </SectionLayout>
  )
}

export default PercentagesConfiguratorView
