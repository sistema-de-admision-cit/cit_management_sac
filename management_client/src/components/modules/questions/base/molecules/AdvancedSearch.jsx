import InputField from '../../../../core/global/atoms/InputField'
import QuestionTypeOptions from './QuestionTypeOptions'

const AdvancedSearch = ({ query, setQuery, searchExamType, setSearchExamType, examTypeOptions }) => {
  return (
    <div className='advanced-search-container'>
      <InputField
        field={{
          name: 'questionCode',
          label: 'Código de Pregunta',
          type: 'text',
          placeholder: 'Ingrese el código de la pregunta'
        }}
        value={query}
        handleChange={(e) => setQuery(e.target.value)}
        className='form-group'
      />
      <QuestionTypeOptions
        value={searchExamType}
        handleChange={(e) => setSearchExamType(e.target.value)}
        options={examTypeOptions}
        isRequired={false}
      />
    </div>
  )
}

export default AdvancedSearch
