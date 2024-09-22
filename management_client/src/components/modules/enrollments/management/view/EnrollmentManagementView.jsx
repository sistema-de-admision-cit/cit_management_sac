import { useEffect, useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import EnrollmentTable from '../organisms/EnrollmentTable'
import '../../../../../assets/styles/enrollments/enrollment-management-view.css'
import EnrollemntSearchBar from '../molecules/EnrollmentSearchBar'
import ModalApplicantDetails from '../organisms/ModalApplicantDetails'
import { handleEnrollmentEdit, handleDocClick, handleFileDownload, handleStudendIdClick, handleSearch, handleGetAllEnrollments, handleFileDelete } from '../helpers/handlers'
import useMessages from '../../../../core/global/hooks/useMessages'

const EnrollmentManagementView = () => {
  const [loading, setLoading] = useState(false)

  const [enrollments, setEnrollments] = useState()
  const [applicantSelected, setApplicantSelected] = useState({})

  const [isModalApplicantDetailsOpen, setIsModalApplicantDetailsOpen] = useState(false)

  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  useEffect(() =>
    handleGetAllEnrollments(setEnrollments, setLoading, setErrorMessage)
  , [])

  return (
    <SectionLayout title='Consultar Inscripciones'>
      <div className='enrollment-management-view'>
        <h1>Consultar Inscripciones</h1>
        <p className='description'>Aquí puedes consultar y gestionar las inscripciones de los aspirantes.</p>

        <EnrollemntSearchBar onSearch={(search) => handleSearch(search, setEnrollments)} />
        <EnrollmentTable
          enrollments={enrollments}
          onStudentIdClick={(applicant) => handleStudendIdClick(applicant, setIsModalApplicantDetailsOpen, setApplicantSelected)}
          loading={loading}
        />
      </div>

      {isModalApplicantDetailsOpen && (
        <ModalApplicantDetails
          student={applicantSelected}
          parentsGuardians={applicantSelected.parents}
          enrollments={applicantSelected.enrollments}
          onClose={() => setIsModalApplicantDetailsOpen(false)}
          onDocClick={handleDocClick}
          onFileDownload={handleFileDownload}
          onFileDelete={(selectedFile, setSelectedFile, enrollmentId, studentId) => handleFileDelete(selectedFile, setSelectedFile, setErrorMessage, setSuccessMessage, setEnrollments, enrollmentId, studentId)}
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
