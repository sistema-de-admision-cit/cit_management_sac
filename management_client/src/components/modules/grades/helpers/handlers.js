  //import axios from '../../../../../config/axiosConfig'

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

const getAllAcademicUrl = import.meta.env.VITE_GET_ALL_ACADEMIC_ENDPOINT
export const handleGetAllAcademicGrades = async (setGrades, setLoading, setErrorMessage) => {
  setLoading(true)
  try {
    // Simulamos un delay de red (1 segundo)
    //await new Promise(resolve => setTimeout(resolve, 1000))
    setGrades(mockGrades)
  } catch (error) {
    setErrorMessage('Error al cargar los datos de prueba')
    console.error('Mock data error:', error)
  } finally {
    setLoading(false)
  }
}

const getAllEnglishUrl = import.meta.env.VITE_GET_ALL_ENGLISH_ENDPOINT
export const handleGetAllEnglishGrades = async (setGrades, setLoading, setErrorMessage) => {
    setLoading(true)
    try {
      // Simulamos un delay de red (1 segundo)  
      //await new Promise(resolve => setTimeout(resolve, 1000))
      setGrades(mockGrades)
    } catch (error) {
      setErrorMessage('Error al cargar los datos de prueba')
      console.error('Mock data error:', error)
    } finally {
      setLoading(false)
    }
}

const getAllDAIUrl = import.meta.env.VITE_GET_ALL_ACADEMIC_ENDPOINT
export const handleGetAllDAIGrades = async (setGrades, setLoading, setErrorMessage) => {
    setLoading(true)
    try {
      // Simulamos un delay de red (1 segundo)
      //await new Promise(resolve => setTimeout(resolve, 1000))
      setGrades(mockGrades)
    } catch (error) {
      setErrorMessage('Error al cargar los datos de prueba')
      console.error('Mock data error:', error)
    } finally {
      setLoading(false)
    }
}

// Handlers that Search Data from API
// TODO: Implementar la busqueda de datos desde el servidor
const searchAcademicUrl = import.meta.env.VITE_SEARCH_ACADEMIC_BY_STUDENT_VALUES_ENDPOINT
export const handleSearchAcademicGrades = async (search, setGrades) => {}

const searchEnglishUrl = import.meta.env.VITE_SEARCH_ENGLISH_BY_STUDENT_VALUES_ENDPOINT
export const handleSearchEnglishGrades = async (search, setGrades) => {}

const searchDAIUrl = import.meta.env.VITE_SEARCH_DAI_BY_STUDENT_VALUES_ENDPOINT
export const handleSearchDAIGrades = async (search, setGrades) => {}

// Handlers that Download Data from API
// TODO: Implementar la descarga de archivos PDF desde el servidor
const downloadAcademicUrl = import.meta.env.VITE_DOWNLOAD_ACADEMIC_BY_STUDENT_VALUES_ENDPOINT
export const handleDownloadAcademicExam = async (examID, setErrorMessage) => {
  // Must take the data from the API and download it as a PDF file
}

const downloadEnglishUrl = import.meta.env.VITE_DOWNLOAD_ENGLISH_BY_STUDENT_VALUES_ENDPOINT
export const handleDownloadEnglishExam = async (enrollment, setErrorMessage) => {}

const downloadDAIUrl = import.meta.env.VITE_DOWNLOAD_DAI_BY_STUDENT_VALUES_ENDPOINT
export const handleDownloadDAIExam = async (enrollment, setErrorMessage) => {}

// Handlers that Save Data from API
// TODO: Implementar envio de comentario DAI al servidor
const saveDAIUrl = import.meta.env.VITE_SAVE_DAI_BY_STUDENT_VALUES_ENDPOINT
export const handleSaveDAIComment = async (enrollment, grade, setErrorMessage, setSuccessMessage) => {}


// Data simulating English Grades

// Data simulating Academic Grades

// Data simulationg DAI Grades

