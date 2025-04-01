import { useEffect, useState } from 'react'
import SectionLayout from '../../../core/global/molecules/SectionLayout'
import useMessages from '../../../core/global/hooks/useMessages'
import AcademicGradesTable from '../organisms/AcademicGradesTable'
import AcademicSearchBar from '../molecules/AcademicSearchBar'
import {handleGetAllAcademicGrades} from '../helpers/handlers'
import '../../../../assets/styles/grades/grades-management-view.css'

const AcademicGradesManagementView = () => {
  const [loading, setLoading] = useState(false)
  const [grades, setGrades] = useState([])
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(1)
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  const loadGrades = async (page = 0) => {
    const result = await handleGetAllAcademicGrades(
      page,
      setGrades,
      setLoading,
      setErrorMessage
    );
    
    if (result) {
      setTotalPages(result.totalPages);
      setCurrentPage(result.currentPage);
    }
  };

  const loadMockGrades = (page = 0) => {
    setLoading(true);
    
    // Simulamos un pequeño retraso como si fuera una petición real
    setTimeout(() => {
      const startIndex = page * itemsPerPage;
      const paginatedGrades = mockGrades.slice(startIndex, startIndex + itemsPerPage);
      
      setGrades(paginatedGrades);
      setTotalPages(Math.ceil(mockGrades.length / itemsPerPage));
      setCurrentPage(page);
      setLoading(false);
    }, 300);
  };

  // Cargar datos iniciales
  /*useEffect(() => {
    loadGrades(0)
  }, [])
  
  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      loadGrades(newPage);
    }
  };  
  */
  const itemsPerPage = 10;

  useEffect(() => {
    loadMockGrades(0);
  }, []);

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      loadMockGrades(newPage);
    }
  };


  return (
    <SectionLayout title='Resultados de exámenes Académicos'>
      <div className='grade-management-view'>
        <AcademicSearchBar/>
        <AcademicGradesTable
          grades={grades}
          loading={loading}
          onPageChange={handlePageChange}
          currentPage={currentPage}
          totalPages={totalPages}
        />
      </div>
    </SectionLayout>
  );
};

export default AcademicGradesManagementView

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