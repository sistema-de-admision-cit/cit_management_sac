import axios from 'axios'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL
const POST_PDF_REPORT = import.meta.env.VITE_POST_PDF_REPORT
const POST_CSV_REPORT = import.meta.env.VITE_POST_CSV_REPORT

export const generateReport = async (request, format) => {
  try {
    const processedRequest = {
      ...request,
      reportType: request.reportType === 'ALL' ? null : request.reportType,
      knownThroughFilter: request.knownThroughFilter === 'ALL' ? null : request.knownThroughFilter,
      gradeFilter: request.gradeFilter === 'ALL' ? null : request.gradeFilter,
      statusFilter: request.statusFilter === 'ALL' ? null : request.statusFilter,
      provinceFilter: request.provinceFilter === 'ALL' ? null : request.provinceFilter,
      gradeTypeFilter: request.gradeTypeFilter === 'ALL' ? null : request.gradeTypeFilter
    }

    const endpoint = format === 'PDF' ? POST_PDF_REPORT : POST_CSV_REPORT
    const response = await axios.post(`${API_BASE_URL}${endpoint}`, processedRequest, {
      responseType: 'blob'
    })
    return response.data
  } catch (error) {
    console.error(`Error generating ${format} report:`, error)
    throw error
  }
}

export const getEnumOptions = async () => {
  return {
    reportTypes: [
      { value: 'KNOWN_THROUGH', label: 'Conocido por' },
      { value: 'GRADE_TO_ENROLL', label: 'Grado a matricular' },
      { value: 'PROCESS_STATUS', label: 'Estado del proceso' },
      { value: 'PROVINCE', label: 'Provincia' },
      { value: 'GRADES', label: 'Calificaciones' }
    ],
    knownThroughOptions: [
      { value: 'ALL', label: 'Todos' },
      { value: 'SM', label: 'Redes Sociales' },
      { value: 'OH', label: 'Open House' },
      { value: 'FD', label: 'Amigos' },
      { value: 'FM', label: 'Familia' },
      { value: 'OT', label: 'Otro' }
    ],
    gradeOptions: [
      { value: 'FIRST', label: 'Primer Grado' },
      { value: 'SECOND', label: 'Segundo Grado' },
      { value: 'THIRD', label: 'Tercer Grado' },
      { value: 'FOURTH', label: 'Cuarto Grado' },
      { value: 'FIFTH', label: 'Quinto Grado' },
      { value: 'SIXTH', label: 'Sexto Grado' },
      { value: 'SEVENTH', label: 'Séptimo Grado' },
      { value: 'EIGHTH', label: 'Octavo Grado' },
      { value: 'NINTH', label: 'Noveno Grado' },
      { value: 'TENTH', label: 'Décimo Grado' }
    ],
    processStatusOptions: [
      { value: 'PENDING', label: 'Pendiente' },
      { value: 'ELIGIBLE', label: 'Elegible' },
      { value: 'INELIGIBLE', label: 'No elegible' },
      { value: 'ACCEPTED', label: 'Aceptado' },
      { value: 'REJECTED', label: 'Rechazado' }
    ],
    gradeTypeOptions: [
      { value: 'ENGLISH', label: 'Inglés' },
      { value: 'ACADEMIC', label: 'Académico' },
      { value: 'PREVIOUS', label: 'Nota Anterior' }
    ],
    provinceOptions: [
      { value: 'SAN JOSE', label: 'San José' },
      { value: 'ALAJUELA', label: 'Alajuela' },
      { value: 'CARTAGO', label: 'Cartago' },
      { value: 'HEREDIA', label: 'Heredia' },
      { value: 'GUANACASTE', label: 'Guanacaste' },
      { value: 'PUNTARENAS', label: 'Puntarenas' },
      { value: 'LIMON', label: 'Limón' }
    ]
  }
}
