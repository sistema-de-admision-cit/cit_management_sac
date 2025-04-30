import { useEffect, useState } from 'react'
import EnrollmentRow from '../molecules/EnrollmentRow'
import EnrollemntSearchBar from '../molecules/EnrollmentSearchBar'
import '../../../../../assets/styles/enrollments/enrollment-table.css'
import Button from '../../../../core/global/atoms/Button'
import Spinner from '../../../../core/global/atoms/Spinner'
import { handleGetEnrollments, handleGetTotalPages, handleSearch } from '../helpers/handlers'

const EnrollmentTable = ({ onStudentIdClick, setErrorMessage }) => {
  const [currentPage, setCurrentPage] = useState(0)
  const [pageSize] = useState(10)
  const [totalPages, setTotalPages] = useState(0)
  const [enrollments, setEnrollments] = useState([])
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    handleGetEnrollments(currentPage, pageSize, setEnrollments, setLoading, setErrorMessage)
    handleGetTotalPages(setTotalPages, pageSize)
  }, [])

  useEffect(() => {
    handleGetEnrollments(currentPage, pageSize, setEnrollments, setLoading, setErrorMessage)
  }, [currentPage])

  return (
    <>
      <EnrollemntSearchBar onSearch={(search) => { handleSearch(search, setEnrollments, pageSize, setTotalPages)}} />
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
                {enrollments
                  ? (
                    enrollments?.map((enrollment, index) => (
                      <EnrollmentRow
                        key={enrollment.id}
                        enrollment={enrollment}
                        onStudentIdClick={onStudentIdClick}
                      />
                    ))
                  )
                  : (
                    <tr>
                      <td colSpan='6' className='no-applicants'>
                        No hay aspirantes
                      </td>
                    </tr>
                  )}
              </tbody>
            )}
        </table>
        <div className='pagination'>
          {Array.from({ length: totalPages }, (_, i) => i + 1).map((number) => (
            <Button
              key={number}
              onClick={() => setCurrentPage(number - 1)}
              className={currentPage + 1 === number ? 'active' : ''}
            >
              {number}
            </Button>
          ))}
        </div>
      </div>
    </>
  )
}

export default EnrollmentTable
