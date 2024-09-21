const StudentInfo = ({ student }) => (
  <div className='tab-content'>
    <h2>Información de Aplicante</h2>
    <p><strong>Nombre:</strong> {student.firstName} {student.firstSurname} {student.secondSurname}</p>
    <p><strong>Fecha de Nacimiento:</strong> {student.birthDate}</p>
    <p><strong>Tipo de ID:</strong> {student.idType}</p>
    <p><strong>Número de ID:</strong> {student.idNumber}</p>
    <p><strong>Escuela Anterior:</strong> {student.previousSchool}</p>
    <p><strong>Adaptaciones:</strong> {student.hasAccommodations ? 'Sí' : 'No'}</p>
  </div>
)

export default StudentInfo
