import React, { useState, useEffect } from 'react'
import InputField from '../../../global/atoms/InputField'
import Button from '../../../global/atoms/Button'
import PopupComponent from '../../../popups/PopupComponent'
import SuggestionsList from '../../modify_questions/molecules/SuggestionsList'
import AdvancedSearch from './AdvancedSearch'
import { handleSearch } from '../../helpers/formHandlers'
import '../../../../assets/styles/questions/find-question.css'

const FindQuestion = ({ onQuestionFound, onResultsUpdate, lookingFor }) => {
  const [query, setQuery] = useState('')
  const [errorMessage, setErrorMessage] = useState('')
  const [suggestions, setSuggestions] = useState([])
  const [showAdvancedSearch, setShowAdvancedSearch] = useState(false)
  const [searchCode, setSearchCode] = useState('')
  const [searchExamType, setSearchExamType] = useState('both')

  useEffect(() => {
    handleSearch(query, setQuestions, searchExamType, setSearchCode, lookingFor)
  }, [query, onResultsUpdate, lookingFor, searchExamType])

  const handleInputChange = (e) => {
    const searchTerm = e.target.value
    setQuery(searchTerm)
  }

  const handleExamTypeChange = (e) => {
    const examType = e.target.value
    setSearchExamType(examType)
  }

  const setQuestions = (questions) => {
    lookingFor === 'delete' ? onResultsUpdate(questions) : setSuggestions(questions)
  }

  const handleSuggestionClick = (item) => {
    onQuestionFound(item)
    setQuery('')
    setSuggestions([])
  }

  const handleAdvancedSearch = () => {
    setShowAdvancedSearch(!showAdvancedSearch)
  }

  const examTypeOptions = [
    { value: 'both', label: 'Ambos' },
    { value: 'academic', label: 'Académico' },
    { value: 'dai', label: 'DAI' }
  ]

  return (
    <div className='find-question-container'>
      {errorMessage && (
        <PopupComponent message={errorMessage} onClose={() => setErrorMessage('')} type='error' />
      )}
      <InputField
        field={{ name: 'questionText', label: 'Buscar Pregunta', type: 'text', placeholder: 'Ingrese el texto de la pregunta' }}
        value={query}
        handleChange={handleInputChange}
        className='form-group'
      />
      <Button type='button' className='btn btn-secondary' onClick={handleAdvancedSearch}>
        {showAdvancedSearch ? 'Ocultar Búsqueda Avanzada' : 'Búsqueda Avanzada'}
      </Button>

      {showAdvancedSearch && (
        <AdvancedSearch
          searchCode={searchCode}
          setSearchCode={setSearchCode}
          setQuery={setQuery}
          setSearchExamType={setSearchExamType}
          setQuestions={setQuestions}
          searchExamType={searchExamType}
          handleExamTypeChange={handleExamTypeChange}
          examTypeOptions={examTypeOptions}
        />
      )}

      {suggestions.length > 0 && lookingFor !== 'delete' && (
        <SuggestionsList suggestions={suggestions} onSuggestionClick={handleSuggestionClick} />
      )}
    </div>
  )
}

export default FindQuestion
