import React, { useState, useEffect } from 'react'
import InputField from '../../../global/atoms/InputField'
import PopupComponent from '../../../popups/PopupComponent'
import SuggestionsList from '../../modify_questions/molecules/SuggestionsList'
import { handleModifyForSearch, handleDeleteForSearch } from '../../helpers/formHandlers'
import { dummyData } from '../../helpers/dummyData'
import '../../../../assets/styles/questions/find-question.css'

const FindQuestion = ({ onQuestionFound, onResultsUpdate, lookingFor }) => {
  const [query, setQuery] = useState('') // state para almacenar la consulta (query) de busqueda
  const [errorMessage, setErrorMessage] = useState('') // state  mensajes de error
  const [suggestions, setSuggestions] = useState([]) // state para las sugerencias de busqueda

  useEffect(() => {
    // Este efecto se ejecuta cada vez que cambia 'query', 'questions' o 'onResultsUpdate'
    if (query.length > 2) { // Solo realizar búsqueda si la consulta tiene más de 2 caracteres
      if (lookingFor === 'delete') {
        handleDeleteForSearch(query, onResultsUpdate)
      } else {
        handleModifyForSearch(query, setErrorMessage, setSuggestions)
      }
    } else {
      onResultsUpdate ? onResultsUpdate(dummyData) : setSuggestions([]) // Limpiar las sugerencias si la consulta es menor a 3 caracteres
    }
  }, [query, onResultsUpdate, lookingFor])

  // Maneja el cambio en el campo de entrada
  const handleInputChange = (e) => {
    const searchTerm = e.target.value
    setQuery(searchTerm) // Actualizar el estado de la consulta
  }

  // Maneja el clic en una sugerencia
  const handleSuggestionClick = (item) => {
    onQuestionFound(item) // Llamar a la función proporcionada cuando se encuentra una pregunta
    setQuery('') // Restablecer la consulta
    setSuggestions([]) // Limpiar las sugerencias
  }

  return (
    <div className='find-question-container'>
      {/* Mostrar mensaje de error si hay alguno */}
      {errorMessage && (
        <PopupComponent message={errorMessage} onClose={() => setErrorMessage('')} type='error' />
      )}
      {/* Campo de entrada para buscar preguntas */}
      <InputField
        field={{ name: 'questionCode', label: 'Buscar Pregunta', type: 'text', placeholder: 'Ingrese el texto de la pregunta' }}
        value={query}
        handleChange={handleInputChange}
        className='form-group'
      />
      {/* Mostrar la lista de sugerencias si hay sugerencias y no se pasaron preguntas */}
      {suggestions.length > 0 && lookingFor !== 'delete' && (
        <SuggestionsList suggestions={suggestions} onSuggestionClick={handleSuggestionClick} />
      )}
    </div>
  )
}

export default FindQuestion
