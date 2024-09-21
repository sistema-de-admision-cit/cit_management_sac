const EnrollmentRow = ({ enrollment, index, onStudentIdClick }) => (
  <tr>
    <td className='applicant-id' onClick={() => onStudentIdClick(enrollment)}>{enrollment.idNumber}</td>
    <td>{enrollment.firstName}</td>
    <td>{enrollment.firstSurname}</td>
    <td>{enrollment.secondSurname}</td>
    <td>{enrollment.previousSchool}</td>
    <td>{enrollment.hasAccommodations ? 'Sí' : 'No'}</td>
  </tr>
)

export default EnrollmentRow
