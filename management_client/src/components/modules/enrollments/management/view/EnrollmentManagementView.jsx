import { useEffect, useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import EnrollmentTable from '../organisms/EnrollmentTable'
import '../../../../../assets/styles/enrollments/enrollment-management-view.css'
import EnrollemntSearchBar from '../molecules/EnrollmentSearchBar'
import ModalManageFiles from '../molecules/ModalManageFiles'
import ModalApplicantDetails from '../molecules/ModalApplicantDetails'
import { handleDateChange, handleDocClick, handleFileDownload, handleStudendIdClick, handleWhatsappChange, handleSearch, handleGetAllEnrollments } from '../helpers/handlers'
import useMessages from '../../../../core/global/hooks/useMessages'

const EnrollmentManagementView = () => {
  const [loading, setLoading] = useState(false)

  const [enrollments, setEnrollments] = useState()
  const [isDocModalOpen, setIsDocModalOpen] = useState(false)
  const [selectedColumn, setSelectedColumn] = useState('')
  const [selectedFile, setSelectedFile] = useState([])
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
          onDateChange={handleDateChange}
          onWhatsappChange={handleWhatsappChange}
          onDocClick={(applicant, column, file) => handleDocClick(applicant, column, file, setSelectedColumn, setSelectedFile, setIsDocModalOpen)}
          loading={loading}
        />
      </div>

      {isDocModalOpen && (
        <ModalManageFiles
          selectedColumn={selectedColumn}
          selectedFile={selectedFile}
          onFileUpload={() => console.log('Subir archivo')}
          onFileDownload={handleFileDownload}
          onFileDelete={(document) => console.log('Eliminar:', document)}
          onClose={() => setIsDocModalOpen(false)}
        />
      )}

      {isModalApplicantDetailsOpen && (
        <ModalApplicantDetails
          student={applicantSelected}
          parentsGuardians={applicantSelected.parents}
          onClose={() => setIsModalApplicantDetailsOpen(false)}
        />
      )}
      {renderMessages()}
    </SectionLayout>
  )
}

export default EnrollmentManagementView
