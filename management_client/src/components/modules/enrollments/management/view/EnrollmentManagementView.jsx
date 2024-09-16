import { useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import EnrollmentTable from '../organisms/EnrollmentTable'
import '../../../../../assets/styles/enrollments/enrollment-management-view.css'
import { dummyData } from './temp_data'
import EnrollemntSearchBar from '../molecules/EnrollmentSearchBar'
import ModalManageFiles from '../molecules/ModalManageFiles'
import ModalApplicantDetails from '../molecules/ModalApplicantDetails'

const EnrollmentManagementView = ({ enrollments }) => {
  const [applicants, setApplicants] = useState(dummyData)
  const [isDocModalOpen, setIsDocModalOpen] = useState(false)
  const [selectedColumn, setSelectedColumn] = useState('')
  const [selectedFiles, setSelectedFiles] = useState([])

  const [isModalApplicantDetailsOpen, setIsModalApplicantDetailsOpen] = useState(false)

  const handleStudendIdClick = (aspirante) => {
    setIsModalApplicantDetailsOpen(true)
  }

  const handleDateChange = (applicant, date) => {
    console.log('Fecha Entrevista:', date)
  }

  const handleWhatsappChange = (applicant, value) => {
    console.log('Whatsapp:', value)
  }

  const handleDocClick = (applicant, column, files) => {
    setSelectedColumn(column)
    setSelectedFiles(files)
    setIsDocModalOpen(true)
  }

  const handleSearch = (search) => {
    const filteredApplicants = dummyData.filter((applicant) => {
      return applicant.studendtId.includes(search) ||
        applicant.firstName.toLowerCase().includes(search.toLowerCase()) ||
        applicant.firstSurname.includes(search.toLowerCase()) ||
        applicant.secondSurname.toLowerCase().includes(search.toLowerCase())
    })

    setApplicants(filteredApplicants)
  }

  return (
    <SectionLayout title='Consultar Inscripciones'>
      <div className='enrollment-management-view'>
        <h1>Consultar Inscripciones</h1>
        <p className='description'>Aquí puedes consultar y gestionar las inscripciones de los aspirantes.</p>

        <EnrollemntSearchBar onSearch={handleSearch} />
        <EnrollmentTable
          applicants={applicants}
          onStudentIdClick={handleStudendIdClick}
          onDateChange={handleDateChange}
          onWhatsappChange={handleWhatsappChange}
          onDocClick={handleDocClick}
        />
      </div>

      {isDocModalOpen && (
        <ModalManageFiles
          selectedColumn={selectedColumn}
          selectedFiles={[{ documentId: '1', name: 'Documento 1' }, { documentId: '2', name: 'Documento 2' }]}
          onFileUpload={() => console.log('Subir archivo')}
          onFileDownload={(document) => console.log('Descargar:', document)}
          onFileDelete={(document) => console.log('Eliminar:', document)}
          onClose={() => setIsDocModalOpen(false)}
        />
      )}

      {isModalApplicantDetailsOpen && (
        <ModalApplicantDetails
          student={applicants[0]}
          parentsGuardians={[{
            id: 10,
            firstName: 'Luis',
            firstSurname: 'Vega',
            secondSurname: 'Alvarado',
            idType: 'DNI',
            idNumber: 'DNI43215678',
            phoneNumber: '8889990001',
            email: 'luis.vega@example.com',
            homeAddress: '444 Avenida Norte, Ciudad Central',
            relationship: 'FATHER'
          }]}
          onClose={() => setIsModalApplicantDetailsOpen(false)}
        />
      )}
    </SectionLayout>
  )
}

export default EnrollmentManagementView
