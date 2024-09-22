import { useEffect, useState } from 'react'
import '../../../../../assets/styles/enrollments/modal-applicant-details.css'
import Modal from '../../../../core/global/molecules/Modal'
import StudentInfo from '../molecules/StudentInfo'
import GuardianInfo from '../molecules/GuardianInfo'
import EnrollmentInfo from './EnrollmentInfo'
import Button from '../../../../core/global/atoms/Button'
import { guardianTabText } from '../helpers/helpers'
import ModalManageFiles from '../molecules/ModalManageFiles'

const ModalApplicantDetails = ({
  student,
  parentsGuardians,
  enrollments,
  onClose,
  onDocClick,
  onFileDownload,
  onEnrollmentEdit
}) => {
  const [activeTab, setActiveTab] = useState('student')
  const [isDocModalOpen, setIsDocModalOpen] = useState(false)
  const [selectedFile, setSelectedFile] = useState([])
  const [selectedFileType, setSelectedFileType] = useState('')
  const [isEditing, setIsEditing] = useState(false)

  useEffect(() => {
    setIsEditing(false)
  }, [activeTab, enrollments])

  return (
    <Modal onClose={onClose}>
      <h2>Detalles del Aspirante</h2>
      <div className='modal-tabs'>

        <Button
          className={`tab-button ${activeTab === 'student' ? 'active' : ''}`}
          onClick={() => setActiveTab('student')}
        >
          Información del Estudiante
        </Button>

        {parentsGuardians.map((guardian) => (
          <Button
            key={`guardian-${guardian.id}`}
            className={`tab-button ${activeTab === `guardian-${guardian.id}` ? 'active' : ''}`}
            onClick={() => setActiveTab(`guardian-${guardian.id}`)}
          >
            {guardianTabText[guardian.relationship]}
          </Button>
        ))}

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

      {activeTab === 'student' && <StudentInfo student={student} />}
      {parentsGuardians.some((guardian) => activeTab === `guardian-${guardian.id}`) &&
        <GuardianInfo guardian={parentsGuardians.find((guardian) => activeTab === `guardian-${guardian.id}`)} />}
      {enrollments.some((enrollment) => activeTab === `enrollment-${enrollment.id}`) &&
        <EnrollmentInfo
          enrollment={enrollments.find((enrollment) => activeTab === `enrollment-${enrollment.id}`)}
          isEditing={isEditing}
          onEnrollmentEdit={(e, formData, enrollment) => onEnrollmentEdit(e, formData, enrollment, setIsEditing)}
          setIsEditing={setIsEditing}
          onDocClick={(file) => onDocClick(file, setSelectedFile, setIsDocModalOpen)}
          setSelectedFileType={setSelectedFileType}
          student={student}
        />}

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
