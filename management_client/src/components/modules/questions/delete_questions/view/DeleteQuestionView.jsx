import { useState, useEffect } from 'react'
import QuestionList from '../organisms/QuestionList'
import '../../../../../assets/styles/global/view.css'
import { handleDeleteFromList, handleGetAllQuestions } from '../helpers/formHandlers'
import FindQuestion from '../../base/molecules/FindQuestion'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import useMessages from '../../../../core/global/hooks/useMessages'

const DeleteQuestionView = () => {
  const [questions, setQuestions] = useState([])
  const [filteredQuestions, setFilteredQuestions] = useState([])
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  const [loading, setLoading] = useState(false)

  useEffect(() =>
    handleGetAllQuestions(setQuestions, setLoading, setErrorMessage)
  , [])

  const handleDelete = (code) => {
    handleDeleteFromList(code, filteredQuestions, setFilteredQuestions, setErrorMessage, setSuccessMessage)
    setQuestions(questions.filter(question => question.code !== code))
  }

  return (
    <SectionLayout title='Eliminar pregunta'>
      <FindQuestion
        onResultsUpdate={setFilteredQuestions}
        lookingFor='delete'
      />
      <QuestionList
        questions={filteredQuestions}
        onDelete={handleDelete}
        loading={loading}
      />
      {renderMessages()}
    </SectionLayout>
  )
}

export default DeleteQuestionView
