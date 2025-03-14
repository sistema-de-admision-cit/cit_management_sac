import axios from '../../../../../config/axiosConfig'
import { formatDateForApi, formatDateToObj, isCommentRequired } from './helpers'

// Manejo de errores
const getErrorMessage = (error) => {
  if (error.code === 'ECONNABORTED') {
    return 'La conexión al servidor tardó demasiado. Por favor, intenta de nuevo.'
  }

  if (error.response) {
    if (error.response.status === 404) {
      return 'El documento no se encontró. Puede que ya haya sido eliminado.'
    } else if (error.response.status === 500) {
      return 'Hubo un error en el servidor. Por favor, intenta de nuevo más tarde.'
    } else {
      return (error.response.data.message || 'Por favor, intenta de nuevo.')
    }
  } else if (error.request) {
    return 'Error de red: No se pudo conectar al servidor. Por favor, verifica tu conexión.'
  } else {
    return 'Error inesperado: ' + error.message
  }
}

const getEnrollmentsByStudentIdUrl = import.meta.env.VITE_SEARCH_ENROLLMENT_BY_STUDENT_ID_ENDPOINT
export const handleStudendIdClick = (applicant, setIsModalApplicantDetailsOpen, setApplicantSelected) => {

  axios.get(`${getEnrollmentsByStudentIdUrl}${applicant.person.idNumber}`,
    {
      timeout: 10000
    })
    .then(response => {
      setApplicantSelected(response.data)
      setIsModalApplicantDetailsOpen(true)
    })
    .catch(error => {
      setErrorMessage(getErrorMessage(error))
    })
}

const validateDataBeforeSubmit = (formData, enrollment) => {
  if (!formData.status) {
    throw new Error('El estado de la inscripción es obligatorio')
  }
  if (!formData.examDate) {
    throw new Error('La fecha del examen es obligatoria')
  }
  if (isCommentRequired(formData, enrollment) && !formData.comment) {
    throw new Error('El comentario es obligatorio')
  }
}

// not sure if this is the best way to update the local state
// but it works for now
const updateEnrollmentLocal = (enrollment, formData) => {
  enrollment.status = formData.status
  enrollment.examDate = formData.examDate
  enrollment.whatsappNotification = formData.whatsappNotification
}

const updateEnrollmentUrl = import.meta.env.VITE_UPDATE_ENROLLMENT_INFORMATION_ENDPOINT
export const handleEnrollmentEdit = (e, formData, enrollment, setIsEditing, setErrorMessage, setSuccessMessage) => {
  e.preventDefault()

  try {
    validateDataBeforeSubmit(formData, enrollment)
  } catch (error) {
    setErrorMessage(error.message)
    return
  }

  axios.put(`${updateEnrollmentUrl}/${enrollment.id}?status=${formData.status}&examDate=${formatDateForApi(formData.examDate)}&whatsappPermission=${formData.whatsappNotification}&comment=${formData.comment}&changedBy=1`,
    { timeout: 10000 })
    .then(response => {
      setIsEditing(false)
      setSuccessMessage('La inscripción se actualizó correctamente.')
      updateEnrollmentLocal(enrollment, formData)
    })
    .catch(error => {
      setErrorMessage(getErrorMessage(error))
    })
}

export const handleDocClick = (file, setSelectedFile, setIsDocModalOpen) => {
  setSelectedFile(file)
  setIsDocModalOpen(true)
}

const downloadFileUrl = import.meta.env.VITE_DOWNLOAD_DOCUMENT_BY_DOCUMENT_NAME_ENDPOINT
export const handleFileDownload = (filename, setErrorMessage) => {
  axios.get(`${downloadFileUrl}/${filename}`,
    {
      responseType: 'blob',
      timeout: 10000
    })
    .then(response => {
      const url = window.URL.createObjectURL(new Blob([response.data]))
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', filename)
      document.body.appendChild(link)
      link.click()
      window.URL.revokeObjectURL(url)
    })
    .catch(error => {
      setErrorMessage(getErrorMessage(error))
    })
}

