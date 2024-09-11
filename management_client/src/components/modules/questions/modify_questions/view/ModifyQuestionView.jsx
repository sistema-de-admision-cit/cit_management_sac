import { useEffect, useState } from 'react'
import FindQuestion from '../../base/molecules/FindQuestion'
import ModifyQuestionForm from '../organisms/ModifyQuestionForm'
import '../../../../../assets/styles/global/view.css'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import QuestionList from '../../delete_questions/organisms/QuestionList'
import { handleGetAllQuestions } from '../../delete_questions/helpers/formHandlers'
import { getQuestionByCode } from '../helpers/helpers'
import useMessages from '../../../../core/global/hooks/useMessages'

const ModifyQuestionView = () => {
  const [questionData, setQuestionData] = useState(null)
  const [incomingData, setIncomingData] = useState(null)
  const [questions, setQuestions] = useState([])
  const [loading, setLoading] = useState(false)

  const { setErrorMessage, renderMessages } = useMessages()

  useEffect(() =>
    handleGetAllQuestions(setQuestions, setLoading, setErrorMessage)
  , [])

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
    // getQuestionByCode is async
    getQuestionByCode(data.code, setIncomingData, setErrorMessage, setLoading)
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
              onResultsUpdate={setQuestions}
            />}
        </div>
        {!questionData && (
          <div className='list-section'>
            <QuestionList
              questions={questions}
              onModify={handleQuestionFound}
              loading={loading}
              actionType='modify'
            />
          </div>
        )}
        {questionData && (
          <div className='form-section'>
            <ModifyQuestionForm
              questionData={questionData}
              setQuestionData={setQuestionData}
            />
          </div>
        )}
      </div>
      {renderMessages()}
    </SectionLayout>
  )
}

export default ModifyQuestionView
