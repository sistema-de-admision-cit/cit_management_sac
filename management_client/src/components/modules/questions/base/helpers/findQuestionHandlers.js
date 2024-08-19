export const handleInputChange = (e, setQuery) => {
  const searchTerm = e.target.value
  setQuery(searchTerm)
}

export const handleExamTypeChange = (e, setSearchExamType) => {
  const examType = e.target.value
  setSearchExamType(examType)
}

export const setQuestions = (questions, lookingFor, onResultsUpdate, setSuggestions) => {
  lookingFor === 'delete' ? onResultsUpdate(questions) : setSuggestions(questions)
}

export const handleSuggestionClick = (item, onQuestionFound, setQuery, setSuggestions) => {
  onQuestionFound(item)
  setQuery('')
  setSuggestions([])
}

export const handleAdvancedSearch = (showAdvancedSearch, setShowAdvancedSearch) => {
  setShowAdvancedSearch(!showAdvancedSearch)
}
