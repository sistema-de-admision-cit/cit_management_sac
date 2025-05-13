import { useState, useMemo, useCallback } from 'react'
import QuestionTypeOptions from '../molecules/QuestionTypeOptions'
import QuestionGradeOptions from '../molecules/QuestionGradeOptions'
import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'
import UniqueQuestionSection from './UniqueQuestionSection'
import useMessages from '../../../../core/global/hooks/useMessages'
import useFormState from '../../../../core/global/hooks/useFormState'
import { useAuth } from '../../../../../router/AuthProvider'
import '../../../../../assets/styles/questions/question-form.css'
import {
  handleChange,
  handleTestOptionChange,
  handleOptionChange
} from '../../helpers/formHandlers'
import { getButtonState } from '../../helpers/helpers'
import { EXAM_GRADE_OPTIONS, ACADEMIC_EXAM_TYPE_OPTIONS, DAI_EXAM_TYPE_OPTIONS } from '../helpers/questionFormOptions'
const getExamTypeOptionsByUserRole = (userRole) => {
  const optionsByRole = {
    SYS: [ACADEMIC_EXAM_TYPE_OPTIONS, DAI_EXAM_TYPE_OPTIONS],
    ADMIN: [ACADEMIC_EXAM_TYPE_OPTIONS, DAI_EXAM_TYPE_OPTIONS],
    PSYCHOLOGIST: [DAI_EXAM_TYPE_OPTIONS],
    TEACHER: [ACADEMIC_EXAM_TYPE_OPTIONS]
  }
  return optionsByRole[userRole] || [ACADEMIC_EXAM_TYPE_OPTIONS, DAI_EXAM_TYPE_OPTIONS]
}

const QuestionForm = ({ title, initialData, onSubmit, submitButtonText, searchAgain }) => {
  const { user } = useAuth()
  const userRole = user.role

  const { formData: questionData, setFormData: setQuestionData, resetForm } = useFormState(initialData)
  const [isLoading, setIsLoading] = useState(false)
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  const { questionType, questionGrade, questionText, questionOptionsText, correctOption } = questionData

  const filteredExamTypeOptions = useMemo(() => getExamTypeOptionsByUserRole(userRole), [userRole])

  const handleSearchAgain = useCallback(() => {
    searchAgain()
    resetForm()
  }, [searchAgain, resetForm])

  const handleInputChange = (e, isFile = false) => handleChange(e, setQuestionData, isFile)

  return (
    <div className='container question-form-container'>
      <div className='back-button'>
        {title === 'Modificar Pregunta' && (
          <button
            type='button'
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
      <form
        onSubmit={(e) =>
          onSubmit(e, questionData, setErrorMessage, setSuccessMessage, setIsLoading, setQuestionData)}
        className='question-form'
      >
        <h2>Información de la pregunta</h2>
        <QuestionTypeOptions
          value={questionType}
          handleChange={(e) => handleTestOptionChange(e, questionData, setQuestionData)}
          options={filteredExamTypeOptions}
        />
        <QuestionGradeOptions
          value={questionGrade}
          handleChange={(e) => handleTestOptionChange(e, questionData, setQuestionData)}
          options={EXAM_GRADE_OPTIONS}
        />
        <h2>Contenido de la pregunta</h2>
        <InputField
          field={{
            name: 'questionText',
            label: 'Pregunta',
            type: 'text',
            placeholder: 'Ingrese la pregunta aquí',
            required: true
          }}
          value={questionText}
          handleChange={handleInputChange}
          className='form-group'
        />
        <InputField
          field={{
            name: 'images',
            label: 'Agregar Imágenes',
            type: 'file',
            multiple: true,
            required: false
          }}
          handleChange={(e, isFile = true) => handleInputChange(e, isFile)}
          className='form-group'
        />
        {/* Cuando el tipo de pregunta es única, se agrega el componente UniqueQuestionSection */}
        {questionType === 'ACA' && (
          <>
            <h2>Opciones de respuesta</h2>
            <UniqueQuestionSection
              options={questionOptionsText}
              correctOption={correctOption}
              handleOptionChange={(index, value) => handleOptionChange(index, value, questionData, setQuestionData)}
              handleInputChange={handleInputChange}
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
