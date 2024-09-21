import { useState } from 'react'
import '../../../../../assets/styles/enrollments/modal-applicant-details.css'
import Modal from '../../../../core/global/molecules/Modal'
import Button from '../../../../core/global/atoms/Button'
import { guardianTabText, buildGuardianAddress } from '../helpers/helpers'
import PdfIcon from '../../../../../assets/icons/pdf-svgrepo-com.svg'
import ModalManageFiles from './ModalManageFiles'

const ModalApplicantDetails = ({
  student,
  parentsGuardians,
  enrollments,
  onClose,
  onDocClick,
  onFileDownload,
  onDateChange,
  onStatusChange
}) => {
  const [activeTab, setActiveTab] = useState('student')
  const [isDocModalOpen, setIsDocModalOpen] = useState(false)
  const [selectedFile, setSelectedFile] = useState([])
  const [selectedFileType, setSelectedFileType] = useState('')

  const renderStudentInfo = () => (
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

  const renderGuardianInfo = (guardian) => (
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

  const renderEnrollmentInfo = (enrollment) => (
    <div className='tab-content'>
      <h2>Inscripción - {enrollment.id}</h2>
      <p><strong>Estado:</strong> {enrollment.status}</p>
      <p><strong>Fecha del Examen:</strong> {enrollment.examDate}</p>
      <p><strong>Notificación por WhatsApp:</strong> {enrollment.whatsappNotification ? 'Sí' : 'No'}</p>
      <p><strong>Consentimiento:</strong> {enrollment.consentGiven ? 'Dado' : 'No Dado'}</p>
      <div className='documents-sections'>
        <div className='document-item'>
          <p><strong>Documento de Notas:</strong>:</p>
          <Button
            className='pdf-icon'
            onClick={() => {
              onDocClick(enrollment.document.find((doc) => doc.documentType === 'OT'), setSelectedFile, setIsDocModalOpen)
              setSelectedFileType('Documentos de Notas')
            }}
          >
            <img src={PdfIcon} alt='logo de un pdf' />
          </Button>
        </div>
        {student.hasAccommodations && (
          <div className='document-item'>
            <p><strong>Documento de :</strong>:</p>
            <Button
              className='pdf-icon'
              onClick={() => {
                setSelectedFileType('Documento de Adaptaciones')
                onDocClick(enrollment.document.find((doc) => doc.documentType === 'HC'), setSelectedFile, setIsDocModalOpen, setSelectedFileType)
              }}
            >
              <img src={PdfIcon} alt='logo de un pdf' />
            </Button>
          </div>
        )}
      </div>
    </div>
  )

  return (
    <Modal onClose={onClose}>
      <h2>Detalles del Aspirante</h2>
      <div className='modal-tabs'>
        {/* button to show student info */}
        <Button
          className={`tab-button ${activeTab === 'student' ? 'active' : ''}`}
          onClick={() => setActiveTab('student')}
        >
          Información del Estudiante
        </Button>

        {/* render a button for each parent/guardian */}
        {parentsGuardians.map((guardian) => (
          <Button
            key={`guardian-${guardian.id}`}
            className={`tab-button ${activeTab === `guardian-${guardian.id}` ? 'active' : ''}`}
            onClick={() => setActiveTab(`guardian-${guardian.id}`)}
          >
            {guardianTabText[guardian.relationship]}
          </Button>
        ))}

        {/* render a button for each enrollment */}
        {enrollments.map((enrollment) => (
          <Button
            key={`enrollment-${enrollment.id}`}
            className={`tab-button ${activeTab === `enrollment-${enrollment.id}` ? 'active' : ''}`}
            onClick={() => setActiveTab(`enrollment-${enrollment.id}`)}
          >
            Inscripción - {enrollment.id}
          </Button>
        ))}
      </div>

      {activeTab === 'student' && renderStudentInfo()}

      {parentsGuardians.some((guardian) => activeTab === `guardian-${guardian.id}`) &&
        renderGuardianInfo(parentsGuardians.find((guardian) => activeTab === `guardian-${guardian.id}`))}

      {enrollments.some((enrollment) => activeTab === `enrollment-${enrollment.id}`) &&
        renderEnrollmentInfo(enrollments.find((enrollment) => activeTab === `enrollment-${enrollment.id}`))}

      {isDocModalOpen && (
        <ModalManageFiles
          selectedFileType={selectedFileType}
          selectedFile={selectedFile}
          onFileUpload={() => console.log('Subir archivo')}
          onFileDownload={onFileDownload}
          onFileDelete={(document) => console.log('Eliminar:', document)}
          onClose={() => setIsDocModalOpen(false)}
        />
      )}
    </Modal>
  )
}

export default ModalApplicantDetails