const deleteFileUrl = import.meta.env.VITE_DELETE_DOCUMENT_BY_DOCUMENT_NAME_ENDPOINT
export const handleFileDelete = (selectedFile, setSelectedFile, setErrorMessage, setSuccessMessage, setEnrollments, enrollmentId, studentId) => {
  axios.delete(`${deleteFileUrl}/${selectedFile.id}?filename=${selectedFile.documentUrl}`, { timeout: 10000 }) // 10 segundos
    .then(response => {
      setSuccessMessage('El documento se eliminó correctamente.')
      setSelectedFile(null)
      setEnrollments(prevEnrollments =>
        prevEnrollments.map(student => {
          if (student.id === studentId) {
            return {
              ...student,
              enrollments: student.enrollments.map(enrollment => {
                if (enrollment.id === enrollmentId) {
                  return {
                    ...enrollment,
                    document: enrollment.document.filter(doc => doc.id !== selectedFile.id)
                  }
                }
                return enrollment
              })
            }
          }
          return student
        })
      )
    })
    .catch(error => {
      setErrorMessage(getErrorMessage(error))
    })
}

const uploadFileUrl = import.meta.env.VITE_UPLOAD_DOCUMENT_ENDPOINT
export const handleFileUpload = (e, selectedFileType, setSelectedFile, enrollment, studentId, setErrorMessage, setSuccessMessage) => {
  e.preventDefault()

  const file = e.target[0].files[0]
  if (!file) {
    setErrorMessage('Debes seleccionar un archivo para subir.')
    return
  }

  const documentType = selectedFileType === 'Documento de Notas' ? 'OT' : 'HC'
  const documentName = selectedFileType === 'Documento de Notas'
    ? `notas_${enrollment.id}_${studentId}.pdf`
    : `carta_${enrollment.id}_${studentId}.pdf`

  const data = new FormData()
  data.append('file', file)
  data.append('documentName', documentName)
  data.append('documentType', documentType)
  data.append('enrollmentId', enrollment.id)
  axios.post(
    uploadFileUrl,
    data,
    { timeout: 10000 })
    .then(response => {
      setSuccessMessage('El documento se subió correctamente.')

      const updatedDoc = {
        id: response.data.id,
        documentName: response.data.documentName,
        documentType: response.data.documentType,
        documentUrl: response.data.documentUrl
      }

      enrollment.document.push(updatedDoc)
      setSelectedFile(updatedDoc)
    })
    .catch(error => {
      setErrorMessage(getErrorMessage(error))
    })
}

const searchEnrollmentUrl = import.meta.env.VITE_SEARCH_ENROLLMENT_BY_STUDENT_VALUES_ENDPOINT
export const handleSearch = (search, setEnrollments) => {
  axios.get(`${searchEnrollmentUrl}?value=${search}`, { timeout: 10000 })
    .then(response => {
      const enrollments = response.data.map(enrollment => formatDateToObj(enrollment))
      setEnrollments(enrollments)
    })
    .catch(error => {
      console.error(error)
    })
}

const getAllEnrollmentsUrl = import.meta.env.VITE_GET_ALL_ENROLLMENTS_ENDPOINT
export const handleGetAllEnrollments = (setEnrollments, setLoading, setErrorMessage) => {
  setLoading(true)

  axios.get(getAllEnrollmentsUrl, { timeout: 10000 })
    .then(response => {
      const enrollments = response.data.map(enrollment => formatDateToObj(enrollment))
      const uniqueStudents = enrollments.reduce((acc, enrollment) => {
        if (!acc.some(e => e.student.id === enrollment.student.id)) {
          acc.push(enrollment);
        }
        return acc;
      }, [])
      setEnrollments(uniqueStudents)
    })
    .catch(error => {
      setErrorMessage(getErrorMessage(error))
    })
    .finally(() => {
      setLoading(false)
    })
}
