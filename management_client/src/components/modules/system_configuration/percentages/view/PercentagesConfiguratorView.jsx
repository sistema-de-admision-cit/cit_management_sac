import { useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import PercentagesForm from '../molecules/PercentagesForm'

const PercentagesConfiguratorView = () => {
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

  return (
    <SectionLayout title='Configurar Porcentajes'>
      <div className='container percentages-configurator'>
        <h1>Configuración de porcentajes</h1>
        <p className='description'>Configura los porcentajes de cada examen para el cálculo de la nota final.</p>
        <div className='percentages-configurator'>
          <PercentagesForm formValues={formValues} handleChange={handleChange} />
        </div>
      </div>
    </SectionLayout>
  )
}

export default PercentagesConfiguratorView
