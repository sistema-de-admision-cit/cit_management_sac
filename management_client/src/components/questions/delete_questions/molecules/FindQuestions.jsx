import React, { useState } from 'react'
import InputField from '../../../global/atoms/InputField'
import PopupComponent from '../../../popups/PopupComponent'
import '../../../../assets/styles/questions/find-question.css'

const FindQuestion = ({ questions, onResultsUpdate }) => {
  const [query, setQuery] = useState('')
  const [errorMessage, setErrorMessage] = useState('')

  const handleInputChange = (e) => {
    const searchTerm = e.target.value
    setQuery(searchTerm)

    if (searchTerm.length > 2) {
      const filteredQuestions = questions.filter(question =>
        question.question.toLowerCase().includes(searchTerm.toLowerCase())
      )
      onResultsUpdate(filteredQuestions)
    } else {
      onResultsUpdate(questions)
    }
  }

  return (
    <div className='find-question-container'>
      {errorMessage && (
        <PopupComponent message={errorMessage} onClose={() => setErrorMessage('')} type='error' />
      )}
      <InputField
        field={{ name: 'questionCode', label: 'Buscar Pregunta', type: 'text', placeholder: 'Ingrese el texto de la pregunta' }}
        value={query}
        handleChange={handleInputChange}
        className='form-group'
      />
    </div>
  )
}

export default FindQuestion
