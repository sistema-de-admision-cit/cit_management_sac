import React, { useState, useEffect } from 'react'
import InputField from '../../../global/atoms/InputField'
import Button from '../../../global/atoms/Button'
import ExamTypeOptions from '../../base/molecules/ExamTypeOptions'
import PopupComponent from '../../../popups/PopupComponent'
import SuggestionsList from '../../modify_questions/molecules/SuggestionsList'
import { handleSearch, handleSearchByCode } from '../../helpers/formHandlers'
import '../../../../assets/styles/questions/find-question.css'

const FindQuestion = ({ onQuestionFound, onResultsUpdate, lookingFor }) => {
  const [query, setQuery] = useState('') // state para almacenar la consulta (query) de busqueda
  const [errorMessage, setErrorMessage] = useState('') // state  mensajes de error
  const [suggestions, setSuggestions] = useState([]) // state para las sugerencias de busqueda
  const [showAdvancedSearch, setShowAdvancedSearch] = useState(false) // state para mostrar la busqueda avanzada
  const [searchCode, setSearchCode] = useState('') // state para almacenar el codigo de busqueda
  const [searchExamType, setSearchExamType] = useState('both') // state para almacenar el tipo de examen de busqueda

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

  // interfaz para facilitar la busqueda // utilizar onResultsUpdate o setSuggestions
  const setQuestions = (questions) => {
    lookingFor === 'delete' ? onResultsUpdate(questions) : setSuggestions(questions)
  }

  // Maneja el clic en una sugerencia
  const handleSuggestionClick = (item) => {
    onQuestionFound(item) // Llamar a la función proporcionada cuando se encuentra una pregunta
    setQuery('') // Restablecer la consulta
    setSuggestions([]) // Limpiar las sugerencias
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
      {/* Mostrar mensaje de error si hay alguno */}
      {errorMessage && (
        <PopupComponent message={errorMessage} onClose={() => setErrorMessage('')} type='error' />
      )}
      {/* Campo de entrada para buscar preguntas */}
      <InputField
        field={{ name: 'questionText', label: 'Buscar Pregunta', type: 'text', placeholder: 'Ingrese el texto de la pregunta' }}
        value={query}
        handleChange={handleInputChange}
        className='form-group'
      />
      {/* mostrar/ocultar la bsqueda avanzada */}
      <Button type='button' className='btn btn-secondary' onClick={handleAdvancedSearch}>
        {showAdvancedSearch ? 'Ocultar Búsqueda Avanzada' : 'Búsqueda Avanzada'}
      </Button>

      {/* busqueda avanzada */}
      {showAdvancedSearch && (
        <div className='advanced-search-container'>
          <InputField
            field={{ name: 'questionCode', label: 'Código de Pregunta', type: 'text', placeholder: 'Ingrese el código de la pregunta' }}
            value={searchCode}
            handleChange={(e) => handleSearchByCode(e, setQuery, setSearchCode, setSearchExamType, setQuestions)}
            className='form-group'
          />
          <ExamTypeOptions
            value={searchExamType}
            handleChange={(e) => handleExamTypeChange(e)}
            options={examTypeOptions}
          />
        </div>
      )}

      {/* lista de sugerencias cuando se esta en la pestana de modificar */}
      {suggestions.length > 0 && lookingFor !== 'delete' && (
        <SuggestionsList suggestions={suggestions} onSuggestionClick={handleSuggestionClick} />
      )}
    </div>
  )
}

export default FindQuestion
