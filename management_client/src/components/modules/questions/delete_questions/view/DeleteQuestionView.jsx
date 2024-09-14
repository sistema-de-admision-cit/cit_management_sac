import { useState, useEffect } from 'react'
import QuestionList from '../../base/organism/QuestionList.jsx'
import '../../../../../assets/styles/global/view.css'
import { handleDeleteFromList, handleGetAllQuestions } from '../helpers/formHandlers'
import FindQuestion from '../../base/molecules/FindQuestion'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import useMessages from '../../../../core/global/hooks/useMessages'

const DeleteQuestionView = () => {
  const [questions, setQuestions] = useState([])
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  const [loading, setLoading] = useState(false)

  useEffect(() =>
    handleGetAllQuestions(setQuestions, setLoading, setErrorMessage)
  , [])

  const handleDelete = (code) => {
    handleDeleteFromList(code, questions, setQuestions, setErrorMessage, setSuccessMessage)
  }

  return (
    <SectionLayout title='Eliminar pregunta'>
      <FindQuestion
        onResultsUpdate={setQuestions}
      />
      <QuestionList
        questions={questions}
        onDelete={handleDelete}
        loading={loading}
        actionType='delete'
      />
      {renderMessages()}
    </SectionLayout>
  )
}

export default DeleteQuestionView
