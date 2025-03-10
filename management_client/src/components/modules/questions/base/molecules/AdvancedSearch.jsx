import QuestionTypeOptions from './QuestionTypeOptions'

const AdvancedSearch = ({ query, setQuery, searchExamType, setSearchExamType, examTypeOptions }) => {
  return (
    <div className='advanced-search-container'>
      <QuestionTypeOptions
        value={searchExamType}
        handleChange={(e) => setSearchExamType(e.target.value)}
        options={examTypeOptions}
        isRequired={false}
      />
      {/* todo: consider adding more advanced search options */}
    </div>
  )
}

export default AdvancedSearch
