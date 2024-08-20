import InputField from '../../../../core/global/atoms/InputField'
import ExamTypeOptions from '../../base/molecules/ExamTypeOptions'
import { handleSearchByCode } from '../../helpers/formHandlers'

const AdvancedSearch = ({ searchCode, setSearchCode, setQuery, setSearchExamType, setQuestions, searchExamType, handleExamTypeChange, examTypeOptions }) => {
  return (
    <div className='advanced-search-container'>
      <InputField
        field={{ name: 'questionCode', label: 'Código de Pregunta', type: 'text', placeholder: 'Ingrese el código de la pregunta' }}
        value={searchCode}
        handleChange={(e) => handleSearchByCode(e, setQuery, setSearchCode, setSearchExamType, setQuestions)}
        className='form-group'
      />
      <ExamTypeOptions
        value={searchExamType}
        handleChange={handleExamTypeChange}
        options={examTypeOptions}
        isRequired={false}
      />
    </div>
  )
}

export default AdvancedSearch
