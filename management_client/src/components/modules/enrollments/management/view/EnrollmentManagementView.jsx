import { useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import EnrollmentTable from '../organisms/EnrollmentTable'
import '../../../../../assets/styles/enrollments/enrollment-management-view.css'
import { dummyData } from './temp_data'

const EnrollmentManagementView = ({ enrollments }) => {
  const [applicants, setApplicants] = useState(dummyData)

  const handleCedulaClick = (aspirante) => {
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

  return (
    <SectionLayout title='Consultar Inscripciones'>
      <div className='enrollment-management-view'>
        <h1>Consultar Inscripciones</h1>
        <p className='description'>Aquí puedes consultar y gestionar las inscripciones de los aspirantes.</p>
        <EnrollmentTable
          applicants={applicants}
          onCedulaClick={handleCedulaClick}
          onDateChange={handleDateChange}
          onWhatsappChange={handleWhatsappChange}
          onDocClick={handleDocClick}
        />
      </div>
    </SectionLayout>
  )
}

export default EnrollmentManagementView
