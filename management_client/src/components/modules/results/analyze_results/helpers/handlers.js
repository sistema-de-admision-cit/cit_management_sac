import axiosInstance from '../../../../../config/axiosConfig'

// Manejo de errores
// Handlers that Get Data from API

const getAllResultsUrl = import.meta.env.VITE_GET_ALL_RESULTS_ENDPOINT
export const handleGetAllResults = (
  page = 0,
  pageSize = 10,
  setResults,
  setLoading,
  setErrorMessage,
  setSuccessMessage
) => {
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
export const handleSearchResults = async (
  search,
  setResults,
  setLoading,
  setErrorMessage,
  setSuccessMessage) => {
  if (!search || search.trim() === '') {
    setErrorMessage?.('Debe ingresar un valor para buscar')
    return
  }

  const url = `${searchResultUrl}/${search}`

  try {
    setLoading?.(true)

    const response = await axiosInstance.get(url)
    const data = response.data

    const grades = Array.isArray(data)
      ? data
      : Array.isArray(data.content)
        ? data.content
        : []

    setResults(grades)

    if (grades.length === 0) {
      setSuccessMessage?.('No se encontraron resultados para tu búsqueda')
    }
  } catch (error) {
    console.error('Error al buscar calificaciones:', error)

    let errorMsg = 'No se pudieron buscar las calificaciones académicas'

    if (error.response) {
      if (error.response.status === 400) {
        errorMsg = 'Parámetros de búsqueda inválidos'
      } else if (error.response.status === 401) {
        errorMsg = 'Sesión expirada. Por favor inicie sesión nuevamente'
      } else if (error.response.status === 403) {
        errorMsg = 'No tiene permisos para realizar esta búsqueda'
      } else if (error.response.status === 404) {
        setResults([]) // explícitamente vacío si no se encuentra
        errorMsg = 'No se encontraron resultados para tu búsqueda'
        setErrorMessage?.(errorMsg)
        return
      } else if (error.response.data?.message) {
        errorMsg = error.response.data.message
      }
    } else if (error.request) {
      errorMsg = 'No se recibió respuesta del servidor'
    }

    setErrorMessage?.(errorMsg)
  } finally {
    setLoading?.(false)
  }
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

export const handleGetTotalResultsPages = (setTotalPages, pageSize) => {
  const getTotalPagesUrl = import.meta.env.VITE_GET_TOTAL_PAGES_ENDPOINT_RESULTS
  axiosInstance.get(getTotalPagesUrl, { timeout: 10000 })
    .then(response => {
      setTotalPages(response.data % pageSize === 0 ? response.data / pageSize : Math.floor(response.data / pageSize) + 1)
    })
    .catch(error => {
      console.error('Error al obtener el total de páginas:', error)
    })
}

export const handleGetTotalResultsSearchPages = (search, pageSize, setTotalPages) => {
  const getTotalPagesUrl = import.meta.env.VITE_GET_TOTAL_PAGES_FOR_SEARCH_ENDPOINT_RESULTS
  axiosInstance.get(`${getTotalPagesUrl}?value=${search}`, { timeout: 10000 })
    .then(response => {
      setTotalPages(response.data % pageSize === 0 ? response.data / pageSize : Math.floor(response.data / pageSize) + 1)
    })
    .catch(error => {
      console.error('Error al obtener el total de páginas:', error)
    })
}
