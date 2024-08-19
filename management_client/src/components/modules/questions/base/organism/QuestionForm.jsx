import { useState } from 'react'
import ExamTypeOptions from '../molecules/ExamTypeOptions'
import QuestionTypeOptions from '../molecules/QuestionTypeOptions'
import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'
import UniqueQuestionSection from './UniqueQuestionSection'
import useMessages from '../../../../core/global/hooks/useMessages'
import useFormState from '../../../../core/global/hooks/useFormState'
import '../../../../../assets/styles/questions/question-form.css'
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

  return (
    <div className='container question-form-container'>
      <h1>{title}</h1>
      <form onSubmit={(e) => onSubmit(e, questionData, setErrorMessage, setSuccessMessage, setIsLoading, setQuestionData)} className='question-form'>
        <ExamTypeOptions
          value={examType}
          handleChange={(e) => handleTestOptionChange(e, questionData, setQuestionData)}
          options={EXAM_TYPE_OPTIONS}
        />

        {examType && (
          <QuestionTypeOptions
            value={questionType}
            handleChange={(e, isFile = false) => handleChange(e, questionData, setQuestionData, isFile)}
            options={currentQuestionTypeOptions}
          />
        )}

        <InputField
          field={{ name: 'question', label: 'Pregunta', type: 'text', placeholder: 'Ingrese la pregunta aquí' }}
          value={question}
          handleChange={(e, isFile = false) => handleChange(e, questionData, setQuestionData, isFile)}
          className='form-group'
        />

        <InputField
          field={{ name: 'images', label: 'Agregar Imágenes', type: 'file', multiple: true, required: false }}
          handleChange={(e, isFile = true) => handleChange(e, questionData, setQuestionData, isFile)}
          className='form-group'
        />

        {/* cuando el tipo de pregunta es unica, se agrega el componente UniqueQuestionSection */}
        {questionType === 'unique' && (
          <UniqueQuestionSection
            options={options}
            correctOption={correctOption}
            handleOptionChange={(index, value) => handleOptionChange(index, value, questionData, setQuestionData)}
            handleInputChange={(e, isFile = false) => handleChange(e, questionData, setQuestionData, isFile)}
          />
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
    </div>
  )
}

export default QuestionForm
