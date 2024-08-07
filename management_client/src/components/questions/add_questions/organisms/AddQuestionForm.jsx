import React from 'react'
import QuestionForm from '../../base/organism/QuestionForm'
import { handleSubmit } from '../../helpers/formHandlers'

const AddQuestionForm = () => {
  const initialData = {
    examType: '',
    questionType: '',
    question: '',
    images: [],
    options: ['', '', '', ''],
    correctOption: ''
  }

  return (
    <QuestionForm
      title='Ingresar Pregunta'
      initialData={initialData}
      onSubmit={handleSubmit}
      submitButtonText='Guardar Pregunta'
    />
  )
}

export default AddQuestionForm
