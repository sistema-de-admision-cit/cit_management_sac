import { useEffect, useState } from 'react'
import '../../../../../assets/styles/enrollments/modal-applicant-details.css'
import Modal from '../../../../core/global/molecules/Modal'
import StudentInfo from '../molecules/StudentInfo'
import ParentInfo from '../molecules/ParentInfo'
import EnrollmentInfo from './EnrollmentInfo'
import Button from '../../../../core/global/atoms/Button'
import { guardianTabText } from '../helpers/helpers'
import ModalManageFiles from '../molecules/ModalManageFiles'
import { handleDocClick, handleFileDownload, handleFileDelete, mapGradeToSpanish } from '../helpers/handlers'

const ModalApplicantDetails = ({
  student,
  parents,
  enrollments,
  onClose,
  onFileUpload,
  setErrorMessage,
  setSuccessMessage
}) => {
  const [activeTab, setActiveTab] = useState('student')
  const [isDocModalOpen, setIsDocModalOpen] = useState(false)
  const [selectedFile, setSelectedFile] = useState(null)
  const [selectedFileType, setSelectedFileType] = useState('')
  
  const [enrollment, setEnrollment] = useState({})

  return (
    <Modal onClose={onClose}>
      <h2>Información e Inscripciones del Estudiante</h2>
      <div className='modal-tabs'>

        <Button
          className={`tab-button ${activeTab === 'student' ? 'active' : ''}`}
          onClick={() => setActiveTab('student')}
        >
          Información del Estudiante
        </Button>

        {parents?.map((parent) => (
          <Button
            key={`parent-${parent.id}`}
            className={`tab-button ${activeTab === `parent-${parent.id}` ? 'active' : ''}`}
            onClick={() => setActiveTab(`parent-${parent.id}`)}
          >
            {guardianTabText[parent.relationship]}
          </Button>
        ))}

        {enrollments.map((enrollment) => (
          <Button
            key={`enrollment-${enrollment.id}`}
            className={`tab-button ${activeTab === `enrollment-${enrollment.id}` ? 'active' : ''}`}
            onClick={() => {
              setActiveTab(`enrollment-${enrollment.id}`)
              setEnrollment(enrollment)
            }}
          >
            Inscripción para {mapGradeToSpanish(enrollment.gradeToEnroll)}
          </Button>
        ))}
      </div>

      {activeTab === 'student' && <StudentInfo student={student} />}
      {parents?.some((parent) => activeTab === `parent-${parent.id}`) &&
        <ParentInfo parent={parents?.find((parent) => activeTab === `parent-${parent.id}`)} />}
      {enrollments.some((enrollment) => activeTab === `enrollment-${enrollment.id}`) &&
        <EnrollmentInfo
          enrollment={enrollments.find((enrollment) => activeTab === `enrollment-${enrollment.id}`)}
          onDocClick={(file) => handleDocClick(file, setSelectedFile, setIsDocModalOpen)}
          setSelectedFileType={setSelectedFileType}
          student={student}
          setErrorMessage={setErrorMessage}
          setSuccessMessage={setSuccessMessage}
        />}

      {isDocModalOpen && (
        <ModalManageFiles
          selectedFileType={selectedFileType}
          selectedFile={selectedFile}
          onFileUpload={(e) => onFileUpload(e, selectedFileType, setSelectedFile, enrollment, student.idNumber)}
          onFileDownload={() => handleFileDownload(selectedFile, student, setErrorMessage)}
          onFileDelete={(selectedFile) => {
            handleFileDelete(selectedFile, setErrorMessage, setSuccessMessage)
            setSelectedFile(null)
          }}
          onClose={() => setIsDocModalOpen(false)}
        />
      )}
    </Modal>
  )
}

export default ModalApplicantDetails
