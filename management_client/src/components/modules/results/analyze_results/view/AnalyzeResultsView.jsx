import { useEffect, useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import useMessages from '../../../../core/global/hooks/useMessages'
import ResultGradesTable from '../organisms/ResultsTable'
import ResultSearchBar from '../molecules/ResultSearchBar'
import { handleGetAllResults, handleSearchResults, handleGetTotalResultsPages } from '../helpers/handlers'
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
    if (!searching) {
      // Si no estamos buscando, obtenemos todos los resultados
      handleGetAllResults(currentPage, pageSize, setResults, setLoading, setErrorMessage)
      handleGetTotalResultsPages(setTotalPages, pageSize)
    }
  }, [currentPage, searching])

  useEffect(() => {
    if (searching) {
      onSearch(searchValue)
    }
  }, [currentSearchPage])

  const onSearch = (search) => {
    if (search.trim() === '') {
      setSearching(false)
      setSearchValue('')
      handleGetAllResults(currentPage, pageSize, setResults, setLoading, setErrorMessage)
      handleGetTotalResultsPages(setTotalPages, pageSize)
      return
    }
    setSearching(true)
    setSearchValue(search)
    handleSearchResults(
      search,
      setResults,
      setLoading,
      setErrorMessage,
      setSuccessMessage,
      setTotalPages,
      setCurrentPage
    )
  }

  const loadResults = (pageNumber = 0) => {
    setCurrentPage(pageNumber)
    setSearching(false)
    setSearchValue('')
    setCurrentSearchPage(0)
    handleGetAllResults(pageNumber, pageSize, setResults, setLoading, setErrorMessage)
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
          Aquí puedes visualizar los resultados del Proceso de Admisón, ademas de aceptar o rechazar al estudiante.
        </p>
        <ResultSearchBar
          onSearch={(value) => {
            if (value.trim() === '') {
              loadResults(0) // Llamar a loadResults para cargar todos los resultados
            } else {
              onSearch(value) // Realizar la búsqueda
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
