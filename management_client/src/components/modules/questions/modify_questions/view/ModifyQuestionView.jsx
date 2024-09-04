import { useEffect, useState } from 'react'
import FindQuestion from '../../base/molecules/FindQuestion'
import ModifyQuestionForm from '../organisms/ModifyQuestionForm'
import '../../../../../assets/styles/global/view.css'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'

const ModifyQuestionView = () => {
  const [questionData, setQuestionData] = useState(null)
  const [incomingData, setIncomingData] = useState(null)

  // Efecto para manejar la actualización de questionData
  useEffect(() => {
    if (incomingData) {
      setQuestionData(null) // Limpiar los datos actuales
      const timer = setTimeout(() => {
        setQuestionData(incomingData) // Luego, establecer los nuevos datos
      }, 0)

      // Limpieza del efecto
      return () => clearTimeout(timer)
    }
  }, [incomingData])

  // Maneja la pregunta encontrada
  const handleQuestionFound = (data) => {
    setIncomingData(data) // Establece los datos para que useEffect los maneje
  }

  // si el usuario decide buscar otra pregunta, limpiar los datos actuales (esto arregla el error de que no se podia eliminar una pregunta y volve a buscar la misma)
  useEffect(() => {
    setIncomingData(null)
  }, [questionData])

  return (
    <SectionLayout title='Modificar pregunta'>
      <div className='modify-question-container'>
        <div className='search-section'>
          {!questionData &&
            <FindQuestion
              onQuestionFound={handleQuestionFound}
              lookingFor='modify'
            />}
        </div>
        <div className='form-section'>
          {questionData && (
            <ModifyQuestionForm
              questionData={questionData}
              setQuestionData={setQuestionData}
            />
          )}
        </div>
      </div>
    </SectionLayout>
  )
}

export default ModifyQuestionView