// Datos mock de ejemplo
const mockGrades = [
  {
    student: {
      id: '1',
      person: {
        idNumber: '123456789',
        firstName: 'María',
        firstSurname: 'Gómez',
        secondSurname: 'Pérez',
      },
    },
    english: {
      level: 'B1',
    },
    academic: {
      grade: 92,
    },
    DAI: {
      grade: "RECHAZAR",
      comments: 'Puede mejorar',
    },
    finalGrade: 82,
    enrollmentDate: '2023-10-15T00:00:00Z'
  },
  {
    student: {
      id: '2',
      person: {
        idNumber: '987654321',
        firstName: 'Carlos',
        firstSurname: 'Rodríguez',
        secondSurname: 'López',
      },
    },
    english: {
      level: 'A2',
    },
    academic: {
      grade: 85,
    },
    DAI: null,
    finalGrade: 72,
    enrollmentDate: '2023-09-20T00:00:00Z'
  },
  {
    student: {
      id: '3',
      person: {
        idNumber: '456789123',
        firstName: 'Ana',
        firstSurname: 'Martínez',
        secondSurname: 'Sánchez',
      },
    },
    english: {
      level: 'B2',
    },
    academic: {
      grade: 88,
    },
    DAI: {
      grade: "ADMITIR",
      comments: 'Excelente desempeño',
    },
    finalGrade: 90,
    enrollmentDate: '2023-11-05T00:00:00Z'
  },
  {
    student: {
      id: '4',
      person: {
        idNumber: '321654987',
        firstName: 'Luis',
        firstSurname: 'Hernández',
        secondSurname: 'García',
      },
    },
    english: {
      level: 'A1',
    },
    academic: {
      grade: 78,
    },
    DAI: {
      grade: "RECHAZAR",
      comments: 'Necesita reforzar conocimientos básicos',
    },
    finalGrade: 65,
    enrollmentDate: '2023-08-12T00:00:00Z'
  },
  {
    student: {
      id: '5',
      person: {
        idNumber: '654123987',
        firstName: 'Sofía',
        firstSurname: 'Díaz',
        secondSurname: 'Fernández',
      },
    },
    english: {
      level: 'C1',
    },
    academic: {
      grade: 95,
    },
    DAI: {
      grade: "ADMITIR",
      comments: 'Destacado en todas las áreas',
    },
    finalGrade: 96,
    enrollmentDate: '2023-12-01T00:00:00Z'
  },
  {
    student: {
      id: '6',
      person: {
        idNumber: '789321654',
        firstName: 'Javier',
        firstSurname: 'Pérez',
        secondSurname: 'Ramírez',
      },
    },
    english: {
      level: 'B1',
    },
    academic: {
      grade: 82,
    },
    DAI: null,
    finalGrade: 75,
    enrollmentDate: '2023-10-22T00:00:00Z'
  },
  {
    student: {
      id: '7',
      person: {
        idNumber: '159753486',
        firstName: 'Elena',
        firstSurname: 'Gutiérrez',
        secondSurname: 'Morales',
      },
    },
    english: {
      level: 'A2',
    },
    academic: {
      grade: 76,
    },
    DAI: {
      grade: "RECHAZAR",
      comments: 'Falta preparación en lógica',
    },
    finalGrade: 68,
    enrollmentDate: '2023-09-15T00:00:00Z'
  },
  {
    student: {
      id: '8',
      person: {
        idNumber: '357951468',
        firstName: 'Pedro',
        firstSurname: 'Jiménez',
        secondSurname: 'Ruiz',
      },
    },
    english: {
      level: 'B2',
    },
    academic: {
      grade: 89,
    },
    DAI: {
      grade: "ADMITIR",
      comments: 'Buen potencial',
    },
    finalGrade: 87,
    enrollmentDate: '2023-11-18T00:00:00Z'
  },
  {
    student: {
      id: '9',
      person: {
        idNumber: '852963741',
        firstName: 'Laura',
        firstSurname: 'Moreno',
        secondSurname: 'Alvarez',
      },
    },
    english: {
      level: 'A2',
    },
    academic: {
      grade: 81,
    },
    DAI: null,
    finalGrade: 74,
    enrollmentDate: '2023-10-05T00:00:00Z'
  },
  {
    student: {
      id: '10',
      person: {
        idNumber: '258147369',
        firstName: 'Diego',
        firstSurname: 'Castro',
        secondSurname: 'Ortega',
      },
    },
    english: {
      level: 'B1',
    },
    academic: {
      grade: 84,
    },
    DAI: {
      grade: "ADMITIR",
      comments: 'Cumple con los requisitos mínimos',
    },
    finalGrade: 80,
    enrollmentDate: '2023-09-30T00:00:00Z'
  },
  {
    student: {
      id: '11',
      person: {
        idNumber: '147258369',
        firstName: 'Isabel',
        firstSurname: 'Navarro',
        secondSurname: 'Torres',
      },
    },
    english: {
      level: 'C1',
    },
    academic: {
      grade: 93,
    },
    DAI: {
      grade: "ADMITIR",
      comments: 'Excelente candidata',
    },
    finalGrade: 94,
    enrollmentDate: '2023-12-10T00:00:00Z'
  },
  {
    student: {
      id: '12',
      person: {
        idNumber: '369258147',
        firstName: 'Miguel',
        firstSurname: 'Romero',
        secondSurname: 'Serrano',
      },
    },
    english: {
      level: 'A1',
    },
    academic: {
      grade: 70,
    },
    DAI: {
      grade: "RECHAZAR",
      comments: 'Necesita mejorar en todas las áreas',
    },
    finalGrade: 62,
    enrollmentDate: '2023-08-25T00:00:00Z'
  },
  {
    student: {
      id: '13',
      person: {
        idNumber: '486275193',
        firstName: 'Carmen',
        firstSurname: 'Vega',
        secondSurname: 'Mendoza',
      },
    },
    english: {
      level: 'B2',
    },
    academic: {
      grade: 87,
    },
    DAI: {
      grade: "ADMITIR",
      comments: 'Buena base teórica',
    },
    finalGrade: 85,
    enrollmentDate: '2023-11-22T00:00:00Z'
  }
]