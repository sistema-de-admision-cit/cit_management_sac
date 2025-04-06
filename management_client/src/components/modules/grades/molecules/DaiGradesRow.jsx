import Button from '../../../core/global/atoms/Button'

const DAIGradesRow = ({ grade, onDAIClick}) => {
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
      <td>{formatDate(grade.daiExams[0].exam.examDate)}</td>
      {/* Examen DAI */}
      <td>
        <div className="exam-cell">
          <Button
            className='exam-button dai'
            onClick={() => onDAIClick()}
          >
            Calificar
          </Button>
        </div>
      </td>
      <td>{grade.daiExams[0].reviewed === true? 'Revisado' : 'No Revisado'}</td>
    </tr>
  )
}

export default DAIGradesRow
