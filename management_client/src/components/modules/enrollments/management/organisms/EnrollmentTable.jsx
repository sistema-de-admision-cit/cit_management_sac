import React, { useState } from 'react'
import EnrollmentRow from '../molecules/EnrollmentRow'
import '../../../../../assets/styles/enrollments/enrollment-table.css'
import Button from '../../../../core/global/atoms/Button'
import Spinner from '../../../../core/global/atoms/Spinner'

const EnrollmentTable = ({ enrollments, onStudentIdClick, onDateChange, onWhatsappChange, onDocClick, loading }) => {
  const [currentPage, setCurrentPage] = useState(1)
  const itemsPerPage = 5

  const indexOfLastItem = currentPage * itemsPerPage
  const indexOfFirstItem = indexOfLastItem - itemsPerPage
  const currentEnrollments = enrollments?.slice(indexOfFirstItem, indexOfLastItem)

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
            <th>Fecha Entrevista</th>
            <th>Estado</th>
            <th>Whatsapp</th>
            <th>Notas</th>
            <th>Adecuación</th>
          </tr>
        </thead>
        {loading
          ? (
            <tbody>
              <tr>
                <td colSpan='9'>
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
                        onDateChange={onDateChange}
                        onWhatsappChange={onWhatsappChange}
                        onDocClick={onDocClick}
                      />
                    ))
                  )
                : (
                  <tr>
                    <td colSpan='9' className='no-applicants'>
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
