import { useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import EnrollmentTable from '../organisms/EnrollmentTable'
import '../../../../../assets/styles/enrollments/enrollment-management-view.css'
import { dummyData } from './temp_data'
import EnrollemntSearchBar from '../molecules/EnrollmentSearchBar'

const EnrollmentManagementView = ({ enrollments }) => {
  const [applicants, setApplicants] = useState(dummyData)

  const handleStudendIdClick = (aspirante) => {
    console.log('Cédula:', aspirante.cedula)
  }

  const handleDateChange = (applicant, date) => {
    console.log('Fecha Entrevista:', date)
  }

  const handleWhatsappChange = (applicant, value) => {
    console.log('Whatsapp:', value)
  }

  const handleDocClick = (applicant, type) => {
    console.log('Documento:', type)
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
    </SectionLayout>
  )
}

export default EnrollmentManagementView
