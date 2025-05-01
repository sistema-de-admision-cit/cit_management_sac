import { useEffect, useState } from 'react'
import StudentRow from '../molecules/StudentRow'
import StudentSearchBar from '../molecules/StudentSearchBar'
import '../../../../../assets/styles/enrollments/enrollment-table.css'
import Button from '../../../../core/global/atoms/Button'
import Spinner from '../../../../core/global/atoms/Spinner'
import { handleGetStudents, handleGetTotalPages, handleSearch, handleGetTotalPagesForSearch } from '../helpers/handlers'

const StudentsTable = ({ onStudentIdClick, setErrorMessage }) => {
  const [currentPage, setCurrentPage] = useState(0)
  const [pageSize] = useState(10)
  const [totalPages, setTotalPages] = useState(0)
  const [students, setStudents] = useState([])
  const [loading, setLoading] = useState(false)

  const [currentSearchPage, setCurrentSearchPage] = useState(0)
  const [searching, setSearching] = useState(false)
  const [searchValue, setSearchValue] = useState('')

  useEffect(() => {
    handleGetStudents(currentPage, pageSize, setStudents, setLoading, setErrorMessage)
    handleGetTotalPages(setTotalPages, pageSize)
  }, [currentPage])

  const onSearch = (search) => {
    if (search === '') {
      setSearching(false)
      setCurrentSearchPage(0)
      setSearchValue('')
      handleGetStudents(currentPage, pageSize, setStudents, setLoading, setErrorMessage)
      handleGetTotalPages(setTotalPages, pageSize)
      return;
    }
    setSearching(true)
    setSearchValue(search)
    handleSearch(currentSearchPage, pageSize, search, setStudents, setLoading, setErrorMessage)
    handleGetTotalPagesForSearch(search, pageSize, setTotalPages)
  }

  const onClickPage = (number) => {
    if (searching) {
      setCurrentSearchPage(prev => number + 1)
      onSearch(searchValue)
    } else {
      setCurrentPage(number + 1)
    }
  }

  return (
    <>
      <StudentSearchBar onSearch={onSearch} />
      <div className='enrollment-table-container'>
        <table className='enrollment-table'>
          <thead>
            <tr>
              <th>Cédula</th>
              <th>Nombre</th>
              <th>Primer Apellido</th>
              <th>Segundo Apellido</th>
              <th>Escuela de Procedencia</th>
              <th>Posee Adaptaciones</th>
            </tr>
          </thead>
          {loading
            ? (
              <tbody>
                <tr>
                  <td colSpan='6'>
                    <Spinner />
                  </td>
                </tr>
              </tbody>
            )
            : (
              <tbody>
                {students.length > 0
                  ? (
                    students?.map((student) => (
                      <StudentRow
                        key={student.id}
                        student={student}
                        onStudentIdClick={onStudentIdClick}
                      />
                    ))
                  )
                  : (
                    <tr>
                      <td colSpan='6' className='no-applicants'>
                        No hay estudiantes inscritos
                      </td>
                    </tr>
                  )}
              </tbody>
            )}
        </table>
        <div className='pagination'>
          {totalPages > 18 ? (
            <>
              <Button
                key={0}
                onClick={() => onClickPage(-1)}
                disabled={false}
                className={currentPage + 1 === 1 ? 'active' : ''}
              >
                {'1'}
              </Button>
              ...
              {Array.from({ length: 18 }, (_, i) => currentPage + 2 + i < totalPages - 18 ? currentPage + 2 + i : totalPages - 18 + i).map((number) => (
                <Button
                  key={number}
                  onClick={() => onClickPage(number - 2)}
                  className={currentPage + 1 === number ? 'active' : ''}
                >
                  {number}
                </Button>
              ))}
              ...
              <Button
                key={0}
                onClick={() => onClickPage(totalPages - 2)}
                disabled={false}
                className={currentPage + 1 === totalPages ? 'active' : ''}
              >
                {totalPages}
              </Button>
            </>
          )
            :
            (
              Array.from({ length: totalPages }, (_, i) => i + 1).map((number) => (
                <Button
                  key={number}
                  onClick={() => onClickPage(number - 2)}
                  className={searching ? currentSearchPage + 1 === number ? 'active' : '' : currentPage + 1 === number ? 'active' : ''}
                >
                  {number}
                </Button>
              ))
            )}
        </div>
      </div>
    </>
  )
}

export default StudentsTable
