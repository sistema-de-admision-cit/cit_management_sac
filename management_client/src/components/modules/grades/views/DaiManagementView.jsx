import { useEffect, useState } from 'react'
import SectionLayout from '../../../core/global/molecules/SectionLayout'
import DaiGradesTable from '../organisms/DaiGradesTable'
import DaiSearchBar from '../molecules/DaiSearchBar'
import '../../../../assets/styles/grades/grades-management-view.css'
import useMessages from '../../../core/global/hooks/useMessages'
import {
  handleGetAllDaiGrades,
  handleSearchDAIGrades,
  handleGetTotalGradesDAIPages,
  handleGetTotalDAIGradesSearchPages

} from '../helpers/handlers.js'

const DaiGradesManagementView = () => {
  const { setErrorMessage, setSuccessMessage } = useMessages()
  const [currentPage, setCurrentPage] = useState(0)
  const [allGrades, setAllGrades] = useState([])
  const [pageSize] = useState(10)
  const [totalPages, setTotalPages] = useState(0)
  const [grades, setGrades] = useState([])
  const [loading, setLoading] = useState(false)
  const [onlyReviewed, setOnlyReviewed] = useState(false)

  const [currentSearchPage, setCurrentSearchPage] = useState(0)
  const [searching, setSearching] = useState(false)
  const [searchValue, setSearchValue] = useState('')

  useEffect(() => {
    loadGrades(currentPage)
  }, [currentPage])

  useEffect(() => {
    if (searching) {
      onSearch(searchValue)
    }
  }, [currentSearchPage])

  const applyReviewedFilter = (gradesList, reviewed) => {
    return reviewed
      ? gradesList.filter(g => g.daiExams.some(exam => exam.reviewed === true))
      : gradesList
  }

  const onSearch = (search) => {
    const trimmed = search.trim()

    if (trimmed === '') {
      setSearching(false)
      setCurrentSearchPage(0)
      setSearchValue('')
      loadGrades(currentPage)
      return
    }

    setSearching(true)
    setSearchValue(trimmed)

    handleSearchDAIGrades(
      search,
      (grades) => {
        setAllGrades(grades)
        setGrades(onlyReviewed
          ? grades.filter(g => g.daiExams.some(exam => exam.reviewed))
          : grades
        )
      },
      setLoading,
      setErrorMessage,
      setSuccessMessage
    )
  }


  const loadGrades = (page) => {
    setSearching(false)
    setCurrentPage(page)
    setCurrentSearchPage(0)
    setSearchValue('')

    handleGetAllDaiGrades(
      page,
      pageSize,
      (results) => {
        setAllGrades(results)
        setGrades(applyReviewedFilter(results, onlyReviewed))
      },
      setLoading,
      setErrorMessage
    )

    handleGetTotalGradesDAIPages(setTotalPages, pageSize)
  }

  const onClickPage = (number) => {
    if (searching) {
      setCurrentSearchPage(number)
    } else {
      setCurrentPage(number)
    }
  }

  const handleCheckboxChange = (checked) => {
    setOnlyReviewed(checked)
    setGrades(applyReviewedFilter(allGrades, checked))
  }

  return (
    <SectionLayout title='Resultados de exámenes DAI'>
      <div className='grade-management-view'>
        <h1>Resultados de Exámenes DAI</h1>
        <p className='description'>
          Aquí puedes visualizar los resultados de los exámenes DAI.
        </p>
        <DaiSearchBar
          onSearch={onSearch}
          onCheckedEvaluados={handleCheckboxChange}
        />
        <DaiGradesTable
          grades={grades}
          loading={loading}
          onPageChange={onClickPage}
          currentPage={searching ? currentSearchPage : currentPage}
          totalPages={totalPages}
        />
      </div>
    </SectionLayout>
  )
}

export default DaiGradesManagementView