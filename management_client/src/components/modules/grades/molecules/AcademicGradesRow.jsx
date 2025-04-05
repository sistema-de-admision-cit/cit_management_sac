import Button from '../../../core/global/atoms/Button'

const AcademicGradesRow = ({ grade, onAcademicClick}) => {
  function formatDate(dateString) {
    const date = new Date(dateString);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Meses son 0-11
    const year = date.getFullYear();
    return `${day}/${month}/${year}`; // Formato: DD/MM/AAAA
  }
  return (
    <tr>
      <td>{grade.person.idNumber}</td>
      <td>{grade.person.firstName}</td>
      <td>{grade.person.firstSurname}</td>
      <td>{grade.person.secondSurname || 'N/A'}</td>
      <td>{formatDate(grade.academicExams[0].exam.examDate)}</td>
      {/* Examen de Academico */}
      <td>
        <div className="exam-cell">
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
