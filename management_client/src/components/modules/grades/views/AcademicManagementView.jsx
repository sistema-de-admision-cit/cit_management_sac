import { useEffect, useState } from 'react'
import SectionLayout from '../../../core/global/molecules/SectionLayout'
import AcademicGradesTable from '../organisms/AcademicGradesTable'
import AcademicSearchBar from '../molecules/AcademicSearchBar'
import '../../../../assets/styles/grades/grades-management-view.css'
import useMessages from '../../../core/global/hooks/useMessages'
import {
  handleGetAllAcademicGrades,
  handleSearchAcademicGrades,
  handleGetTotalGradesAcademicPages,
  handleGetTotalAcademicGradesSearchPages
} from '../helpers/handlers.js'

const AcademicGradesManagementView = () => {
  const { setErrorMessage, setSuccessMessage } = useMessages()
  const [currentPage, setCurrentPage] = useState(0)
  const [pageSize] = useState(10)
  const [totalPages, setTotalPages] = useState(0)
  const [grades, setGrades] = useState([])
  const [loading, setLoading] = useState(false)

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
      handleGetAllAcademicGrades(currentPage, pageSize, setGrades, setLoading, setErrorMessage)
      handleGetTotalGradesAcademicPages(setTotalPages, pageSize)
      return
    }
    setSearching(true)
    setSearchValue(search)
    handleSearchAcademicGrades(
      currentSearchPage,
      pageSize,
      search,
      setGrades,
      setLoading,
      setErrorMessage,
      setSuccessMessage
    )
    handleGetTotalAcademicGradesSearchPages(search, pageSize, setTotalPages)
  }

  const loadGrades = (page) => {
    setSearching(false)
    setCurrentPage(page)
    setCurrentSearchPage(0)
    setSearchValue('')
    handleGetAllAcademicGrades(
      page,
      pageSize,
      setGrades,
      setLoading,
      setErrorMessage
    )
    handleGetTotalGradesAcademicPages(setTotalPages, pageSize)
  }

  const onClickPage = (number) => {
    if (searching) {
      setCurrentSearchPage(number)
    } else {
      setCurrentPage(number)
    }
  }

  return (
    <SectionLayout title='Resultados de exámenes Académicos'>
      <div className='grade-management-view'>
        <h1>Resultados de Exámenes Académicos</h1>
        <p className='description'>
          Aquí puedes visualizar los resultados de los Exámenes Académicos.
        </p>
        <AcademicSearchBar
          onSearch={(value) => {
            if (value.trim() === '') {
              loadGrades(0) // recarga todos si el campo se vacía
            } else {
              handleSearchAcademicGrades(
                value,
                setGrades,
                setLoading,
                setErrorMessage,
                setSuccessMessage
              )
            }
          }}
        />
        <AcademicGradesTable
          grades={grades}
          loading={loading}
          onPageChange={onClickPage}
          currentPage={currentPage}
          totalPages={totalPages}
        />
      </div>
    </SectionLayout>
  )
}

export default AcademicGradesManagementView
