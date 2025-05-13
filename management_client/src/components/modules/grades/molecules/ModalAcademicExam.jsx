import { useState } from 'react'
import Button from '../../../core/global/atoms/Button'
import { generateAcademicExamPDF } from '../helpers/handlers'
import '../../../../assets/styles/grades/modal-grade.css'

const ModalAcademicExam = ({ grade, onClose }) => {
  const [loading] = useState(false)

  const handleDownload = async (e) => {
    generateAcademicExamPDF(grade)
  }

  return (
    <div className='modal-overlay'>
      <div className='modal-content'>
        <div className='modal-header'>
          <h2>Resultado Examen Académico</h2>
        </div>
        <div className='student-info'>
          <p><strong>Cédula:</strong> {grade.person.idNumber}</p>
          <p><strong>Estudiante:</strong> {grade.person.firstName} {grade.person.firstSurname}</p>
          <p><strong>Nota obtenida:</strong> {grade.academicExams[0].grade}</p>
        </div>

        <form className='grade-form'>
          <div className='form-actions'>
            <Button
              type='button'
              onClick={handleDownload}
              className='download-button'
              disabled={loading}
            >
              Descargar Examen
            </Button>
          </div>
          <div className='form-actions'>
            <Button
              type='button'
              onClick={onClose}
              className='cancel-button'
            >
              Cerrar
            </Button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default ModalAcademicExam
