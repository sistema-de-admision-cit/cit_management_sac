import Button from '../../../core/global/atoms/Button'

const EnglishGradesRow = ({ grade }) => {
  const formatDate = (dateString) => {
    return dateString ? new Date(dateString).toLocaleDateString() : 'N/A'
  }

  return (
    <tr>
      <td>{grade.student.person.idNumber}</td>
      <td>{grade.student.person.firstName}</td>
      <td>{grade.student.person.firstSurname}</td>
      <td>{grade.student.person.secondSurname || 'N/A'}</td>
      <td>{formatDate(grade.enrollmentDate)}</td>
      <td>{grade.english.level}</td>
    </tr>
  )
}

export default EnglishGradesRow
