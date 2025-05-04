import { useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import StudentsTable from '../organisms/StudentsTable'
import '../../../../../assets/styles/enrollments/enrollment-management-view.css'
import ModalApplicantDetails from '../organisms/ModalApplicantDetails'
import { handleStudendIdClick, handleFileUpload } from '../helpers/handlers'
import { formatDateForApi } from '../helpers/helpers'
import useMessages from '../../../../core/global/hooks/useMessages'

const EnrollmentManagementView = () => {
  const [applicantSelected, setApplicantSelected] = useState({})
  const [applicantEnrollments, setApplicantEnrollments] = useState([])
  const [isModalApplicantDetailsOpen, setIsModalApplicantDetailsOpen] = useState(false)
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  const handleOnUpdateEnrollment = (formData) => {

    setApplicantSelected((prev) => ({
      ...prev,
      previousGrades: parseFloat(formData.previousGrades),
    }))

    const updatedEnrollment = applicantEnrollments.map((enrollment) => {
      enrollment.student.previousGrades = parseFloat(formData.previousGrades)

      if (enrollment.id === formData.enrollmentId) {
        return {
          ...enrollment,
          status: formData.status,
          examDate: formatDateForApi(formData.examDate),
          whatsappNotification: formData.whatsappNotification,
        }
      }
      return enrollment
    })
    setApplicantEnrollments(updatedEnrollment)
  }

  return (
    <SectionLayout title='Consultar Inscripciones'>
      <div className='enrollment-management-view'>
        <h1>Consultar Inscripciones</h1>
        <p className='description'>Aquí puedes consultar y gestionar las inscripciones de los aspirantes.</p>
        <StudentsTable
          onStudentIdClick={(applicant) => handleStudendIdClick(applicant, setIsModalApplicantDetailsOpen, setApplicantSelected, setApplicantEnrollments)}
          setErrorMessage={setErrorMessage}
        />
      </div>

      {isModalApplicantDetailsOpen && (
        <ModalApplicantDetails
          student={applicantSelected}
          parents={applicantSelected.parents}
          enrollments={applicantEnrollments}
          onClose={() => setIsModalApplicantDetailsOpen(false)}
          onFileUpload={
            (e, selectedFileType, setSelectedFile, enrollment, studentId) => handleFileUpload(e, selectedFileType, setSelectedFile, enrollment, studentId, setErrorMessage, setSuccessMessage)
          }
          setErrorMessage={setErrorMessage}
          setSuccessMessage={setSuccessMessage}
          onUpdateEnrollment={(formData) => handleOnUpdateEnrollment(formData)}
        />
      )}
      {renderMessages()}
    </SectionLayout>
  )
}

export default EnrollmentManagementView
