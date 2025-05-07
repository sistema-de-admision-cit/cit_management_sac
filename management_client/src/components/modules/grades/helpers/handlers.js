import axiosInstance from '../../../../config/axiosConfig'
import { jsPDF } from 'jspdf'

// Handlers that Get Data from API
const getAllAcademicGradesUrl = import.meta.env.VITE_GET_ALL_ACADEMIC_GRADES_ENDPOINT
export const handleGetAllAcademicGrades = (
  page = 0,
  setGrades,
  setLoading,
  setErrorMessage,
  setSuccessMessage
) => {
  const pageSize = 25
  const examType = 'ACA'
  const url = `${getAllAcademicGradesUrl}/${examType}`

  setLoading(true)

  axiosInstance.get(url, {
    params: {
      page,
      size: pageSize
    }
  })
    .then(response => {
      const data = response.data

      setGrades(data.content || data)

      if (setSuccessMessage) {
        setSuccessMessage('Calificaciones académicas cargadas correctamente')
      }

      return {
        totalPages: data.totalPages || 1,
        currentPage: data.number || page,
        totalElements: data.totalElements || (Array.isArray(data) ? data.length : 0)
      }
    })
    .catch(error => {
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
    })
    .finally(() => {
      setLoading(false)
    })
}

const getAllDaiGradesUrl = import.meta.env.VITE_GET_ALL_DAI_GRADES_ENDPOINT
export const handleGetAllDaiGrades = (
  page = 0,
  setGrades,
  setLoading,
  setErrorMessage,
  setSuccessMessage
) => {
  const pageSize = 25
  const examType = 'DAI'
  const url = `${getAllDaiGradesUrl}/${examType}`

  setLoading(true)

  axiosInstance.get(url, {
    params: {
      page,
      size: pageSize
    }
  })
    .then(response => {
      const data = response.data

      setGrades(data.content || data)
      if (setSuccessMessage) {
        setSuccessMessage('Calificaciones DAI cargadas correctamente')
      }

      return {
        totalPages: data.totalPages || 1,
        currentPage: data.number || page,
        totalElements: data.totalElements || (Array.isArray(data) ? data.length : 0)
      }
    })
    .catch(error => {
      console.error('Error al obtener calificaciones:', error)

      let errorMsg = 'No se pudieron cargar las calificaciones DAI'

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
// TODO: Implementar la busqueda de datos desde el servidor
const searchAcademicUrl = import.meta.env.VITE_GET_ACADEMIC_GRADES_SEARCH_ENDPOINT
export const handleSearchAcademicGrades = async (
  search,
  setGrades,
  setLoading,
  setErrorMessage,
  setSuccessMessage
) => {
  const examType = 'ACA' // suponiendo que siempre es este tipo

  if (!search || search.trim() === '') {
    setErrorMessage?.('Debe ingresar un valor para buscar')
    return
  }

  const url = `${searchAcademicUrl}/${search}/${examType}`

  try {
    setLoading?.(true)

    const response = await axiosInstance.get(url)
    const data = response.data
    setGrades(data || [])
    // setSuccessMessage?.('Resultados de búsqueda cargados correctamente')
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
  const examType = 'DAI' // suponiendo que siempre es este tipo

  if (!search || search.trim() === '') {
    setErrorMessage?.('Debe ingresar un valor para buscar')
    return
  }

  const url = `${searchDAIUrl}/${search}/${examType}`

  try {
    setLoading?.(true)

    const response = await axiosInstance.get(url)
    const data = response.data
    setGrades(data || [])
    // setSuccessMessage?.('Resultados de búsqueda cargados correctamente')
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

  const daiExam = grade.daiExams[0] // O ajusta si seleccionas otro índice
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
  const examData = gradeData.academicExams[0];
  const exam = examData.exam;
  const grade = examData.grade;

  // Crear un nuevo documento PDF
  // eslint-disable-next-line new-cap
  const doc = new jsPDF();

  // Configuración inicial
  let yPosition = 10;
  const margin = 20;
  const pageWidth = doc.internal.pageSize.getWidth();
  const pageHeight = doc.internal.pageSize.getHeight();
  const maxWidth = pageWidth - margin * 2;
  const maxImageWidth = pageWidth - margin * 2; // Ancho máximo para imágenes
  const maxImageHeight = 100; // Altura máxima para imágenes (en puntos)

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
      doc.addPage();
      yPosition = 20;
    }

    // Número de pregunta
    doc.setFontSize(14);
    doc.setFont('helvetica', 'bold');
    doc.text(`Pregunta ${index + 1}:`, margin, yPosition);
    yPosition += 8;

    if (question.imageUrl) {
      try {
        // Corregir la URL eliminando duplicados de /api
        let imageUrl = question.imageUrl;
        if (imageUrl.startsWith('/api/')) {
          imageUrl = imageUrl.substring(4); // Elimina el primer /api
        }

        // Usar axiosInstance para obtener la imagen
        const response = await axiosInstance.get(imageUrl, {
          responseType: 'blob',
          // Agregar headers si es necesario
          headers: {
            'Cache-Control': 'no-cache',
            'Pragma': 'no-cache'
          }
        });

        // Verificar que la respuesta sea válida
        if (!response.data || response.data.size === 0) {
          throw new Error('Respuesta vacía del servidor');
        }

        const imageBlob = response.data;
        const imageBase64 = await new Promise((resolve, reject) => {
          const reader = new FileReader();
          reader.onloadend = () => resolve(reader.result);
          reader.onerror = reject;
          reader.readAsDataURL(imageBlob);
        });

        // Crear elemento de imagen para obtener dimensiones
        const img = new Image();
        await new Promise((resolve) => {
          img.onload = resolve;
          img.src = imageBase64;
        });

        // Calcular dimensiones proporcionales
        let imgWidth = img.width;
        let imgHeight = img.height;
        const ratio = imgWidth / imgHeight;

        if (imgWidth > maxImageWidth) {
          imgWidth = maxImageWidth;
          imgHeight = imgWidth / ratio;
        }

        if (imgHeight > maxImageHeight) {
          imgHeight = maxImageHeight;
          imgWidth = imgHeight * ratio;
        }

        // Agregar la imagen con dimensiones calculadas
        doc.addImage(imageBase64, 'JPEG', margin, yPosition, imgWidth, imgHeight);
        yPosition += imgHeight + 10;
        
      } catch (error) {
        console.error('Error al cargar la imagen:', error);
        doc.text('(No se pudo cargar la imagen)', margin, yPosition);
        yPosition += 10;
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

export const generateDAIExamPDF = (gradeData) => {
  // Extraer los datos relevantes del JSON (tomamos el primer examen DAI)
  const examData = gradeData.daiExams[0]
  const exam = examData.exam

  // Crear un nuevo documento PDF
  const doc = new jsPDF()

  // Configuración inicial
  let yPosition = 10
  const margin = 20
  const pageWidth = doc.internal.pageSize.getWidth()
  const maxWidth = pageWidth - margin * 2

  // Título del documento
  doc.setFontSize(18)
  doc.setFont('helvetica', 'bold')
  doc.text('Examen DAI (Desarrollo Académico Integral)', margin, yPosition)
  yPosition += 15

  // Información del estudiante
  doc.setFontSize(12)
  doc.setFont('helvetica', 'normal')
  doc.text(`Estudiante: ${gradeData.person.firstName} ${gradeData.person.firstSurname} ${gradeData.person.secondSurname || ''}`, margin, yPosition)
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
  yPosition += 15

  // Preguntas y respuestas
  doc.setFontSize(14)
  doc.setFont('helvetica', 'bold')
  doc.text('Respuestas del Estudiante:', margin, yPosition)
  yPosition += 10

  exam.responses.forEach((question, index) => {
    if (yPosition > 250) {
      doc.addPage()
      yPosition = 20
    }

    // Texto de la pregunta
    doc.setFontSize(12)
    doc.setFont('helvetica', 'bold')
    const questionLines = doc.splitTextToSize(`${index + 1}. ${question.questionText}`, maxWidth)
    doc.text(questionLines, margin, yPosition)
    yPosition += questionLines.length * 7 + 5

    // Respuesta del estudiante
    doc.setFont('helvetica', 'normal')
    doc.setTextColor(0, 0, 128) // Azul oscuro para las respuestas

    const responseLines = doc.splitTextToSize(`Respuesta: ${question.response}`, maxWidth - 10)
    doc.text(responseLines, margin + 10, yPosition)
    yPosition += responseLines.length * 7 + 10

    // Restaurar color
    doc.setTextColor(0, 0, 0)

    yPosition += 5
  })

  // Comentarios y recomendaciones si existen
  if (examData.comment || examData.recommendation) {
    if (yPosition > 250) {
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

  // Guardar el PDF
  doc.save(`Examen_DAI_${gradeData.person.firstName}_${gradeData.person.firstSurname}.pdf`)
}
