import { useEffect, useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import useMessages from '../../../../core/global/hooks/useMessages'
import ResultGradesTable from '../organisms/ResultsTable'
import ResultSearchBar from '../molecules/ResultSearchBar'
import { handleGetAllResults, handleSearchResults, handleGetTotalResultsPages, handleGetTotalResultsSearchPages } from '../helpers/handlers'
import '../../../../../assets/styles/results/analyze_results/analyze-results-view.css'

const AnalyzeResultsView = () => {
  const { setErrorMessage, setSuccessMessage } = useMessages()
  const [currentPage, setCurrentPage] = useState(0)
  const [pageSize] = useState(10)
  const [loading, setLoading] = useState(false)
  const [results, setResults] = useState([])
  const [totalPages, setTotalPages] = useState(1)

  const [currentSearchPage, setCurrentSearchPage] = useState(0)
  const [searching, setSearching] = useState(false)
  const [searchValue, setSearchValue] = useState('')

  useEffect(() => {
    loadResults(currentPage)
  }, [currentPage])

  useEffect(() => {
    if (searching) {
      onSearch(searchValue)
    }
  }, [currentSearchPage])

  const onSearch = (search) => {
    if (search.trim() === '') {
      setSearching(false)
      setCurrentSearchPage(0)
      setSearchValue('')
      handleGetAllResults(currentPage, pageSize, setResults, setLoading, setErrorMessage)
      handleGetTotalResultsPages(setTotalPages, pageSize)
      return
    }
    setSearching(true)
    setSearchValue(search)
    handleSearchResults(
      currentSearchPage,
      pageSize,
      search,
      setResults,
      setLoading,
      setErrorMessage,
      setSuccessMessage
    )
    handleGetTotalResultsSearchPages(search, pageSize, setTotalPages)
  }

  const loadResults = (page) => {
    setSearching(false)
    setCurrentPage(page)
    setCurrentSearchPage(0)
    setSearchValue('')
    handleGetAllResults(
      page,
      pageSize,
      setResults,
      setLoading,
      setErrorMessage
    )
    handleGetTotalResultsPages(setTotalPages, pageSize)
  }

  const onClickPage = (number) => {
    if (searching) {
      setCurrentSearchPage(number)
    } else {
      setCurrentPage(number)
    }
  }

  return (
    <SectionLayout title='Gestión de Resultados de estudiantes'>
      <div className='result-management-view'>
        <h1>Resultados del Proceso de Admisión</h1>
        <p className='description'>
          Aquí puedes visualizar los resultados del Proceso de Admisón, ademas de aceptar o rechazar a la persona Inscrita.
        </p>
        <ResultSearchBar
          onSearch={(value) => {
            if (value.trim() === '') {
              loadResults(0) // recarga todos si el campo se vacía
            } else {
              handleSearchResults(
                value,
                setResults,
                setLoading,
                setErrorMessage,
                setSuccessMessage
              )
            }
          }}
        />
        <ResultGradesTable
          results={results}
          loading={loading}
          onPageChange={onClickPage}
          currentPage={currentPage}
          totalPages={totalPages}
        />
      </div>
    </SectionLayout>
  )
}

export default AnalyzeResultsView
