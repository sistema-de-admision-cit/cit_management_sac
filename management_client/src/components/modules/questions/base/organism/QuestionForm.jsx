import { useState } from 'react'
import ExamTypeOptions from '../molecules/ExamTypeOptions'
import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'
import UniqueQuestionSection from './UniqueQuestionSection'
import useMessages from '../../../../core/global/hooks/useMessages'
import useFormState from '../../../../core/global/hooks/useFormState'
import '../../../../../assets/styles/questions/question-form.css'
import {
  handleChange,
  handleTestOptionChange,
  handleOptionChange
} from '../../helpers/formHandlers'
import { getButtonState } from '../../helpers/helpers'
import { EXAM_TYPE_OPTIONS } from '../helpers/questionFormOptions'

const QuestionForm = ({ title, initialData, onSubmit, submitButtonText, searchAgain }) => {
  const { formData: questionData, setFormData: setQuestionData, resetForm } = useFormState(initialData)
  const [isLoading, setIsLoading] = useState(false)
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  const { questionType, questionText, questionOptionsText, correctOption } = questionData

  // clean ALL the form data so it will look like a new form
  const handleSearchAgain = () => {
    searchAgain()
    resetForm()
  }

  return (
    <div className='container question-form-container'>
      <div className='back-button'>
        {/* Mostrar el ícono de "Buscar Otra Pregunta" solo si el título es "Modificar Pregunta" */}
        {title === 'Modificar Pregunta' && (
          <button
            className={`search-again-button ${isLoading ? 'disabled' : ''}`}
            onClick={handleSearchAgain}
            disabled={isLoading}
            aria-label='Buscar Otra Pregunta'
            title='Buscar Otra Pregunta'
          >
            <i className='arrow-left search-again-icon' />
            <span className='search-again-text'>Buscar Otra Pregunta</span>
          </button>
        )}
      </div>
      <h1>{title}</h1>
      <form onSubmit={(e) => onSubmit(e, questionData, setErrorMessage, setSuccessMessage, setIsLoading, setQuestionData)} className='question-form'>
        <h2>Información de la pregunta</h2>
        <ExamTypeOptions
          value={questionType}
          handleChange={(e) => handleTestOptionChange(e, questionData, setQuestionData)}
          options={EXAM_TYPE_OPTIONS}
        />

        <h2>Contenido de la pregunta</h2>
        <InputField
          field={{ name: 'questionText', label: 'Pregunta', type: 'text', placeholder: 'Ingrese la pregunta aquí', required: true }}
          value={questionText}
          handleChange={(e, isFile = false) => handleChange(e, setQuestionData, isFile)}
          className='form-group'
        />

        <InputField
          field={{ name: 'images', label: 'Agregar Imágenes', type: 'file', multiple: true, required: false }}
          handleChange={(e, isFile = true) => handleChange(e, setQuestionData, isFile)}
          className='form-group'
        />

        {/* cuando el tipo de pregunta es unica, se agrega el componente UniqueQuestionSection */}
        {questionType === 'ACA' && (
          <>
            <h2>Opciones de respuesta</h2>
            <UniqueQuestionSection
              options={questionOptionsText}
              correctOption={correctOption}
              handleOptionChange={(index, value) => handleOptionChange(index, value, questionData, setQuestionData)}
              handleInputChange={(e, isFile = false) => handleChange(e, setQuestionData, isFile)}
            />
          </>
        )}

        <Button
          type='submit'
          className='btn btn-primary'
          disabled={getButtonState(questionData, isLoading)}
        >
          {isLoading ? 'Guardando...' : submitButtonText}
        </Button>
      </form>
      {renderMessages()}
    </div>
  )
}

export default QuestionForm
