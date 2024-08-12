import React, { useState } from 'react'
import ExamTypeOptions from '../molecules/ExamTypeOptions'
import QuestionTypeOptions from '../molecules/QuestionTypeOptions'
import QuestionOptions from '../molecules/QuestionOptions'
import InputField from '../../../global/atoms/InputField'
import Button from '../../../global/atoms/Button'
import useMessages from '../../../global/hooks/useMessages'
import useFormState from '../../../global/hooks/useFormState' // Update this path as needed
import '../../../../assets/styles/questions/question-form.css'

import {
  handleChange,
  handleTestOptionChange,
  handleOptionChange,
  getButtonState
} from '../../helpers/formHandlers'

const QuestionForm = ({
  title,
  initialData,
  onSubmit,
  submitButtonText,
  isModify = false
}) => {
  const { formData: questionData, setFormData: setQuestionData, resetForm } = useFormState(initialData)
  const [isLoading, setIsLoading] = useState(false)
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

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
    <section className='question-form-container'>
      <h1>{title}</h1>
      <form
        onSubmit={(e) => onSubmit(e, questionData, setErrorMessage, setSuccessMessage, setIsLoading, setQuestionData)}
        className='question-form'
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

        <InputField
          field={{ name: 'images', label: 'Agregar Imágenes', type: 'file', multiple: true }} // Nuevo campo para imágenes
          handleChange={(e) => handleChange(e, questionData, setQuestionData, true)}
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
          {isLoading ? 'Guardando...' : submitButtonText}
        </Button>
        <Button
          type='button'
          className='btn btn-secondary'
          onClick={() => {
            resetForm()
            setIsLoading(false)
          }}
          disabled={isLoading}
        >
          Limpiar
        </Button>
      </form>
      {renderMessages()}
    </section>
  )
}

export default QuestionForm
