import axiosInstance from '../../../../config/axiosConfig'
import { jsPDF } from 'jspdf'

// Handlers that Get Data from API
const getAllAcademicGradesUrl = import.meta.env.VITE_GET_ALL_ACADEMIC_GRADES_ENDPOINT
export const handleGetAllAcademicGrades = async (
  page = 0,
  pageSize = 10,
  setGrades,
  setLoading,
  setErrorMessage
) => {
  const examType = 'ACA'
  const url = `${getAllAcademicGradesUrl}/${examType}`

  try {
    setLoading(true)
    const response = await axiosInstance.get(url, {
      params: {
        page,
        size: pageSize
      }
    })

    const data = response.data
    const gradesData = Array.isArray(data) ? data : (data.content || [])
    setGrades(gradesData)

    return {
      totalPages: data.totalPages || 1,
      currentPage: data.number || page,
      totalElements: data.totalElements || gradesData.length
    }
  } catch (error) {
    console.error('Error al obtener calificaciones:', error)
    let errorMsg = 'No se pudieron cargar las calificaciones académicas'

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
  } finally {
    setLoading(false)
  }
}

const getAllDaiGradesUrl = import.meta.env.VITE_GET_ALL_DAI_GRADES_ENDPOINT
export const handleGetAllDaiGrades = async (
  page = 0,
  pageSize = 10,
  setGrades,
  setLoading,
  setErrorMessage
) => {
  const examType = 'DAI'
  const url = `${getAllDaiGradesUrl}/${examType}`

  try {
    setLoading(true)
    const response = await axiosInstance.get(url, {
      params: {
        page,
        size: pageSize
      }
    })

    const data = response.data

    const gradesData = Array.isArray(data) ? data : (data.content || [])
    setGrades(gradesData)

    return {
      totalPages: data.totalPages || 1,
      currentPage: data.number || page,
      totalElements: data.totalElements || gradesData.length
    }
  } catch (error) {
    console.error('Error al obtener calificaciones:', error)
    let errorMsg = 'No se pudieron cargar las calificaciones académicas'

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
  } finally {
    setLoading(false)
  }
}

