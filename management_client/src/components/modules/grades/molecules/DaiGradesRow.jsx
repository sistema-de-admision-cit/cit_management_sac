import Button from '../../../core/global/atoms/Button'

const DAIGradesRow = ({ grade, onDAIClick }) => {
  const formatDate = (dateString) => {
    return dateString ? new Date(dateString).toLocaleDateString() : 'N/A'
  }
  // TODO: Put data on the las column
  return (
    <tr>
      <td>{grade.student.person.idNumber}</td>
      <td>{grade.student.person.firstName}</td>
      <td>{grade.student.person.firstSurname}</td>
      <td>{grade.student.person.secondSurname || 'N/A'}</td>
      <td>{formatDate(grade.enrollmentDate)}</td>
      {/* Examen DAI */}
      <td>
        <div className="exam-cell">
          <Button
            className='exam-button dai'
            onClick={() => onDAIClick(grade.DAI ?? 'Pendiente', grade.student)}
          >
            Calificar
          </Button>
        </div>
      </td>
      <td>Revisado?</td>
    </tr>
  )
}

export default DAIGradesRow
