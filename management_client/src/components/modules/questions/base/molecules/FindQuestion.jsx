import { useState } from 'react'
import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'
import AdvancedSearch from './AdvancedSearch'
import '../../../../../assets/styles/questions/find-question.css'

const FindQuestion = ({ query, setQuery, searchExamType, setSearchExamType }) => {
  const [showAdvancedSearch, setShowAdvancedSearch] = useState(false)

  const examTypeOptions = [
    { value: 'both', label: 'Ambos' },
    { value: 'ACA', label: 'Académico' },
    { value: 'DAI', label: 'DAI' }
  ]

  return (
    <div className='container find-question-container'>
      <InputField
        field={{
          name: 'questionText',
          label: 'Buscar Pregunta',
          type: 'text',
          placeholder: 'Ingrese el texto de la pregunta'
        }}
        value={query}
        handleChange={(e) => setQuery(e.target.value)}
        className='form-group'
      />
      <Button
        type='button'
        className='btn btn-secondary'
        onClick={() => setShowAdvancedSearch(!showAdvancedSearch)}
      >
        {showAdvancedSearch ? 'Ocultar Búsqueda Avanzada' : 'Búsqueda Avanzada'}
      </Button>

      {showAdvancedSearch && (
        <AdvancedSearch
          query={query}
          setQuery={setQuery}
          searchExamType={searchExamType}
          setSearchExamType={setSearchExamType}
          examTypeOptions={examTypeOptions}
        />
      )}
    </div>
  )
}

export default FindQuestion
