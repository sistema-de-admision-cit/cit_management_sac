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

    if (value === '') {
      setFormValues(prev => ({
        ...prev,
        [name]: ''
      }))
      return
    }

    const numericValue = parseInt(value)

    if (!isNaN(numericValue) && numericValue >= 0 && numericValue <= 100) {
      setFormValues(prev => ({
        ...prev,
        [name]: numericValue
      }))
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault()

    // Validar que los valores sean números entre 0 y 100
    const daiValid = formValues.daiQuestionsQuantity !== '' &&
      !isNaN(formValues.daiQuestionsQuantity) &&
      formValues.daiQuestionsQuantity >= 0 &&
      formValues.daiQuestionsQuantity <= 100

    const academicValid = formValues.academicQuestionsQuantity !== '' &&
      !isNaN(formValues.academicQuestionsQuantity) &&
      formValues.academicQuestionsQuantity >= 0 &&
      formValues.academicQuestionsQuantity <= 100

    if (!daiValid || !academicValid) {
      setErrorMessage('Las cantidades deben ser números entre 1 y 100')
      return
    }

    setLoading(true)

    try {
      await questionsQuantityHandler.update({
        daiQuestionsQuantity: formValues.daiQuestionsQuantity,
        academicQuestionsQuantity: formValues.academicQuestionsQuantity
      })

      setSuccessMessage('Configuración guardada correctamente')
      setPlaceholders({
        daiQuestionsQuantity: formValues.daiQuestionsQuantity,
        academicQuestionsQuantity: formValues.academicQuestionsQuantity
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
        <p className='description'>Configura la cantidad de preguntas para cada tipo de examen (0-100).</p>
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
                  max: 100,
                  min: 0
                }}
                value={formValues.daiQuestionsQuantity === 0 ? 0 : formValues.daiQuestionsQuantity || ''}
                handleChange={handleChange}
                className='form-group'
              />
              <small className='form-text text-muted'>Debe ser un número entre 1 y 100</small>
            </div>

            <div className='form-group'>
              <InputField
                field={{
                  name: 'academicQuestionsQuantity',
                  label: 'Preguntas Examen Académico',
                  type: 'number',
                  placeholder: placeholders.academicQuestionsQuantity,
                  required: true,
                  max: 100,
                  min: 0
                }}
                value={formValues.academicQuestionsQuantity === 0 ? 0 : formValues.academicQuestionsQuantity || ''}
                handleChange={handleChange}
                className='form-group'
              />
              <small className='form-text text-muted'>Debe ser un número entre 0 y 100</small>
            </div>

            <Button
              type='submit'
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
