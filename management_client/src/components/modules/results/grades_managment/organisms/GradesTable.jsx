import { useState } from 'react'
import GradesRow from '../molecules/GradesRow'
import '../../../../../assets/styles/results/grades/grades-table.css'
import Button from '../../../../core/global/atoms/Button'
import Spinner from '../../../../core/global/atoms/Spinner'
import ModalEnglishExam from '../molecules/ModalEnglishExam'
import ModalAcademicExam from '../molecules/ModalAcademicExam'
import ModalDAIExam from '../molecules/ModalDaiExam'
import {
  handleSaveEnglishLevel,
  handleSaveAcademicGrade,
  handleSaveDAIComment
} from '../helpers/handlers'
import useMessages from '../../../../core/global/hooks/useMessages'


const GradesTable = ({ grades, onStudentIdClick, loading }) => {
  const [currentPage, setCurrentPage] = useState(1)
  const [activeModal, setActiveModal] = useState(null)
  const [selectedExam, setSelectedExam] = useState(null)
  const [selectedStudent, setSelectedStudent] = useState(null)
  const { setErrorMessage, setSuccessMessage } = useMessages()

  const itemsPerPage = 10
  const indexOfLastItem = currentPage * itemsPerPage
  const indexOfFirstItem = indexOfLastItem - itemsPerPage
  const currentGrades = grades?.slice(indexOfFirstItem, indexOfLastItem) // New
  //const currentGrades = grades?.slice(indexOfFirstItem, indexOfFirstItem + itemsPerPage)  // Old
  const totalPages = Math.ceil(grades?.length / itemsPerPage)
  const paginate = (pageNumber) => setCurrentPage(pageNumber)

  const handleOpenModal = (modalType, examData, student) => {
    setSelectedExam(examData)
    setSelectedStudent(student)
    setActiveModal(modalType)
  }

  const handleCloseModal = () => {
    setActiveModal(null);
    setSelectedExam(null);
    setSelectedStudent(null);
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
            <th>Examen de Inglés</th>
            <th>Examen Académico</th>
            <th>Examen DAI</th>
            <th>Nota Final</th>
          </tr>
        </thead>
        <tbody>
          {loading ? (
            <tr>
              <td colSpan='9'>
                <Spinner />
              </td>
            </tr>
          ) : currentGrades?.length > 0 ? (
            currentGrades.map((grade, index) => (
              <GradesRow
                key={`${grade.student.id}-${index}`}
                grade={grade}
                onEnglishClick={() => handleOpenModal('english', grade.english, grade.student)}
                onAcademicClick={() => handleOpenModal('academic', grade.academic, grade.student)}
                onDAIClick={() => handleOpenModal('dai', grade.DAI, grade.student)}
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
      {/* Modales */}
      {activeModal === 'english' && (
        <ModalEnglishExam
          examData={selectedExam}
          student={selectedStudent}
          onClose={handleCloseModal}
          onSave={handleSaveEnglishLevel}
        />
      )}

      {activeModal === 'academic' && (
        <ModalAcademicExam
          examData={selectedExam}
          student={selectedStudent}
          onClose={handleCloseModal}
          onSave={handleSaveAcademicGrade}
        />
      )}

      {activeModal === 'dai' && (
        <ModalDAIExam
          examData={selectedExam}
          student={selectedStudent}
          onClose={handleCloseModal}
          onSave={handleSaveDAIComment}
        />
      )}
    </div>
  )
}

export default GradesTable