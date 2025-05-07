import { useEffect, useState } from 'react'
import SectionLayout from '../../../core/global/molecules/SectionLayout'
import useMessages from '../../../core/global/hooks/useMessages'
import DaiGradesTable from '../organisms/DaiGradesTable'
import DaiSearchBar from '../molecules/DaiSearchBar'
import { handleGetAllDaiGrades, handleSearchDAIGrades } from '../helpers/handlers'
import '../../../../assets/styles/grades/grades-management-view.css'

const DaiGradesManagementView = () => {
  const [loading, setLoading] = useState(false)
  const [allGrades, setAllGrades] = useState([])
  const [grades, setGrades] = useState([])
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(1)
  const [onlyReviewed, setOnlyReviewed] = useState(false)
  const { setErrorMessage, setSuccessMessage } = useMessages()

  const loadGrades = async (page = 0) => {
    const result = await handleGetAllDaiGrades(
      page,
      (data) => {
        setAllGrades(data) // guardamos todo
        filterGrades(data, onlyReviewed)
      },
      setLoading,
      setErrorMessage
    )
    if (result) {
      setTotalPages(result.totalPages)
      setCurrentPage(result.currentPage)
    }
  }

  const filterGrades = (gradesList, reviewedOnly) => {
    if (reviewedOnly) {
      const filtered = gradesList.filter(
        g => g.daiExams[0]?.reviewed === true
      )
      setGrades(filtered)
    } else {
      setGrades(gradesList)
    }
  }

  useEffect(() => {
    loadGrades(0)
  }, [])

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      loadGrades(newPage)
    }
  }

  const handleCheckboxChange = (checked) => {
    setOnlyReviewed(checked)
    filterGrades(allGrades, checked)
  }

  return (
    <SectionLayout title='Resultados de examenes DAI'>
      <div className='grade-management-view'>
        <h1>Resultados de examenes DAI</h1>
        <p className='description'>
          Aquí puedes visualizar los resultados de los exámenes DAI.
        </p>
        <DaiSearchBar
          onSearch={(value) => {
            if (value.trim() === '') {
              loadGrades(0)
            } else {
              handleSearchDAIGrades(
                value,
                (results) => {
                  setAllGrades(results)
                  filterGrades(results, onlyReviewed)
                },
                setLoading,
                setErrorMessage,
                setSuccessMessage
              )
            }
          }}
          onCheckedEvaluados={handleCheckboxChange}
        />
        <DaiGradesTable
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

export default DaiGradesManagementView
