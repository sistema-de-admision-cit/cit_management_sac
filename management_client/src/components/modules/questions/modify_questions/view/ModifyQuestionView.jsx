import { useEffect, useState } from 'react'
import FindQuestion from '../../base/molecules/FindQuestion'
import ModifyQuestionForm from '../organisms/ModifyQuestionForm'
import '../../../../../assets/styles/global/view.css'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import QuestionList from '../../base/organism/QuestionList.jsx'
import { handleGetAllQuestions } from '../../delete_questions/helpers/formHandlers'
import { getQuestionById } from '../helpers/helpers'
import useMessages from '../../../../core/global/hooks/useMessages'
import Pagination from '../../../../core/global/molecules/Pagination.jsx'

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
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)

  const { setErrorMessage, renderMessages } = useMessages()

  const fetchQuestions = (page = 0, size = 10) => {
    handleGetAllQuestions(page, size, setQuestions, setTotalPages, setLoading, setErrorMessage)
  }

  useEffect(() => {
    fetchQuestions(currentPage)
  }, [currentPage])

  useEffect(() => {
    if (incomingData) {
      setQuestionData(null)
      const timer = setTimeout(() => {
        console.log('incomingData', incomingData)
        const transformedOptions = mapIncomingQuestionOptionsData(incomingData.questionOptions)
        setQuestionData({
          ...incomingData,
          ...transformedOptions
        })
      }, 0)
      return () => clearTimeout(timer)
    }
  }, [incomingData])

  const handleQuestionFound = (data) => {
    getQuestionById(data.id, setIncomingData, setErrorMessage, setLoading)
  }

  useEffect(() => {
    setIncomingData(null)
  }, [questionData])

  const onPageChange = (page) => {
    setCurrentPage(page)
  }

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
        {!questionData && totalPages > 1 && (
          <Pagination
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={onPageChange}
          />
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
