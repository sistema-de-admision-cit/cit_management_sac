import { useState, useEffect } from 'react'
import QuestionList from '../organisms/QuestionList'
import '../../../../../assets/styles/questions/view.css'
import { dummyData } from '../../helpers/dummyData'
import { handleDeleteFromList } from '../helpers/formHandlers'
import FindQuestion from '../../base/molecules/FindQuestion'
import SectionLayout from '../../../../global/molecules/SectionLayout'
import useMessages from '../../../../global/hooks/useMessages'

const DeleteQuestionView = () => {
  const [questions, setQuestions] = useState([])
  const [filteredQuestions, setFilteredQuestions] = useState([])
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  useEffect(() => {
    setQuestions(dummyData)
    setFilteredQuestions(dummyData)
  }, [])

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
      />
      {renderMessages()}
    </SectionLayout>
  )
}

export default DeleteQuestionView
