import { useEffect, useState } from 'react'
import FindQuestion from '../../base/molecules/FindQuestion'
import ModifyQuestionForm from '../organisms/ModifyQuestionForm'
import '../../../../../assets/styles/global/view.css'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import QuestionList from '../../base/organism/QuestionList.jsx'
import { handleGetAllQuestions } from '../../delete_questions/helpers/formHandlers'
import { getQuestionById } from '../helpers/helpers'
import useMessages from '../../../../core/global/hooks/useMessages'

/**
 * Maps question options data to the format required by the form.
 * @param {[{id: number, isCorrect: boolean, option: string}]} questionOptions
 * @returns {{ questionOptionsText: string[], correctOption: number }}
 */
const mapIncomingQuestionOptionsData = (questionOptions) => {
  if (!Array.isArray(questionOptions)) return { questionOptionsText: [], correctOption: '' }

  const questionOptionsText = questionOptions.map(q => q.option)
  const correctIndex = questionOptions.findIndex(q => q.isCorrect)

  return {
    questionOptionsText,
    correctOption: correctIndex !== -1 ? correctIndex : ''
  }
}

const ModifyQuestionView = () => {
  const [questionData, setQuestionData] = useState(null)
  const [incomingData, setIncomingData] = useState(null)
  const [questions, setQuestions] = useState([])
  const [loading, setLoading] = useState(false)

  const { setErrorMessage, renderMessages } = useMessages()

  useEffect(() =>
    handleGetAllQuestions(setQuestions, setLoading, setErrorMessage)
  , [])

  useEffect(() => {
    if (incomingData) {
      setQuestionData(null)
      const timer = setTimeout(() => {
        console.log('incomingData', incomingData)

        // transform questionOptions
        const transformedOptions = mapIncomingQuestionOptionsData(incomingData.questionOptions)

        setQuestionData({
          ...incomingData,
          ...transformedOptions
        })
      }, 0)

      // Limpieza del efecto
      return () => clearTimeout(timer)
    }
  }, [incomingData])

  // Maneja la pregunta encontrada
  const handleQuestionFound = (data) => {
    // getQuestionByCode is async
    getQuestionById(data.id, setIncomingData, setErrorMessage, setLoading)
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
