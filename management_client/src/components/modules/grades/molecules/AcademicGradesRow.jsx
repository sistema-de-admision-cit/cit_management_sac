import Button from '../../../core/global/atoms/Button'

const AcademicGradesRow = ({ grade, onAcademicClick }) => {
  const formatDate = (dateString) => {
    return dateString ? new Date(dateString).toLocaleDateString() : 'N/A'
  }

  /*const getExamStatus = (exam, examType) => {
    if (!exam) {
      return 'No presentado'
    }else{
      return exam.grade ? `${exam.grade}%` : 'Pendiente'
    }
  }*/

  return (
    <tr>
      <td>{grade.student.person.idNumber}</td>
      <td>{grade.student.person.firstName}</td>
      <td>{grade.student.person.firstSurname}</td>
      <td>{grade.student.person.secondSurname || 'N/A'}</td>
      <td>{formatDate(grade.enrollmentDate)}</td>
      {/* Examen de Inglés */}
      <td>
        <div className="exam-cell">
          <Button
            className='exam-button'
            onClick={() => onAcademicClick(grade.academic, grade.student)}
          >
            Ver resultado
          </Button>
        </div>
      </td>
    </tr>
  )
}

export default AcademicGradesRow
