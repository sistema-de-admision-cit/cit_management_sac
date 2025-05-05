import { useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import StudentsTable from '../organisms/StudentsTable'
import '../../../../../assets/styles/enrollments/enrollment-management-view.css'
import ModalApplicantDetails from '../organisms/ModalApplicantDetails'
import { handleStudendIdClick } from '../helpers/handlers'
import { formatDateForApi } from '../helpers/helpers'
import useMessages from '../../../../core/global/hooks/useMessages'

const EnrollmentManagementView = () => {
  const [studentSelected, setStudentSelected] = useState({})
  const [studentEnrollments, setStudentEnrollments] = useState([])
  const [isModalApplicantDetailsOpen, setIsModalApplicantDetailsOpen] = useState(false)
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  const handleOnUpdateEnrollment = (sentData, editedEnrollment) => {

    const updatedEnrollments = studentEnrollments.map((enrollment) => {
      enrollment.student.previousGrades = parseFloat(sentData.previousGrades)

      if (enrollment.id === editedEnrollment.id) {
        return {
          ...enrollment,
          status: sentData.status,
          examDate: formatDateForApi(sentData.examDate),
          whatsappNotification: sentData.whatsappPermission,
        }
      }
      return enrollment
    })
    setStudentEnrollments(updatedEnrollments)
    setStudentSelected(studentEnrollments[0].student)
  }

  return (
    <SectionLayout title='Consultar Inscripciones'>
      <div className='enrollment-management-view'>
        <h1>Consultar Inscripciones</h1>
        <p className='description'>Aquí puedes consultar y gestionar las inscripciones de los aspirantes.</p>
        <StudentsTable
          onStudentIdClick={(applicant) => handleStudendIdClick(applicant, setIsModalApplicantDetailsOpen, setStudentSelected, setStudentEnrollments)}
          setErrorMessage={setErrorMessage}
        />
      </div>

      {isModalApplicantDetailsOpen && (
        <ModalApplicantDetails
          student={studentSelected}
          parents={studentSelected.parents}
          enrollments={studentEnrollments}
          setStudentEnrollments={setStudentEnrollments}
          onClose={() => setIsModalApplicantDetailsOpen(false)}
          setErrorMessage={setErrorMessage}
          setSuccessMessage={setSuccessMessage}
          onUpdateEnrollment={handleOnUpdateEnrollment}
        />
      )}
      {renderMessages()}
    </SectionLayout>
  )
}

export default EnrollmentManagementView
