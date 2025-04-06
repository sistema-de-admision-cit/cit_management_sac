import { useState } from 'react'
import Button from '../../../core/global/atoms/Button'
import Spinner from '../../../core/global/atoms/Spinner'
import useMessages from '../../../core/global/hooks/useMessages'
import AcademicGradesRow from '../molecules/AcademicGradesRow'
import ModalAcademicExam from '../molecules/ModalAcademicExam'
import '../../../../assets/styles/grades/grades-table.css'


const AcademicGradesTable = ({ grades, loading, onPageChange, currentPage, totalPages }) => {
  const [activeModal, setActiveModal] = useState(null)
  const [selectedExam, setSelectedExam] = useState(null)
  const { setErrorMessage, setSuccessMessage } = useMessages()
  const itemsPerPage = 25

  const handleOpenModal = (modalType, exam) => {
    setSelectedExam(exam)
    setActiveModal(modalType)
  }

  const handleCloseModal = () => {
    setSelectedExam(null) 
    setActiveModal(null)
  };

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
            <th>Examen Académico</th>
          </tr>
        </thead>
        <tbody>
          {loading ? (
            <tr>
              <td colSpan='9'>
                <Spinner />
              </td>
            </tr>
          ) : Array.isArray(grades) && grades.length > 0 ? (
            grades.map((grade, index) => (
              <AcademicGradesRow
                key={`${grade.person.id}-${index}`}
                grade={grade}
                onAcademicClick={() => handleOpenModal('academic', grades[index])}
              />
            ))
          ) : (
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
          <Button
            onClick={() => onPageChange(currentPage - 1)}
            disabled={currentPage === 0}
          >
            Anterior
          </Button>

          {Array.from({ length: Math.min(5, totalPages) }, (_, i) => {
            let pageNum;
            if (totalPages <= 5) {
              pageNum = i;
            } else if (currentPage <= 2) {
              pageNum = i;
            } else if (currentPage >= totalPages - 3) {
              pageNum = totalPages - 5 + i;
            } else {
              pageNum = currentPage - 2 + i;
            }

            return (
              <Button
                key={pageNum}
                onClick={() => onPageChange(pageNum)}
                className={currentPage === pageNum ? 'active' : ''}
              >
                {pageNum + 1}
              </Button>
            );
          })}

          <Button
            onClick={() => onPageChange(currentPage + 1)}
            disabled={currentPage >= totalPages - 1}
          >
            Siguiente
          </Button>
        </div>
      )}
      {/* Modal */}
      {activeModal === 'academic' && (
        <ModalAcademicExam
          grade={selectedExam}
          onClose={handleCloseModal}
        />
      )}
    </div>
  )
}

export default AcademicGradesTable