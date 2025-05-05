import Button from '../../../../core/global/atoms/Button'

const StudentRow = ({ student, onStudentIdClick }) => (
  <tr>
    <td>
      <Button
        className='applicant-id-button'
        onClick={() => onStudentIdClick(student)}
        ariaLabel={`Ver detalles del estudiante con cédula ${student.person.idNumber}`}
      >
        {student.person.idNumber}
      </Button>
    </td>
    <td>{student.person.firstName}</td>
    <td>{student.person.firstSurname}</td>
    <td>{student.person.secondSurname}</td>
    <td>{student.previousSchool}</td>
    <td>{student.hasAccommodations ? 'Sí' : 'No'}</td>
  </tr>
)

export default StudentRow
