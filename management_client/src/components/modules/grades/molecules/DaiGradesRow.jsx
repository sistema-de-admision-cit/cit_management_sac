import Button from '../../../core/global/atoms/Button'
import { formatDate } from '../helpers/helpers'

const DAIGradesRow = ({ grade, onDAIClick }) => {
  return (
    <tr>
      <td>{grade.person.idNumber}</td>
      <td>{grade.person.firstName}</td>
      <td>{grade.person.firstSurname}</td>
      <td>{grade.person.secondSurname || 'N/A'}</td>
      <td>{formatDate(grade.academicExams[0]?.exam.examDate || '')}</td>
      {/* Examen DAI */}
      <td>
        <div className='exam-cell'>
          <Button
            className='exam-button dai'
            onClick={() => onDAIClick()}
          >
            Calificar
          </Button>
        </div>
      </td>
      <td>{grade.daiExams[0]?.reviewed === true ? 'Revisado' : 'No Revisado'}</td>
    </tr>
  )
}

export default DAIGradesRow
