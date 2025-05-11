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

  const onSearch = (search) => {
    if (search.trim() === '') {
      setSearching(false)
      setCurrentSearchPage(0)
      setSearchValue('')
      loadGrades(currentPage)
      return
    }
    setSearching(true)
    setSearchValue(search)
    handleSearchDAIGrades(
      currentSearchPage,
      pageSize,
      search,
      (grades) => {
        setAllGrades(grades)
        if (onlyReviewed) {
          const filtered = grades.filter(g => g.daiExams.some(exam => exam.reviewed === true))
          setGrades(filtered)
        } else {
          setGrades(grades)
        }
      },
      setLoading,
      setErrorMessage,
      setSuccessMessage
    )
    handleGetTotalDAIGradesSearchPages(search, pageSize, setTotalPages)
  }


  const loadGrades = (page) => {
    setSearching(false)
    setCurrentPage(page)
    setCurrentSearchPage(0)
    setSearchValue('')
    handleGetAllDaiGrades(page, pageSize, (grades) => {
      setAllGrades(grades)
      if (onlyReviewed) {
        const filtered = grades.filter(g => g.daiExams.some(exam => exam.reviewed === true))
        setGrades(filtered)
      } else {
        setGrades(grades)
      }
    }, setLoading, setErrorMessage)
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
    if (checked) {
      const filtered = allGrades.filter(g => g.daiExams.some(exam => exam.reviewed === true))
      setGrades(filtered)
    } else {
      setGrades(allGrades)
    }
  }

  return (
    <SectionLayout title='Resultados de exámenes DAI'>
      <div className='grade-management-view'>
        <h1>Resultados de Exámenes DAI</h1>
        <p className='description'>
          Aquí puedes visualizar los resultados de los exámenes DAI.
        </p>
        <DaiSearchBar
          onSearch={(value) => {
            if (value.trim() === '') {
              loadGrades(0) // recarga todos si el campo se vacía
            } else {
              handleSearchDAIGrades(
                value,
                setGrades,
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
          onPageChange={onClickPage}
          currentPage={searching ? currentSearchPage : currentPage}
          totalPages={totalPages}
        />
      </div>
    </SectionLayout>
  )
}

export default DaiGradesManagementView
