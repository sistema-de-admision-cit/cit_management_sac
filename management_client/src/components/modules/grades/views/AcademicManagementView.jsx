import { useEffect, useState } from 'react'
import SectionLayout from '../../../core/global/molecules/SectionLayout'
import useMessages from '../../../core/global/hooks/useMessages'
import AcademicGradesTable from '../organisms/AcademicGradesTable'
import {handleGetAllAcademicGrades} from '../helpers/handlers'
import '../../../../assets/styles/grades/grades-management-view.css'

const AcademicGradesManagementView = () => {
  const [loading, setLoading] = useState(false)
  const [grades, setGrades] = useState([])
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  // Cargar datos iniciales
  useEffect(() => {
    const loadGrades = async () => {
      await handleGetAllAcademicGrades(
        setGrades,
        setLoading,
        setErrorMessage
      )
    }
    loadGrades()
  }, [])

  return (
    <SectionLayout title='Resultados de examenes Académicos'>
      <div className='grade-management-view'>
        <h1>Resultados de examenes Académicos</h1>
        <p className='description'>
          Aquí puedes visualizar los resultados de los exámenes académicos.
        </p>
        <AcademicGradesTable
          grades={grades}
          loading={loading}
        />
      </div>
      {renderMessages()}
    </SectionLayout>
  )
}

export default AcademicGradesManagementView