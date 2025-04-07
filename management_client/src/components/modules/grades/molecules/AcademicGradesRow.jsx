import Button from '../../../core/global/atoms/Button'
import { formatDate } from '../helpers/helpers'

const AcademicGradesRow = ({ grade, onAcademicClick }) => {
  return (
    <tr>
      <td>{grade.person.idNumber}</td>
      <td>{grade.person.firstName}</td>
      <td>{grade.person.firstSurname}</td>
      <td>{grade.person.secondSurname || 'N/A'}</td>
      <td>{formatDate(grade.academicExams[0]?.exam.examDate || '')}</td>
      {/* Examen de Academico */}
      <td>
        <div className='exam-cell'>
          <Button
            className='exam-button'
            onClick={() => onAcademicClick()}
          >
            Ver resultado
          </Button>
        </div>
      </td>
    </tr>
  )
}

export default AcademicGradesRow
