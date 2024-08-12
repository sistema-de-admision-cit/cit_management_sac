import React, { useState, useEffect } from 'react'
import QuestionList from '../organisms/QuestionList'
import '../../../../assets/styles/questions/view.css'
import { dummyData } from '../../helpers/dummyData'
import { handleDeleteFromList } from '../helpers/formHandlers'
import FindQuestion from '../../base/molecules/FindQuestion'
import PopupComponent from '../../../popups/PopupComponent'
import SectionLayout from '../../../global/molecules/SectionLayout'

const DeleteQuestionView = () => {
  const [questions, setQuestions] = useState([])
  const [filteredQuestions, setFilteredQuestions] = useState([])
  const [errorMessage, setErrorMessage] = useState('')
  const [successMessage, setSuccessMessage] = useState('')

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
      <title>Eliminar Pregunta</title>
      {errorMessage && <PopupComponent message={errorMessage} onClose={() => setErrorMessage('')} type='error' />}
      {successMessage && <PopupComponent message={successMessage} onClose={() => setSuccessMessage('')} type='confirmation' />}
      <FindQuestion
        onResultsUpdate={setFilteredQuestions}
        lookingFor='delete'
      />
      <QuestionList
        questions={filteredQuestions}
        onDelete={handleDelete}
      />
    </SectionLayout>
  )
}

export default DeleteQuestionView
