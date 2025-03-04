import InputField from '../../../../core/global/atoms/InputField'
import QuestionTypeOptions from './QuestionTypeOptions'
import { handleSearchByCode } from '../../helpers/formHandlers'

const AdvancedSearch = ({ searchCode, setSearchCode, setQuery, setSearchExamType, setQuestions, searchExamType, handleExamTypeChange, questionTypeOptions }) => {
  return (
    <div className='advanced-search-container'>
      <InputField
        field={{ name: 'questionCode', label: 'Código de Pregunta', type: 'text', placeholder: 'Ingrese el código de la pregunta' }}
        value={searchCode}
        handleChange={(e) => handleSearchByCode(e, setQuery, setSearchCode, setQuestions, searchExamType)}
        className='form-group'
      />
      <QuestionTypeOptions
        value={searchExamType}
        handleChange={handleExamTypeChange}
        options={questionTypeOptions}
        isRequired={false}
      />
    </div>
  )
}

export default AdvancedSearch
