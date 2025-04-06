import { useState } from 'react'
import Button from '../../../core/global/atoms/Button'
import Spinner from '../../../core/global/atoms/Spinner'
import useMessages from '../../../core/global/hooks/useMessages'
import EnglishGradesRow from '../molecules/EnglishGradesRow'
import '../../../../assets/styles/grades/grades-table.css'

const EnglishGradesTable = ({ grades, loading }) => {
  const [currentPage, setCurrentPage] = useState(1)
  const [activeModal, setActiveModal] = useState(null)
  const [selectedExam, setSelectedExam] = useState(null)
  const [selectedStudent, setSelectedStudent] = useState(null)

  const itemsPerPage = 10
  const indexOfLastItem = currentPage * itemsPerPage
  const indexOfFirstItem = indexOfLastItem - itemsPerPage
  const currentGrades = grades?.slice(indexOfFirstItem, indexOfLastItem) // New
  // const currentGrades = grades?.slice(indexOfFirstItem, indexOfFirstItem + itemsPerPage)  // Old
  const totalPages = Math.ceil(grades?.length / itemsPerPage)
  const paginate = (pageNumber) => setCurrentPage(pageNumber)

  return (
    <div className='grades-table-container'>
      <table className='grades-table'>
        <thead>
          <tr>
            <th>Cédula</th>
            <th>Nombre</th>
            <th>Primer Apellido</th>
            <th>Segundo Apellido</th>
            <th>Fecha de inscripcion</th>
            <th>Examen de Ingles</th>
          </tr>
        </thead>
        <tbody>
          {loading
            ? (
              <tr>
                <td colSpan='9'>
                  <Spinner />
                </td>
              </tr>
              )
            : currentGrades?.length > 0
              ? (
                  currentGrades.map((grade, index) => (
                    <EnglishGradesRow
                      key={`${grade.student.id}-${index}`}
                      grade={grade}
                    />
                  ))
                )
              : (
                <tr>
                  <td colSpan='9' className='no-students'>
                    No hay estudiantes registrados
                  </td>
                </tr>
                )}
        </tbody>
      </table>
      {totalPages > 1 && (
        <div className='pagination'>
          {Array.from({ length: totalPages }, (_, i) => i + 1).map((number) => (
            <Button
              key={number}
              onClick={() => setCurrentPage(number)}
              className={currentPage === number ? 'active' : ''}
            >
              {number}
            </Button>
          ))}
        </div>
      )}
    </div>
  )
}

export default EnglishGradesTable
