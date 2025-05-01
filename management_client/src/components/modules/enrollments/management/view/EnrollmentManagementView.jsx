import { useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import StudentsTable from '../organisms/StudentsTable'
import '../../../../../assets/styles/enrollments/enrollment-management-view.css'
import ModalApplicantDetails from '../organisms/ModalApplicantDetails'
import { handleEnrollmentEdit, handleDocClick, handleFileDownload, handleStudendIdClick, handleFileDelete, handleFileUpload } from '../helpers/handlers'
import useMessages from '../../../../core/global/hooks/useMessages'

const EnrollmentManagementView = () => {
  const [applicantSelected, setApplicantSelected] = useState({})
  const [isModalApplicantDetailsOpen, setIsModalApplicantDetailsOpen] = useState(false)
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  return (
    <SectionLayout title='Consultar Inscripciones'>
      <div className='enrollment-management-view'>
        <h1>Consultar Inscripciones</h1>
        <p className='description'>Aquí puedes consultar y gestionar las inscripciones de los aspirantes.</p>
        <StudentsTable
          onStudentIdClick={(applicant) => handleStudendIdClick(applicant, setIsModalApplicantDetailsOpen, setApplicantSelected)}
          setErrorMessage={setErrorMessage}
        />
      </div>

      {isModalApplicantDetailsOpen && (
        <ModalApplicantDetails
          student={applicantSelected[0].student}
          parentsGuardians={applicantSelected[0].student.parents}
          enrollments={applicantSelected}
          onClose={() => setIsModalApplicantDetailsOpen(false)}
          onDocClick={handleDocClick}
          onFileDownload={(file, student) => handleFileDownload(file, student, setErrorMessage)}
          onFileDelete={
            (selectedFile) => handleFileDelete(selectedFile, setErrorMessage, setSuccessMessage)
          }
          onFileUpload={
            (e, selectedFileType, setSelectedFile, enrollment, studentId) => handleFileUpload(e, selectedFileType, setSelectedFile, enrollment, studentId, setErrorMessage, setSuccessMessage)
          }
          onEnrollmentEdit={
            (e, formData, enrollment, setIsEditing) => handleEnrollmentEdit(e, formData, enrollment, setIsEditing, setErrorMessage, setSuccessMessage)
          }
        />
      )}
      {renderMessages()}
    </SectionLayout>
  )
}

export default EnrollmentManagementView
