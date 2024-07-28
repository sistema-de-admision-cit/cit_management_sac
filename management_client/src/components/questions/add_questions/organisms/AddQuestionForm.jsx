import React, { useState } from 'react'
import ExamTypeOptions from '../molecules/ExamTypeOptions'
import QuestionTypeOptions from '../molecules/QuestionTypeOptions'
import QuestionOptions from '../molecules/QuestionOptions'
import InputField from '../../../global/atoms/InputField'
import Button from '../../../global/atoms/Button'
import PopupComponent from '../../../popups/PopupComponent'
import '../../../../assets/styles/questions/add_questions/add-question.css'

import {
  handleChange,
  handleTestOptionChange,
  handleOptionChange,
  clearForm,
  handleSubmit,
  getButtonState
} from '../../helpers/formHandlers'

const AddQuestionForm = () => {
  const [questionData, setQuestionData] = useState({
    examType: '',
    questionType: '',
    question: '',
    options: ['', '', '', ''],
    correctOption: ''
  })
  const [isLoading, setIsLoading] = useState(false)
  const [errorMessage, setErrorMessage] = useState('')
  const [successMessage, setSuccessMessage] = useState('')

  const examTypeOptions = [
    { value: 'academic', label: 'Académico' },
    { value: 'dai', label: 'DAI' }
  ]

  const questionTypeOptions = [
    ...(questionData.examType === 'dai'
      ? [{ value: 'short', label: 'Respuesta Corta' }]
      : [{ value: 'unique', label: 'Selección Única' }])
  ]

  return (
    <section className='add-question-container'>
      <h1>Ingresar Pregunta</h1>
      {errorMessage && (
        <PopupComponent message={errorMessage} onClose={() => setErrorMessage('')} type='error' />
      )}
      {successMessage && (
        <PopupComponent message={successMessage} onClose={() => setSuccessMessage('')} type='confirmation' />
      )}
      <form
        onSubmit={(e) =>
          handleSubmit(e, questionData, setErrorMessage, setSuccessMessage, setIsLoading, setQuestionData)}
        className='add-question-form'
      >
        <ExamTypeOptions
          value={questionData.examType}
          handleChange={(e) => handleTestOptionChange(e, questionData, setQuestionData)}
          options={examTypeOptions}
        />

        {questionData.examType && (
          <QuestionTypeOptions
            value={questionData.questionType}
            handleChange={(e) => handleChange(e, questionData, setQuestionData)}
            options={questionTypeOptions}
          />
        )}

        <InputField
          field={{ name: 'question', label: 'Pregunta', type: 'text', placeholder: 'Ingrese la pregunta aquí' }}
          value={questionData.question}
          handleChange={(e) => handleChange(e, questionData, setQuestionData)}
          className='form-group'
        />

        {questionData.questionType === 'unique' && (
          <QuestionOptions
            options={questionData.options}
            handleOptionChange={(index, value) => handleOptionChange(index, value, questionData, setQuestionData)}
          />
        )}

        {questionData.questionType === 'unique' && (
          <InputField
            field={{ name: 'correctOption', label: 'Respuesta Correcta', type: 'select' }}
            value={questionData.correctOption}
            handleChange={(e) => handleChange(e, questionData, setQuestionData)}
            className='form-group'
          >
            <option value=''>Seleccionar</option>
            {questionData.options.map((option, index) => (
              <option key={index} value={index}>{`Opción ${index + 1}`}</option>
            ))}
          </InputField>
        )}

        <Button type='submit' className='btn btn-primary' disabled={getButtonState(questionData, isLoading)}>
          {isLoading ? 'Guardando...' : 'Guardar Pregunta'}
        </Button>
        <Button
          type='button'
          className='btn btn-secondary'
          onClick={() => clearForm(setQuestionData)}
          disabled={isLoading}
        >
          Limpiar
        </Button>
      </form>
    </section>
  )
}

export default AddQuestionForm
