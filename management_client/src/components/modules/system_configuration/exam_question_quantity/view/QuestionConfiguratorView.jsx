import { useState, useEffect } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import useMessages from '../../../../core/global/hooks/useMessages'
import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'
import { questionsQuantityHandler } from '../handlers/handler'

const QuestionsConfiguratorView = () => {
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  const [loading, setLoading] = useState(false)
  const [initialLoading, setInitialLoading] = useState(true)
  const [formValues, setFormValues] = useState({
    daiQuestionsQuantity: '',
    academicQuestionsQuantity: ''
  })
  const [placeholders, setPlaceholders] = useState({
    daiQuestionsQuantity: 'Cargando...',
    academicQuestionsQuantity: 'Cargando...'
  })

  useEffect(() => {
    const fetchInitialData = async () => {
      setLoading(true)
      try {
        const response = await questionsQuantityHandler.get()

        const academicQuantity = response.find(item => item.configName === 'ACADEMIC_EXAM_QUESTIONS_QUANTITY')?.configValue || '10'
        const daiQuantity = response.find(item => item.configName === 'DAI_EXAM_QUESTIONS_QUANTITY')?.configValue || '10'

        setFormValues({
          daiQuestionsQuantity: daiQuantity,
          academicQuestionsQuantity: academicQuantity
        })

        setPlaceholders({
          daiQuestionsQuantity: daiQuantity,
          academicQuestionsQuantity: academicQuantity
        })
      } catch (error) {
        setErrorMessage(error.message)
        setPlaceholders({
          daiQuestionsQuantity: '10',
          academicQuestionsQuantity: '10'
        })
      } finally {
        setLoading(false)
        setInitialLoading(false)
      }
    }

    fetchInitialData()
  }, [setErrorMessage])

  const handleChange = (e) => {
    const { name, value } = e.target
    setFormValues(prev => ({
      ...prev,
      [name]: value === '' ? '' : parseInt(value) || 0
    }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)

    try {
      await questionsQuantityHandler.update({
        daiQuestionsQuantity: formValues.daiQuestionsQuantity || placeholders.daiQuestionsQuantity,
        academicQuestionsQuantity: formValues.academicQuestionsQuantity || placeholders.academicQuestionsQuantity
      })

      setSuccessMessage('Configuración guardada correctamente')

      // Actualizar placeholders con los nuevos valores
      const newDaiValue = formValues.daiQuestionsQuantity || placeholders.daiQuestionsQuantity
      const newAcademicValue = formValues.academicQuestionsQuantity || placeholders.academicQuestionsQuantity

      setPlaceholders({
        daiQuestionsQuantity: newDaiValue,
        academicQuestionsQuantity: newAcademicValue
      })
    } catch (error) {
      setErrorMessage(error.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <SectionLayout title='Configurar Cantidad de Preguntas'>
      <div className='container percentages-configurator'>
        {renderMessages()}
        <h1>Configuración de cantidad de preguntas</h1>
        <p className='description'>Configura la cantidad de preguntas para cada tipo de examen.</p>
        <div className='percentages-configurator'>
          <form onSubmit={handleSubmit}>
            <div className='form-group'>
              <InputField
                field={{
                  name: 'daiQuestionsQuantity',
                  label: 'Preguntas Examen DAI',
                  type: 'number',
                  placeholder: placeholders.daiQuestionsQuantity,
                  required: true,
                  min: 1
                }}
                value={formValues.daiQuestionsQuantity}
                handleChange={handleChange}
                className='form-group'
              />
            </div>

            <div className='form-group'>
              <InputField
                field={{
                  name: 'academicQuestionsQuantity',
                  label: 'Preguntas Examen Académico',
                  type: 'number',
                  placeholder: placeholders.academicQuestionsQuantity,
                  required: true,
                  min: 1
                }}
                value={formValues.academicQuestionsQuantity}
                handleChange={handleChange}
                className='form-group'
              />
            </div>

            <Button
              onClick={handleSubmit}
              className='btn btn-primary w-full'
              disabled={loading || initialLoading}
            >
              {loading ? 'Guardando...' : 'Guardar Configuración'}
            </Button>
          </form>
        </div>
      </div>
    </SectionLayout>
  )
}

export default QuestionsConfiguratorView
