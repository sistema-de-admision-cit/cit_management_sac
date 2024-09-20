import { useState } from 'react'
import '../../../../../assets/styles/enrollments/modal-applicant-details.css'
import Modal from '../../../../core/global/molecules/Modal'
import Button from '../../../../core/global/atoms/Button'
import { guardianTabText, buildGuardianAddress } from '../helpers/helpers'

const ModalApplicantDetails = ({ student, parentsGuardians, onClose }) => {
  const [activeTab, setActiveTab] = useState('student')

  const renderStudentInfo = () => (

    <div className='tab-content'>
      <h2>Informacion de Aplicante</h2>
      <p><strong>Nombre:</strong> {student.firstName} {student.firstSurname} {student.secondSurname}</p>
      <p><strong>Fecha de Nacimiento:</strong> {student.birthDate}</p>
      <p><strong>Tipo de ID:</strong> {student.idType}</p>
      <p><strong>Número de ID:</strong> {student.studendtId}</p>
      <p><strong>Escuela Anterior:</strong> {student.previousSchool}</p>
      <p><strong>Adaptaciones:</strong> {student.hasAccommodations ? 'Sí' : 'No'}</p>
    </div>
  )

  const renderGuardianInfo = (guardian) => {
    return (
      <div className='tab-content'>
        <h2>{guardianTabText[guardian.relationship]}</h2>
        <p><strong>Nombre:</strong> {guardian.firstName} {guardian.firstSurname} {guardian.secondSurname}</p>
        <p><strong>Tipo de ID:</strong> {guardian.idType}</p>
        <p><strong>Número de ID:</strong> {guardian.idNumber}</p>
        <p><strong>Teléfono:</strong> {guardian.phoneNumber}</p>
        <p><strong>Email:</strong> {guardian.email}</p>
        <p><strong>Dirección:</strong> {buildGuardianAddress(guardian.addresses[0])}</p>
      </div>
    )
  }

  return (
    <Modal onClose={onClose}>
      <h2>Detalles del Aspirante</h2>
      <div className='modal-tabs'>
        <Button
          className={`tab-button ${activeTab === 'student' ? 'active' : ''}`}
          onClick={() => setActiveTab('student')}
        >
          Informacion del Estudiante
        </Button>
        {parentsGuardians.map((guardian) => (
          <Button
            key={guardian.id}
            className={`tab-button ${activeTab === guardian.id ? 'active' : ''}`}
            onClick={() => setActiveTab(guardian.id)}
          >
            {guardianTabText[guardian.relationship]}
          </Button>
        ))}
      </div>
      {activeTab === 'student' && renderStudentInfo()}
      {parentsGuardians.map(
        (guardian) =>
          activeTab === guardian.id && renderGuardianInfo(guardian)
      )}
    </Modal>
  )
}

export default ModalApplicantDetails
