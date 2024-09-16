import { dummyData } from '../view/temp_data'

export const handleStudendIdClick = (aspirante, setIsModalApplicantDetailsOpen) => {
  setIsModalApplicantDetailsOpen(true)
}

export const handleDateChange = (applicant, date) => {
  console.log('Fecha Entrevista:', date)
}

export const handleWhatsappChange = (applicant, value) => {
  console.log('Whatsapp:', value)
}

export const handleDocClick = (applicant, column, files, setSelectedColumn, setSelectedFiles, setIsDocModalOpen) => {
  setSelectedColumn(column)
  setSelectedFiles(files)
  setIsDocModalOpen(true)
}

export const handleSearch = (search, setApplicants) => {
  const filteredApplicants = dummyData.filter((applicant) => {
    return applicant.studendtId.includes(search) ||
      applicant.firstName.toLowerCase().includes(search.toLowerCase()) ||
      applicant.firstSurname.includes(search.toLowerCase()) ||
      applicant.secondSurname.toLowerCase().includes(search.toLowerCase())
  })

  setApplicants(filteredApplicants)
}
