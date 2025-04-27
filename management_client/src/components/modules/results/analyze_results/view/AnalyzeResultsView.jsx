import { useEffect, useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import useMessages from '../../../../core/global/hooks/useMessages'
import ResultGradesTable from '../organisms/ResultsTable'
import ResultSearchBar from '../molecules/ResultSearchBar'
import { handleGetAllResults, handleSearchResults } from '../helpers/handlers'
import '../../../../../assets/styles/results/analyze_results/analyze-results-view.css'

const AnalyzeResultsView = () => {
  const [loading, setLoading] = useState(false)
  const [results, setResults] = useState([])
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(1)
  const { setErrorMessage } = useMessages()

  const loadResults = async (page = 0) => {
    const result = await handleGetAllResults(
      page,
      setResults,
      setLoading,
      setErrorMessage
    )
    if (result) {
      setTotalPages(result.totalPages)
      setCurrentPage(result.currentPage)
    }
  }

  const handleSearch = async (searchValue) => {
    if (searchValue.trim() === '') {
      loadResults(0)
      return
    }

    await handleSearchResults(
      searchValue,
      setResults,
      setLoading,
      setErrorMessage,
      setTotalPages,
      setCurrentPage
    )
  }

  // Cargar datos iniciales
  useEffect(() => {
    loadResults(0)
  }, [])

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      loadResults(newPage)
    }
  }

  return (
    <SectionLayout title='Gestión de Resultados de estudiantes'>
      <div className='result-management-view'>
        <h1>Resultados de examenes Academico</h1>
        <p className='description'>
          Aquí puedes visualizar los resultados de los exámenes academicos.
        </p>
        <ResultSearchBar onSearch={handleSearch} />
        <ResultGradesTable
          results={results}
          loading={loading}
          onPageChange={handlePageChange}
          currentPage={currentPage}
          totalPages={totalPages}
        />
      </div>
    </SectionLayout>
  )
}

export default AnalyzeResultsView
