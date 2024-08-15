import { useState, useEffect } from 'react'
import InputField from '../../../global/atoms/InputField'
import Button from '../../../global/atoms/Button'
import SuggestionsList from '../../modify_questions/molecules/SuggestionsList'
import AdvancedSearch from './AdvancedSearch'
import { handleSearch } from '../../helpers/formHandlers'
import {
  handleInputChange,
  handleExamTypeChange,
  setQuestions,
  handleSuggestionClick,
  handleAdvancedSearch
} from '../helpers/findQuestionHandlers'
import '../../../../assets/styles/questions/find-question.css'
import useFormState from '../../../global/hooks/useFormState'

const FindQuestion = ({ onQuestionFound, onResultsUpdate, lookingFor }) => {
  const { formData: query, setFormData: setQuery } = useFormState('')
  const { formData: searchCode, setFormData: setSearchCode } = useFormState('')
  const [showAdvancedSearch, setShowAdvancedSearch] = useState(false)
  const [searchExamType, setSearchExamType] = useState('both')
  const [suggestions, setSuggestions] = useState([])

  useEffect(() => {
    handleSearch(query, (questions) => setQuestions(questions, lookingFor, onResultsUpdate, setSuggestions), searchExamType, setSearchCode, lookingFor)
  }, [query, onResultsUpdate, lookingFor, searchExamType])

  const examTypeOptions = [
    { value: 'both', label: 'Ambos' },
    { value: 'academic', label: 'Académico' },
    { value: 'dai', label: 'DAI' }
  ]

  return (
    <div className='container find-question-container'>
      <InputField
        field={{ name: 'questionText', label: 'Buscar Pregunta', type: 'text', placeholder: 'Ingrese el texto de la pregunta' }}
        value={query}
        handleChange={(e) => handleInputChange(e, setQuery)}
        className='form-group'
      />
      <Button type='button' className='btn btn-secondary' onClick={() => handleAdvancedSearch(showAdvancedSearch, setShowAdvancedSearch)}>
        {showAdvancedSearch ? 'Ocultar Búsqueda Avanzada' : 'Búsqueda Avanzada'}
      </Button>

      {showAdvancedSearch && (
        <AdvancedSearch
          searchCode={searchCode}
          setSearchCode={setSearchCode}
          setQuery={setQuery}
          setSearchExamType={setSearchExamType}
          setQuestions={(questions) => setQuestions(questions, lookingFor, onResultsUpdate, setSuggestions)}
          searchExamType={searchExamType}
          handleExamTypeChange={(e) => handleExamTypeChange(e, setSearchExamType)}
          examTypeOptions={examTypeOptions}
        />
      )}

      {suggestions.length > 0 && lookingFor !== 'delete' && (
        <SuggestionsList suggestions={suggestions} onSuggestionClick={(item) => handleSuggestionClick(item, onQuestionFound, setQuery, setSuggestions)} />
      )}
    </div>
  )
}

export default FindQuestion