const searchAcademicUrl = import.meta.env.VITE_GET_ACADEMIC_GRADES_SEARCH_ENDPOINT
export const handleSearchAcademicGrades = async (
  search,
  setGrades,
  setLoading,
  setErrorMessage,
  setSuccessMessage
) => {
  const examType = 'ACA'

  if (!search || search.trim() === '') {
    setErrorMessage?.('Debe ingresar un valor para buscar')
    return
  }

  const url = `${searchAcademicUrl}/${search}/${examType}`

  try {
    setLoading?.(true)

    const response = await axiosInstance.get(url)
    const data = response.data

    const grades = Array.isArray(data)
      ? data
      : Array.isArray(data.content)
        ? data.content
        : []

    setGrades(grades)

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
        setGrades([]) // explícitamente vacío si no se encuentra
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

const searchDAIUrl = import.meta.env.VITE_GET_DAI_GRADES_SEARCH_ENDPOINT
export const handleSearchDAIGrades = async (
  search,
  setGrades,
  setLoading,
  setErrorMessage,
  setSuccessMessage
) => {
  const examType = 'DAI'

  if (!search || search.trim() === '') {
    setErrorMessage?.('Debe ingresar un valor para buscar')
    return
  }

  const url = `${searchDAIUrl}/${search}/${examType}`

  try {
    setLoading?.(true)

    const response = await axiosInstance.get(url)
    const data = response.data

    const grades = Array.isArray(data)
      ? data
      : Array.isArray(data.content)
        ? data.content
        : []

    setGrades(grades)

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
        setGrades([])
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
export const handleSaveDAIComment = (
  grade,
  comment,
  status,
  setErrorMessage,
  setSuccessMessage,
  setLoading,
  onClose
) => {
  const saveDAIUrl = import.meta.env.VITE_PUT_DAI_COMMENT_ENDPOINT || '/management-exams/dai-exam'

  setLoading(true)

  const daiExam = grade.daiExams[0]
  const payload = {
    id: daiExam.id,
    comment,
    recommendation: status,
    reviewed: true,
    exam: {
      id: daiExam.exam.id,
      enrollment: daiExam.exam.enrollment,
      examDate: daiExam.exam.examDate,
      examType: daiExam.exam.examType,
      responses: daiExam.exam.responses.map(r => ({
        id: r.id,
        questionType: r.questionType,
        questionText: r.questionText,
        imageUrl: r.imageUrl,
        selectionType: r.selectionType,
        deleted: r.deleted,
        response: r.response
      }))
    }
  }

  axiosInstance.put(saveDAIUrl, payload)
    .then(response => {
      setSuccessMessage('Comentario y estado guardados exitosamente.')
      if (onClose) onClose() // Cierra el modal o el formulario
      setTimeout(() => {
        window.location.reload()
      }, 1500)
      return response.data // Puedes retornar los datos si los necesitas
    })
    .catch(error => {
      console.error('Error al guardar comentario DAI:', error)

      let errorMessage = 'Ocurrió un error al guardar el comentario'

      if (error.response) {
        if (error.response.status === 401) {
          errorMessage = 'No autorizado. Por favor inicie sesión nuevamente.'
        } else if (error.response.status === 403) {
          errorMessage = 'No tiene permisos para realizar esta acción.'
        } else if (error.response.data?.message) {
          errorMessage = error.response.data.message
        } else if (error.response.data) {
          errorMessage = `Error: ${JSON.stringify(error.response.data)}`
        }
      } else if (error.request) {
        errorMessage = 'No se recibió respuesta del servidor. Verifique su conexión.'
      }

      setErrorMessage(errorMessage)
    })
    .finally(() => {
      setLoading(false)
    })
}

// Handlers that transfort to PDF

export const generateAcademicExamPDF = async (gradeData) => {
  // Extraer los datos relevantes del JSON
  const examData = gradeData.academicExams[0]
  const exam = examData.exam
  const grade = examData.grade

  // Crear un nuevo documento PDF
  // eslint-disable-next-line new-cap
  const doc = new jsPDF()

  // Configuración inicial
  let yPosition = 10
  const margin = 20
  const pageWidth = doc.internal.pageSize.getWidth()
  const pageHeight = doc.internal.pageSize.getHeight()
  const maxWidth = pageWidth - margin * 2
  const maxImageWidth = pageWidth - margin * 2 // Ancho máximo para imágenes
  const maxImageHeight = 100 // Altura máxima para imágenes (en puntos)

  // Título del documento
  doc.setFontSize(18)
  doc.setFont('helvetica', 'bold')
  doc.text('Examen Académico', margin, yPosition)
  yPosition += 15

  // Información del estudiante
  doc.setFontSize(12)
  doc.setFont('helvetica', 'normal')
  doc.text(`Estudiante: ${gradeData.person.firstName} ${gradeData.person.firstSurname}`, margin, yPosition)
  yPosition += 8
  doc.text(`Documento: ${gradeData.person.idNumber}`, margin, yPosition)
  yPosition += 8

  // Información del examen
  doc.text(`ID del Examen: ${exam.id}`, margin, yPosition)
  yPosition += 8
  doc.text(`Fecha: ${new Date(exam.examDate).toLocaleDateString('es-ES', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  })}`, margin, yPosition)
  yPosition += 8
  doc.text(`Calificación: ${grade}%`, margin, yPosition)
  yPosition += 15

  // Preguntas y respuestas
  for (const [index, question] of exam.responses.entries()) {
    if (yPosition > pageHeight - 50) {
      doc.addPage()
      yPosition = 20
    }

    // Número de pregunta
    doc.setFontSize(14)
    doc.setFont('helvetica', 'bold')
    doc.text(`Pregunta ${index + 1}:`, margin, yPosition)
    yPosition += 8

    if (question.imageUrl) {
      try {
        // Corregir la URL eliminando duplicados de /api
        let imageUrl = question.imageUrl
        if (imageUrl.startsWith('/api/')) {
          imageUrl = imageUrl.substring(4) // Elimina el primer /api
        }

        // Usar axiosInstance para obtener la imagen
        const response = await axiosInstance.get(imageUrl, {
          responseType: 'blob',
          // Agregar headers si es necesario
          headers: {
            'Cache-Control': 'no-cache',
            Pragma: 'no-cache'
          }
        })

        // Verificar que la respuesta sea válida
        if (!response.data || response.data.size === 0) {
          throw new Error('Respuesta vacía del servidor')
        }

        const imageBlob = response.data
        const imageBase64 = await new Promise((resolve, reject) => {
          // eslint-disable-next-line no-undef
          const reader = new FileReader()
          reader.onloadend = () => resolve(reader.result)
          reader.onerror = reject
          reader.readAsDataURL(imageBlob)
        })

        // Crear elemento de imagen para obtener dimensiones
        // eslint-disable-next-line no-undef
        const img = new Image()
        await new Promise((resolve) => {
          img.onload = resolve
          img.src = imageBase64
        })

        // Calcular dimensiones proporcionales
        let imgWidth = img.width
        let imgHeight = img.height
        const ratio = imgWidth / imgHeight

        if (imgWidth > maxImageWidth) {
          imgWidth = maxImageWidth
          imgHeight = imgWidth / ratio
        }

        if (imgHeight > maxImageHeight) {
          imgHeight = maxImageHeight
          imgWidth = imgHeight * ratio
        }

        // Agregar la imagen con dimensiones calculadas
        doc.addImage(imageBase64, 'JPEG', margin, yPosition, imgWidth, imgHeight)
        yPosition += imgHeight + 10
      } catch (error) {
        console.error('Error al cargar la imagen:', error)
        doc.text('(No se pudo cargar la imagen)', margin, yPosition)
        yPosition += 10
      }
    }

    // Texto de la pregunta
    doc.setFontSize(12)
    doc.setFont('helvetica', 'normal')
    const questionLines = doc.splitTextToSize(question.questionText, maxWidth)
    doc.text(questionLines, margin, yPosition)
    yPosition += questionLines.length * 7 + 10

    // Opciones de respuesta
    question.questionOptions.forEach((option, optIndex) => {
      if (yPosition > 270) {
        doc.addPage()
        yPosition = 20
      }

      let optionText = `${String.fromCharCode(97 + optIndex)}) ${option.option}`

      // Marcar la opción correcta y la seleccionada
      if (option.isCorrect && option.selected) {
        optionText += ' ✓ (Correcta y seleccionada)'
        doc.setTextColor(0, 128, 0) // Verde
      } else if (option.isCorrect) {
        optionText += ' (Correcta)'
        doc.setTextColor(0, 0, 255) // Azul
      } else if (option.selected) {
        optionText += ' ✗ (Seleccionada)'
        doc.setTextColor(255, 0, 0) // Rojo
      }

      const optionLines = doc.splitTextToSize(optionText, maxWidth - 10)
      doc.text(optionLines, margin + 10, yPosition)
      yPosition += optionLines.length * 7 + 5

      // Restaurar color
      doc.setTextColor(0, 0, 0)
    })

    yPosition += 10
  }

  // Guardar el PDF
  doc.save(`Examen_${gradeData.person.firstName}_${gradeData.person.firstSurname}_${exam.id}.pdf`)
}

export const generateDAIExamPDF = async (gradeData) => {
  const examData = gradeData.daiExams[0]
  const exam = examData.exam

  // eslint-disable-next-line new-cap
  const doc = new jsPDF()

  let yPosition = 10
  const margin = 20
  const pageWidth = doc.internal.pageSize.getWidth()
  const pageHeight = doc.internal.pageSize.getHeight()
  const maxWidth = pageWidth - margin * 2
  const maxImageWidth = pageWidth - margin * 2
  const maxImageHeight = 100

  doc.setFontSize(18)
  doc.setFont('helvetica', 'bold')
  doc.text('Examen DAI (Desarrollo Académico Integral)', margin, yPosition)
  yPosition += 15

  doc.setFontSize(12)
  doc.setFont('helvetica', 'normal')
  doc.text(`Estudiante: ${gradeData.person.firstName} ${gradeData.person.firstSurname} ${gradeData.person.secondSurname || ''}`, margin, yPosition)
  yPosition += 8
  doc.text(`Documento: ${gradeData.person.idNumber}`, margin, yPosition)
  yPosition += 8
  doc.text(`ID del Examen: ${exam.id}`, margin, yPosition)
  yPosition += 8
  doc.text(`Fecha: ${new Date(exam.examDate).toLocaleDateString('es-ES', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  })}`, margin, yPosition)
  yPosition += 15

  doc.setFontSize(14)
  doc.setFont('helvetica', 'bold')
  doc.text('Respuestas del Estudiante:', margin, yPosition)
  yPosition += 10

  for (const [index, question] of exam.responses.entries()) {
    if (yPosition > pageHeight - 60) {
      doc.addPage()
      yPosition = 20
    }

    // Imagen si existe
    if (question.imageUrl) {
      try {
        let imageUrl = question.imageUrl
        if (imageUrl.startsWith('/api/')) {
          imageUrl = imageUrl.substring(4)
        }

        const response = await axiosInstance.get(imageUrl, {
          responseType: 'blob',
          headers: {
            'Cache-Control': 'no-cache',
            Pragma: 'no-cache'
          }
        })

        if (!response.data || response.data.size === 0) {
          throw new Error('Imagen vacía')
        }

        const imageBlob = response.data
        const imageBase64 = await new Promise((resolve, reject) => {
          // eslint-disable-next-line no-undef
          const reader = new FileReader()
          reader.onloadend = () => resolve(reader.result)
          reader.onerror = reject
          reader.readAsDataURL(imageBlob)
        })

        const img = new Image()
        await new Promise((resolve) => {
          img.onload = resolve
          img.src = imageBase64
        })

        let imgWidth = img.width
        let imgHeight = img.height
        const ratio = imgWidth / imgHeight

        if (imgWidth > maxImageWidth) {
          imgWidth = maxImageWidth
          imgHeight = imgWidth / ratio
        }

        if (imgHeight > maxImageHeight) {
          imgHeight = maxImageHeight
          imgWidth = imgHeight * ratio
        }

        if (yPosition + imgHeight > pageHeight - 20) {
          doc.addPage()
          yPosition = 20
        }

        doc.addImage(imageBase64, 'JPEG', margin, yPosition, imgWidth, imgHeight)
        yPosition += imgHeight + 10
      } catch (error) {
        console.error('Error al cargar la imagen:', error)
        doc.text('(No se pudo cargar la imagen)', margin, yPosition)
        yPosition += 10
      }
    }

    // Texto de la pregunta
    doc.setFontSize(12)
    doc.setFont('helvetica', 'bold')
    const questionLines = doc.splitTextToSize(`${index + 1}. ${question.questionText}`, maxWidth)
    doc.text(questionLines, margin, yPosition)
    yPosition += questionLines.length * 7 + 5

    // Respuesta
    doc.setFont('helvetica', 'normal')
    doc.setTextColor(0, 0, 128)
    const responseLines = doc.splitTextToSize(`Respuesta: ${question.response}`, maxWidth - 10)
    doc.text(responseLines, margin + 10, yPosition)
    yPosition += responseLines.length * 7 + 10
    doc.setTextColor(0, 0, 0)
    yPosition += 5
  }

  if (examData.comment || examData.recommendation) {
    if (yPosition > pageHeight - 40) {
      doc.addPage()
      yPosition = 20
    }

    doc.setFontSize(14)
    doc.setFont('helvetica', 'bold')
    doc.text('Observaciones:', margin, yPosition)
    yPosition += 10

    doc.setFontSize(12)
    doc.setFont('helvetica', 'normal')

    if (examData.comment) {
      const commentLines = doc.splitTextToSize(`Comentario: ${examData.comment}`, maxWidth)
      doc.text(commentLines, margin, yPosition)
      yPosition += commentLines.length * 7 + 8
    }

    if (examData.recommendation) {
      const recommendationLines = doc.splitTextToSize(`Recomendación: ${examData.recommendation}`, maxWidth)
      doc.text(recommendationLines, margin, yPosition)
      yPosition += recommendationLines.length * 7 + 8
    }
  }

  doc.save(`Examen_DAI_${gradeData.person.firstName}_${gradeData.person.firstSurname}.pdf`)
}

export const handleGetTotalGradesAcademicPages = (setTotalPages, pageSize) => {
  const getTotalPagesUrl = import.meta.env.VITE_GET_TOTAL_PAGES_ENDPOINT_EXAMS_ACADEMIC

  axiosInstance.get(getTotalPagesUrl, { timeout: 10000 })
    .then(response => {
      setTotalPages(response.data % pageSize === 0 ? response.data / pageSize : Math.floor(response.data / pageSize) + 1)
    })
    .catch(error => {
      console.error('Error al obtener el total de páginas:', error)
    })
}

export const handleGetTotalGradesDAIPages = (setTotalPages, pageSize) => {
  const getTotalPagesUrl = import.meta.env.VITE_GET_TOTAL_PAGES_ENDPOINT_EXAMS_DAI

  axiosInstance.get(getTotalPagesUrl, { timeout: 10000 })
    .then(response => {
      setTotalPages(response.data % pageSize === 0 ? response.data / pageSize : Math.floor(response.data / pageSize) + 1)
    })
    .catch(error => {
      console.error('Error al obtener el total de páginas:', error)
    })
}

export const handleGetTotalAcademicGradesSearchPages = (search, pageSize, setTotalPages) => {
  const getTotalPagesUrl = import.meta.env.VITE_GET_TOTAL_PAGES_FOR_SEARCH_ENDPOINT_EXAMS_ACADEMIC
  axiosInstance.get(`${getTotalPagesUrl}?value=${search}`, { timeout: 10000 })
    .then(response => {
      setTotalPages(response.data % pageSize === 0 ? response.data / pageSize : Math.floor(response.data / pageSize) + 1)
    })
    .catch(error => {
      console.error('Error al obtener el total de páginas:', error)
    })
}

export const handleGetTotalDAIGradesSearchPages = (search, pageSize, setTotalPages) => {
  const getTotalPagesUrl = import.meta.env.VITE_GET_TOTAL_PAGES_FOR_SEARCH_ENDPOINT_EXAMS_DAI
  axiosInstance.get(`${getTotalPagesUrl}?value=${search}`, { timeout: 10000 })
    .then(response => {
      setTotalPages(response.data % pageSize === 0 ? response.data / pageSize : Math.floor(response.data / pageSize) + 1)
    })
    .catch(error => {
      console.error('Error al obtener el total de páginas:', error)
    })
}
