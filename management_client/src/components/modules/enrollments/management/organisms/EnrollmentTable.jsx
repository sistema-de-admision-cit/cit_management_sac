import React, { useState } from 'react'
import EnrollmentRow from '../molecules/EnrollmentRow'
import '../../../../../assets/styles/enrollments/enrollment-table.css'
import Button from '../../../../core/global/atoms/Button'

const EnrollmentTable = ({ applicants, onCedulaClick, onDateChange, onWhatsappChange, onDocClick }) => {
  const [currentPage, setCurrentPage] = useState(1)
  const itemsPerPage = 5

  // Calcula el índice inicial y final de los elementos a mostrar en la página actual
  const indexOfLastItem = currentPage * itemsPerPage
  const indexOfFirstItem = indexOfLastItem - itemsPerPage
  const currentApplicants = applicants.slice(indexOfFirstItem, indexOfLastItem)

  // Calcula el número total de páginas
  const totalPages = Math.ceil(applicants.length / itemsPerPage)

  // Función para cambiar de página
  const paginate = (pageNumber) => setCurrentPage(pageNumber)

  return (
    <div>
      <table className='enrollment-table'>
        <thead>
          <tr>
            <th>Cédula</th>
            <th>Nombre</th>
            <th>Primer Apellido</th>
            <th>Segundo Apellido</th>
            <th>Fecha Inscripción</th>
            <th>Fecha Entrevista</th>
            <th>Whatsapp</th>
            <th>Notas</th>
            <th>Adecuación</th>
          </tr>
        </thead>
        <tbody>
          {currentApplicants.map((applicant, index) => (
            <EnrollmentRow
              key={applicant.cedula}
              applicant={applicant}
              index={indexOfFirstItem + index}
              onCedulaClick={onCedulaClick}
              onDateChange={onDateChange}
              onWhatsappChange={onWhatsappChange}
              onDocClick={onDocClick}
            />
          ))}
        </tbody>
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
