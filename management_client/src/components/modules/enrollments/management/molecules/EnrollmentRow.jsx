import Button from '../../../../core/global/atoms/Button'

const EnrollmentRow = ({ enrollment, index, onStudentIdClick }) => (
  <tr>
    <td>
      <Button
        className='applicant-id-button'
        onClick={() => onStudentIdClick(enrollment)}
        ariaLabel={`Ver detalles del estudiante con cédula ${enrollment.idNumber}`}
      >
        {enrollment.idNumber}
      </Button>
    </td>
    <td>{enrollment.firstName}</td>
    <td>{enrollment.firstSurname}</td>
    <td>{enrollment.secondSurname}</td>
    <td>{enrollment.previousSchool}</td>
    <td>{enrollment.hasAccommodations ? 'Sí' : 'No'}</td>
  </tr>
)

export default EnrollmentRow
