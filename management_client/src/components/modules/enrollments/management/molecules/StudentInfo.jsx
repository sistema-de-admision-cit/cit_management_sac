import { formatDate } from '../helpers/helpers'

const StudentInfo = ({ student }) => (
  <div className='tab-content'>
    <p>
      <strong>Nombre:</strong> {student.person.firstName} {student.person.firstSurname} {student.person.secondSurname}
    </p>
    <p><strong>Fecha de Nacimiento:</strong> {formatDate(new Date(student.birthDate))}</p>
    <p><strong>Tipo de ID:</strong> {student.person.idType}</p>
    <p><strong>Número de ID:</strong> {student.person.idNumber}</p>
    <p><strong>Promedio de Notas:</strong> {student.previousGrades}</p>
    <p><strong>Escuela Anterior:</strong> {student.previousSchool}</p>
    <p><strong>Adaptaciones:</strong> {student.hasAccommodations ? 'Sí' : 'No'}</p>
  </div>
)

export default StudentInfo
