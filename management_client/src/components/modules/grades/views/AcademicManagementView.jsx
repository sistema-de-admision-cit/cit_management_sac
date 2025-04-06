import { useEffect, useState } from 'react'
import SectionLayout from '../../../core/global/molecules/SectionLayout'
import useMessages from '../../../core/global/hooks/useMessages'
import AcademicGradesTable from '../organisms/AcademicGradesTable'
import AcademicSearchBar from '../molecules/AcademicSearchBar'
import { handleGetAllAcademicGrades } from '../helpers/handlers'
import '../../../../assets/styles/grades/grades-management-view.css'

const AcademicGradesManagementView = () => {
  const [loading, setLoading] = useState(false)
  const [grades, setGrades] = useState([])
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(1)
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  const itemsPerPage = 25 // Número de elementos por página

  const loadGrades = async (page = 0) => {
    const result = await handleGetAllAcademicGrades(
      page,
      setGrades,
      setLoading,
      setErrorMessage
    )
    if (result) {
      setTotalPages(result.totalPages)
      setCurrentPage(result.currentPage)
    }
  }

  // Cargar datos iniciales
  useEffect(() => {
    loadGrades(0)
  }, [])

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      loadGrades(newPage)
    }
  }

  return (
    <SectionLayout title='Resultados de exámenes Académicos'>
      <div className='grade-management-view'>
        <h1>Resultados de examenes Academico</h1>
        <p className='description'>
          Aquí puedes visualizar los resultados de los exámenes academicos.
        </p>
        <AcademicSearchBar />
        <AcademicGradesTable
          grades={grades}
          loading={loading}
          onPageChange={handlePageChange}
          currentPage={currentPage}
          totalPages={totalPages}
        />
      </div>
    </SectionLayout>
  )
}

export default AcademicGradesManagementView
