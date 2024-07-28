import React, { useState } from 'react'
import InputField from '../../../global/atoms/InputField'
import PopupComponent from '../../../popups/PopupComponent'
import { handleSearch } from '../../helpers/formHandlers'
import '../../../../assets/styles/questions/find-question.css'

const FindQuestion = ({ onQuestionFound }) => {
  const [questionCode, setQuestionCode] = useState('')
  const [errorMessage, setErrorMessage] = useState('')
  const [suggestions, setSuggestions] = useState([])

  const handleInputChange = (e) => {
    const query = e.target.value
    setQuestionCode(query)

    if (query.length > 2) {
      handleSearch(query, setErrorMessage, setSuggestions)
    } else {
      setSuggestions([])
    }
  }

  return (
    <div className='find-question-container'>
      {errorMessage && (
        <PopupComponent message={errorMessage} onClose={() => setErrorMessage('')} type='error' />
      )}
      <InputField
        field={{ name: 'questionCode', label: 'Buscar Pregunta', type: 'text', placeholder: 'Ingrese el texto de la pregunta' }}
        value={questionCode}
        handleChange={handleInputChange}
        className='form-group'
      />
      {suggestions.length > 0 && (
        <ul className='suggestions-list'>
          {suggestions.map((item) => (
            <li key={item.code} onClick={() => onQuestionFound(item)}>
              {item.question}
            </li>
          ))}
        </ul>
      )}
    </div>
  )
}

export default FindQuestion
