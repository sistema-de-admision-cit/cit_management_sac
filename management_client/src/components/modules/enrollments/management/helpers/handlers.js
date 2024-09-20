import { dummyData } from '../view/temp_data'
import axios from 'axios'

export const handleStudendIdClick = (applicant, setIsModalApplicantDetailsOpen, setApplicantSelected) => {
  setApplicantSelected(applicant)
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

const getAllEnrollmentsUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_GET_ALL_ENROLLMENTS_ENDPOINT}`
export const handleGetAllEnrollments = (setEnrollments, setLoading, setErrorMessage) => {
  setLoading(true)

  console.log('getAllEnrollmentsUrl:', getAllEnrollmentsUrl)

  axios.get(getAllEnrollmentsUrl).then(response => {
    console.log(response)
    setEnrollments(response.data)
    setLoading(false)
  }).catch(error => {
    console.error(error)
    setLoading(false)
    setErrorMessage('Hubo un error al cargar los aspirantes. Por favor, intenta de nuevo.')
  })
}
