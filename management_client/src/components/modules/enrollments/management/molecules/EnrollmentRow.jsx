import Button from '../../../../core/global/atoms/Button'

const EnrollmentRow = ({ enrollment, index, onStudentIdClick }) => (
  <tr>
    <td>
      <Button
        className='applicant-id-button'
        onClick={() => onStudentIdClick(enrollment.student)}
        ariaLabel={`Ver detalles del estudiante con cédula ${enrollment.student.person.idNumber}`}
      >
        {enrollment.student.person.idNumber}
      </Button>
    </td>
    <td>{enrollment.student.person.firstName}</td>
    <td>{enrollment.student.person.firstSurname}</td>
    <td>{enrollment.student.person.secondSurname}</td>
    <td>{enrollment.student.previousSchool}</td>
    <td>{enrollment.student.hasAccommodations ? 'Sí' : 'No'}</td>
  </tr>
)

export default EnrollmentRow
