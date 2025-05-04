import axiosInstance from '../../../../../config/axiosConfig'
import { formatDateToObj } from '../../../enrollments/management/helpers/helpers.js'

// Manejo de errores
const getErrorMessage = (error) => {
  if (error.code === 'ECONNABORTED') {
    return 'La conexión al servidor tardó demasiado. Por favor, intenta de nuevo.'
  }

  if (error.response) {
    if (error.response.status === 404) {
      return 'El estudiante no se encontró. Puede que ya haya sido eliminado.'
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

// Handlers that Get Data from API

const getAllResultsUrl = import.meta.env.VITE_GET_ALL_RESULTS_ENDPOINT
export const handleGetAllResults = (
  page = 0,
  setResults,
  setLoading,
  setErrorMessage,
  setSuccessMessage
) => {
  const pageSize = 25
  const url = `${getAllResultsUrl}`

  setLoading(true)

  axiosInstance.get(url, {
    params: {
      page,
      size: pageSize
    }
  })
    .then(response => {
      const data = response.data

      setResults(data.content || data)

      if (setSuccessMessage) {
        setSuccessMessage('Resultados finales cargados correctamente')
      }
      // console.log('Resultados finales:', data)
      return {
        totalPages: data.totalPages || 1,
        currentPage: data.number || page,
        totalElements: data.totalElements || (Array.isArray(data) ? data.length : 0)
      }
    })
    .catch(error => {
      console.error('Error al obtener los resultados:', error)

      let errorMsg = 'No se pudieron cargar los resultados finales'

      if (error.response) {
        if (error.response.status === 401) {
          errorMsg = 'Sesión expirada. Por favor inicie sesión nuevamente'
        } else if (error.response.status === 403) {
          errorMsg = 'No tiene permisos para ver estas calificaciones'
        } else if (error.response.data?.message) {
          errorMsg = error.response.data.message
        }
      } else if (error.request) {
        errorMsg = 'No se recibió respuesta del servidor'
      }

      setErrorMessage(errorMsg)
      return null
    })
    .finally(() => {
      setLoading(false)
    })
}

const getStudentDetailsUrl = import.meta.env.VITE_GET_STUDENT_DETAILS_ENDPOINT
export const handleGetStudentDetails = async (
  idNumber,
  setDetails,
  setLoading,
  setErrorMessage,
  setSuccessMessage) => {
  const url = `${getStudentDetailsUrl}/${idNumber}`

  setLoading(true)

  axiosInstance.get(url)
    .then(response => {
      const data = response.data

      setDetails(data.content || data)

      if (setSuccessMessage) {
        // setSuccessMessage('Detalles finales cargados correctamente')
      }
    })
    .catch(error => {
      console.error('Error al obtener los resultados:', error)

      let errorMsg = 'No se pudieron cargar los resultados finales'

      if (error.response) {
        if (error.response.status === 401) {
          errorMsg = 'Sesión expirada. Por favor inicie sesión nuevamente'
        } else if (error.response.status === 403) {
          errorMsg = 'No tiene permisos para ver estas calificaciones'
        } else if (error.response.data?.message) {
          errorMsg = error.response.data.message
        }
      } else if (error.request) {
        errorMsg = 'No se recibió respuesta del servidor'
      }

      setErrorMessage(errorMsg)
      return null
    })
    .finally(() => {
      setLoading(false)
    })
}

// Handlers that Search Data from API
const searchResultUrl = import.meta.env.VITE_GET_RESULTS_BY_SEARCH_ENDPOINT
export const handleSearchResults = (search,
  setResults,
  setLoading,
  setErrorMessage,
  setTotalPages,
  setCurrentPage) => {
  axiosInstance.get(`${searchResultUrl}?value=${search}`, { timeout: 10000 })
    .then(response => {
      const results = response.data.map(setResults => formatDateToObj(setResults))
      const uniqueStudents = results.reduce((acc, setResults) => {
        if (!acc.some(e => e.student.id === setResults.student.id)) {
          acc.push(setResults)
        }
        return acc
      }, [])
      setResults(uniqueStudents)
    })
    .catch(error => {
      console.error(error)
      setErrorMessage(getErrorMessage())
    })
}
// Handlers that Save Data from API

const saveStudentStatusUrl = import.meta.env.VITE_UPDATE_STUDENT_RESULT_ENDPOINT
export const handleSaveStatus = async (
  idNumber,
  updateStatusDTO,
  setErrorMessage,
  setSuccessMessage,
  setLoading,
  onClose
) => {
  setLoading(true)

  try {
    const url = `${saveStudentStatusUrl}/${idNumber}`

    const response = await axiosInstance.put(url, updateStatusDTO, {
      headers: {
        'Content-Type': 'application/json'
      }
    })

    setSuccessMessage('Comentario y estado guardados exitosamente.')

    if (onClose) onClose()

    setTimeout(() => {
      window.location.reload()
    }, 1500)

    return response.data
  } catch (error) {
  } finally {
    setLoading(false)
  }
}
