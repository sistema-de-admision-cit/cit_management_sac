import React, { useState } from 'react'
import ExamTypeOptions from '../molecules/ExamTypeOptions'
import QuestionTypeOptions from '../molecules/QuestionTypeOptions'
import QuestionOptions from '../molecules/QuestionOptions'
import InputField from '../../../global/atoms/InputField'
import Button from '../../../global/atoms/Button'
import useMessages from '../../../global/hooks/useMessages'
import useFormState from '../../../global/hooks/useFormState'
import '../../../../assets/styles/questions/question-form.css'
import {
  handleChange,
  handleTestOptionChange,
  handleOptionChange,
  getButtonState
} from '../../helpers/formHandlers'
import { EXAM_TYPE_OPTIONS, QUESTION_TYPE_OPTIONS } from '../helpers/questionFormOptions'

const QuestionForm = ({ title, initialData, onSubmit, submitButtonText }) => {
  const { formData: questionData, setFormData: setQuestionData, resetForm } = useFormState(initialData)
  const [isLoading, setIsLoading] = useState(false)
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  const { examType, questionType, question, options, correctOption } = questionData
  const currentQuestionTypeOptions = QUESTION_TYPE_OPTIONS[examType] || []

  const handleSubmit = (e) => {
    e.preventDefault()
    onSubmit(e, questionData, setErrorMessage, setSuccessMessage, setIsLoading, setQuestionData)
  }

  const handleInputChange = (e, isFile = false) => {
    handleChange(e, questionData, setQuestionData, isFile)
  }

  const localHandleOptionChange = (index, value) => {
    handleOptionChange(index, value, questionData, setQuestionData)
  }

  return (
    <section className='question-form-container'>
      <h1>{title}</h1>
      <form onSubmit={handleSubmit} className='question-form'>
        <ExamTypeOptions
          value={examType}
          handleChange={(e) => handleTestOptionChange(e, questionData, setQuestionData)}
          options={EXAM_TYPE_OPTIONS}
        />

        {examType && (
          <QuestionTypeOptions
            value={questionType}
            handleChange={(e) => handleInputChange(e)}
            options={currentQuestionTypeOptions}
          />
        )}

        <InputField
          field={{ name: 'question', label: 'Pregunta', type: 'text', placeholder: 'Ingrese la pregunta aquí' }}
          value={question}
          handleChange={(e) => handleInputChange(e)}
          className='form-group'
        />

        <InputField
          field={{ name: 'images', label: 'Agregar Imágenes', type: 'file', multiple: true, required: false }}
          handleChange={(e) => handleInputChange(e, true)}
          className='form-group'
        />

        {questionType === 'unique' && (
          <>
            <QuestionOptions
              options={options}
              handleOptionChange={localHandleOptionChange}
            />

            <InputField
              field={{ name: 'correctOption', label: 'Respuesta Correcta', type: 'select' }}
              value={correctOption}
              handleChange={(e) => handleInputChange(e)}
              className='form-group'
            >
              <option value=''>Seleccionar</option>
              {options.map((option, index) => (
                <option key={index} value={index}>{`Opción ${index + 1}`}</option>
              ))}
            </InputField>
          </>
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
