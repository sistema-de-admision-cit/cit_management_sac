import React, { useEffect, useState } from 'react'
import FindQuestion from '../molecules/FindQuestion'
import ModifyQuestionForm from '../organisms/ModifyQuestionForm'
import '../../../../assets/styles/questions/view.css'

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

  return (
    <div className='section-container'>
      <title>Modificar Pregunta</title>
      <div className='modify-question-container'>
        <div className='search-section'>
          <FindQuestion onQuestionFound={handleQuestionFound} />
        </div>
        <div className='form-section'>
          {questionData && (
            <ModifyQuestionForm questionData={questionData} setQuestionData={setQuestionData} />
          )}
        </div>
      </div>
    </div>
  )
}

export default ModifyQuestionView
