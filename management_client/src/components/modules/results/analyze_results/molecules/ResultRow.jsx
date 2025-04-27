import Button from '../../../../core/global/atoms/Button'
import { formatDate, formatAcademicGrade, formatStatus } from '../helpers/helpers'

const ResultRow = ({ result, onResultClick }) => {
  return (
    <tr>
      <td>
        <Button
          className='applicant-id-button'
          onClick={() => onResultClick(result.idNumber)}
          ariaLabel={`Ver detalles del estudiante con cédula ${result.idNumber}`}
        >
          {result.idNumber}
        </Button>
      </td>
      <td>{result.firstName}</td>
      <td>{result.firstSurname}</td>
      <td>{result.secondSurname}</td>
      <td>{formatDate(result.examDate || '')}</td>
      <td>{formatAcademicGrade(result.gradeToEnroll) || ''}</td>
      <td>{result.finalGrade}</td>
      <td>{formatStatus(result.status) || ''}</td>
    </tr>
  )
}

export default ResultRow
