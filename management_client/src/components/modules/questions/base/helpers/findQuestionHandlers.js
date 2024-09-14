export const handleInputChange = (e, setQuery) => {
  const searchTerm = e.target.value
  setQuery(searchTerm)
}

export const handleExamTypeChange = (e, setSearchExamType) => {
  const examType = e.target.value
  setSearchExamType(examType)
}

export const handleAdvancedSearch = (showAdvancedSearch, setShowAdvancedSearch) => {
  setShowAdvancedSearch(!showAdvancedSearch)
}
