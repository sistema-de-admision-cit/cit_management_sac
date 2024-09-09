import { useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import PercentagesForm from '../molecules/PercentagesForm'
import useMessages from '../../../../core/global/hooks/useMessages'

const PercentagesConfiguratorView = () => {
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  const [loading, setLoading] = useState(false)
  // TODO: Fetch data from API
  const [formValues, setFormValues] = useState({
    academicExam: 0,
    daiExam: 0,
    englishExam: 0
  })

  const handleChange = (field, value) => {
    setFormValues({
      ...formValues,
      [field]: value
    })
  }

  const handleSave = async () => {
    setLoading(true)
    await new Promise((resolve) => setTimeout(resolve, 1000))
    try {
      setSuccessMessage('Porcentajes guardados correctamente.')
    } catch (error) {
      setErrorMessage('Error al guardar los porcentajes.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <SectionLayout title='Configurar Porcentajes'>
      <div className='container percentages-configurator'>
        {renderMessages()}
        <h1>Configuración de porcentajes</h1>
        <p className='description'>Configura los porcentajes de cada examen para el cálculo de la nota final.</p>
        <div className='percentages-configurator'>
          <PercentagesForm
            formValues={formValues}
            handleChange={handleChange}
            onSave={handleSave}
            loading={loading}
          />
        </div>
      </div>
    </SectionLayout>
  )
}

export default PercentagesConfiguratorView
