import axios from '../../../../../config/axiosConfig'

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


const getAllGradesUrl = import.meta.env.VITE_GET_ALL_GRADES_ENDPOINT
export const handleGetAllGrades = async (setGrades, setLoading, setErrorMessage) => {
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

const searchStudentGradeUrl = import.meta.env.VITE_SEARCH_STUDENT_GRADE_BY_STUDENT_VALUES_ENDPOINT
export const handleSearchGrades = (search, setGrades) => { }

const searchDownloadEnglishUrl = import.meta.env.VITE_DOWNLOAD_ENGLISH_BY_STUDENT_VALUES_ENDPOINT
export const handleDownloadEnglishExam = (filename, setErrorMessage) => {
  // Send request to the server


  // Transform data into a PDF and download it
}

const searchDownloadAcademicUrl = import.meta.env.VITE_DOWNLOAD_ACADEMIC_BY_STUDENT_VALUES_ENDPOINT
export const handleDownloadAcademicExam = (enrollment, setErrorMessage) => {
  // Repeat the same process as above
}

const searchDownloadDAIUrl = import.meta.env.VITE_DOWNLOAD_DAI_BY_STUDENT_VALUES_ENDPOINT
export const handleDownloadDAIExam = (enrollment, setErrorMessage) => {
  // Repeat the same process as above
}


const saveEnglishUrl = import.meta.env.VITE_SAVE_ENGLISH_BY_STUDENT_VALUES_ENDPOINT
export const handleSaveEnglishLevel = async (
  formData,
  setIsEditing,
  setErrorMessage,
  setSuccessMessage,
  onClose
) => {
  try {
    // Validación básica
    if (!formData.englishLevel || formData.englishLevel === 'porDefecto') {
      setErrorMessage('Por favor seleccione un nivel de inglés válido');
      return;
    }

    console.log('Datos simulados a enviar:', formData);
    
    // Simulación de llamada API exitosa (similar al ejemplo que me mostraste)
    await new Promise(resolve => setTimeout(resolve, 1000));
    setSuccessMessage('El nivel de inglés se actualizó correctamente.');
    // Primero mostrar el mensaje de éxito
    
    
    // Luego cerrar el modal (sin setTimeout)
    if (onClose) {
      onClose();
    }
    
    // Si hay un setIsEditing (como en tu ejemplo)
    if (setIsEditing) {
      setIsEditing(false);
    }
    
  } catch (error) {
    console.error('Error al guardar el nivel de inglés:', error);
    setErrorMessage('Ocurrió un error al guardar el nivel de inglés');
    throw error; // Importante para manejar el error en el componente
  }
};

const saveAcademicUrl = import.meta.env.VITE_SAVE_ACADEMIC_BY_STUDENT_VALUES_ENDPOINT
export const handleSaveAcademicGrade = (e, formData, setIsEditing, setErrorMessage, setSuccessMessage) => {}

const saveDAIUrl = import.meta.env.VITE_SAVE_DAI_BY_STUDENT_VALUES_ENDPOINT
export const handleSaveDAIComment = (enrollment, setErrorMessage) => {}


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