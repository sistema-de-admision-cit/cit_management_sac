import Button from '../../../../core/global/atoms/Button'

const GradesRow = ({ grade, onEnglishClick, onAcademicClick, onDAIClick }) => {
  const formatDate = (dateString) => {
    return dateString ? new Date(dateString).toLocaleDateString() : 'N/A'
  }

  const getExamStatus = (exam, examType) => {
    if (!exam) return 'No presentado'

    switch (examType) {
      case 'english':
        return exam.level ? `Nivel ${exam.level}` : 'Pendiente'
      case 'academic':
      case 'DAI':
        return exam.grade ? `${exam.grade}%` : 'Pendiente'
      default:
        return 'Pendiente'
    }
  }

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
          <span className="exam-status">{
            grade.english?.level ? `${grade.english.level}` : 'Pendiente'
          }</span>
          <Button
            className='exam-button english'
            onClick={() => onEnglishClick(grade.english ?? 'Pendiente', grade.student)}
            ariaLabel={`Calificar inglés para ${grade.student.person.firstName}`}
          >
            Calificar
          </Button>
        </div>
      </td>

      <td>
        <div className="exam-cell">
          <span className="exam-status">{
            grade.academic?.grade ? `${grade.academic.grade}` : 'Pendiente'
          }</span>
          <Button
            className='exam-button academic'
            onClick={() => onAcademicClick(grade.academic ?? 'Pendiente', grade.student)}
          >
            Calificar
          </Button>
        </div>
      </td>

      <td>
        <div className="exam-cell">
          <span className="exam-status">{
            grade.DAI?.grade ? `${grade.DAI.grade}` : 'Pendiente'
          }</span>
          <Button
            className='exam-button dai'
            onClick={() => onDAIClick(grade.DAI ?? 'Pendiente', grade.student)}
          >
            Calificar
          </Button>
        </div>
      </td>
      <td>{grade.finalGrade}</td>
    </tr>
  )
}

export default GradesRow
