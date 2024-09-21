import React, { useState } from 'react'
import EnrollmentRow from '../molecules/EnrollmentRow'
import '../../../../../assets/styles/enrollments/enrollment-table.css'
import Button from '../../../../core/global/atoms/Button'
import Spinner from '../../../../core/global/atoms/Spinner'

const EnrollmentTable = ({ enrollments, onStudentIdClick, loading }) => {
  const [currentPage, setCurrentPage] = useState(1)
  const itemsPerPage = 10

  const indexOfLastItem = currentPage * itemsPerPage
  const indexOfFirstItem = indexOfLastItem - itemsPerPage
  const currentEnrollments = enrollments?.slice(indexOfFirstItem, indexOfFirstItem + itemsPerPage)

  const totalPages = Math.ceil(enrollments?.length / itemsPerPage)

  const paginate = (pageNumber) => setCurrentPage(pageNumber)

  return (
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
                <td colSpan='4'>
                  <Spinner />
                </td>
              </tr>
            </tbody>
            )
          : (
            <tbody>
              {currentEnrollments
                ? (
                    currentEnrollments.map((enrollment, index) => (
                      <EnrollmentRow
                        key={enrollment.id}
                        enrollment={enrollment}
                        index={indexOfFirstItem + index}
                        onStudentIdClick={onStudentIdClick}
                      />
                    ))
                  )
                : (
                  <tr>
                    <td colSpan='4' className='no-applicants'>
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
            onClick={() => paginate(number)}
            className={currentPage === number ? 'active' : ''}
          >
            {number}
          </Button>
        ))}
      </div>
    </div>
  )
}

export default EnrollmentTable
