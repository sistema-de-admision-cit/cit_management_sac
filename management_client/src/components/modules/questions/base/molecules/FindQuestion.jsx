import { useState, useEffect } from 'react'
import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'
import AdvancedSearch from './AdvancedSearch'
import { handleSearch } from '../../helpers/formHandlers'
import {
  handleInputChange,
  handleExamTypeChange,
  handleAdvancedSearch
} from '../helpers/findQuestionHandlers'
import '../../../../../assets/styles/questions/find-question.css'
import useFormState from '../../../../core/global/hooks/useFormState'

const FindQuestion = ({ onResultsUpdate }) => {
  const { formData: query, setFormData: setQuery } = useFormState('')
  const { formData: searchCode, setFormData: setSearchCode } = useFormState('')
  const [showAdvancedSearch, setShowAdvancedSearch] = useState(false)
  const [searchExamType, setSearchExamType] = useState('both')

  useEffect(() => {
    handleSearch(query, onResultsUpdate, searchExamType, setSearchCode)
  }, [query, onResultsUpdate, searchExamType])

  const examTypeOptions = [
    { value: 'both', label: 'Ambos' },
    { value: 'ACA', label: 'Académico' },
    { value: 'DAI', label: 'DAI' }
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
          setQuestions={(questions) => onResultsUpdate(questions)}
          searchExamType={searchExamType}
          handleExamTypeChange={(e) => handleExamTypeChange(e, setSearchExamType)}
          examTypeOptions={examTypeOptions}
        />
      )}

    </div>
  )
}

export default FindQuestion
