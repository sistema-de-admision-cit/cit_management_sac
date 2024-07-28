import React, { useState } from 'react'
import InputField from '../../../global/atoms/InputField'
import Button from '../../../global/atoms/Button'
import PopupComponent from '../../../popups/PopupComponent'
import { handleSearch } from '../../helpers/formHandlers'
import '../../../../assets/styles/questions/find-question.css'

const FindQuestion = ({ onQuestionFound }) => {
  const [questionCode, setQuestionCode] = useState('')
  const [errorMessage, setErrorMessage] = useState('')

  return (
    <div className='find-question-container'>
      {errorMessage && (
        <PopupComponent message={errorMessage} onClose={() => setErrorMessage('')} type='error' />
      )}
      <InputField
        field={{ name: 'questionCode', label: 'Código de la Pregunta', type: 'text', placeholder: 'Ingrese el código de la pregunta' }}
        value={questionCode}
        handleChange={(e) => setQuestionCode(e.target.value)}
        className='form-group'
      />
      <Button onClick={() => handleSearch(questionCode, setErrorMessage, onQuestionFound)}>
        Buscar Pregunta
      </Button>
    </div>
  )
}

export default FindQuestion
