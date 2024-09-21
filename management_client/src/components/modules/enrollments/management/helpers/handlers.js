import axios from 'axios'

export const handleStudendIdClick = (applicant, setIsModalApplicantDetailsOpen, setApplicantSelected) => {
  setApplicantSelected(applicant)
  setIsModalApplicantDetailsOpen(true)
}

export const handleDateChange = (applicant, date) => {
  console.log('Fecha Entrevista:', date)
}

const updateWhatsappPermissionUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_UPDATE_WHATSAPP_PERMISSION_ENDPOINT}`
export const handleWhatsappChange = (applicant, value, setErrorMessage, setEnrollments) => {
  axios.put(`${updateWhatsappPermissionUrl}/${applicant.id}?permission=${value}`)
    .then(response => {
      // update the state of the enrollments
      setEnrollments(prevEnrollments => {
        const updatedEnrollments = prevEnrollments.map(enrollment => {
          if (enrollment.id === applicant.id) {
            enrollment.enrollments[0].whatsappNotification = value
          }
          return enrollment
        })
        return updatedEnrollments
      })
    }).catch(error => {
      console.error(error)
      setErrorMessage('Hubo un error al actualizar el permiso de Whatsapp. Por favor, intenta de nuevo.')
    })
}

export const handleDocClick = (applicant, column, file, setSelectedColumn, setSelectedFile, setIsDocModalOpen) => {
  setSelectedColumn(column)
  setSelectedFile(file)
  setIsDocModalOpen(true)
}

const downloadFileUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_DOWNLOAD_DOCUMENT_BY_DOCUMENT_NAME_ENDPOINT}`
export const handleFileDownload = (filename) => {
  console.log('Descargando:', filename)
  console.log('Descargando:', downloadFileUrl)

  axios.get(`${downloadFileUrl}/${filename}`, { responseType: 'blob' })
    .then(response => {
      console.log(response)
      const url = window.URL.createObjectURL(new Blob([response.data]))
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', filename)
      document.body.appendChild(link)
      link.click()
      // Limpia el URL después de descargar el archivo
      window.URL.revokeObjectURL(url)
    })
    .catch(error => {
      console.error(error)
    })
}

const searchEnrollmentUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_SEARCH_ENROLLMENT_BY_STUDENT_VALUES_ENDPOINT}`
export const handleSearch = (search, setApplicants) => {
  axios.get(`${searchEnrollmentUrl}?value=${search}`).then(response => {
    console.log(response)
    setApplicants(response.data)
  }).catch(error => {
    console.error(error)
  })
}

const getAllEnrollmentsUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_GET_ALL_ENROLLMENTS_ENDPOINT}`
export const handleGetAllEnrollments = (setEnrollments, setLoading, setErrorMessage) => {
  setLoading(true)

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
