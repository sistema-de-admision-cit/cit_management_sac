import { useState } from 'react'
import '../../../../../assets/styles/enrollments/modal-applicant-details.css'
import Modal from '../../../../core/global/molecules/Modal'

const ModalApplicantDetails = ({ student, parentsGuardians, onClose }) => {
  const [activeTab, setActiveTab] = useState('student')

  const renderStudentInfo = () => (
    <div className='tab-content'>
      <h2>Información del Estudiante</h2>
      <p><strong>Nombre:</strong> {student.firstName} {student.firstSurname} {student.secondSurname}</p>
      <p><strong>Fecha de Nacimiento:</strong> {student.birthDate}</p>
      <p><strong>Tipo de ID:</strong> {student.idType}</p>
      <p><strong>Número de ID:</strong> {student.idNumber}</p>
      <p><strong>Escuela Anterior:</strong> {student.previousSchool}</p>
      <p><strong>Adaptaciones:</strong> {student.hasAccommodations ? 'Sí' : 'No'}</p>
    </div>
  )

  const renderParentsGuardians = () => (
    <div className='tab-content'>
      <h2>Tutores Asociados</h2>
      {parentsGuardians.map((guardian) => (
        <div key={guardian.id} className='guardian-info'>
          <p><strong>Nombre:</strong> {guardian.firstName} {guardian.firstSurname} {guardian.secondSurname}</p>
          <p><strong>Tipo de ID:</strong> {guardian.idType}</p>
          <p><strong>Número de ID:</strong> {guardian.idNumber}</p>
          <p><strong>Teléfono:</strong> {guardian.phoneNumber}</p>
          <p><strong>Email:</strong> {guardian.email}</p>
          <p><strong>Dirección:</strong> {guardian.homeAddress}</p>
          <p><strong>Relación:</strong> {guardian.relationship}</p>
        </div>
      ))}
    </div>
  )

  const renderAdditionalInfo = () => (
    <div className='tab-content'>
      <h2>Información Adicional</h2>
      <p>Esta pestaña puede contener información adicional relevante para el solicitante.</p>
    </div>
  )

  return (
    <Modal onClose={onClose}>
      <div className='modal-tabs'>
        <button
          className={`tab-button ${activeTab === 'student' ? 'active' : ''}`}
          onClick={() => setActiveTab('student')}
        >
          Información del Estudiante
        </button>
        <button
          className={`tab-button ${activeTab === 'guardians' ? 'active' : ''}`}
          onClick={() => setActiveTab('guardians')}
        >
          Tutores Asociados
        </button>
        <button
          className={`tab-button ${activeTab === 'additional' ? 'active' : ''}`}
          onClick={() => setActiveTab('additional')}
        >
          Información Adicional
        </button>
      </div>
      {activeTab === 'student' && renderStudentInfo()}
      {activeTab === 'guardians' && renderParentsGuardians()}
      {activeTab === 'additional' && renderAdditionalInfo()}
    </Modal>
  )
}

export default ModalApplicantDetails
