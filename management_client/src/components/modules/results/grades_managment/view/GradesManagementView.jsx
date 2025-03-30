import { useEffect, useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import '../../../../../assets/styles/results/grades/grades-management-view.css'
import GradesTable from '../organisms/GradesTable'
import GradesSearchBar from '../molecules/GradesSearchBar'
import { handleGetAllGrades, handleSearchGrades } from '../helpers/handlers'
import useMessages from '../../../../core/global/hooks/useMessages'


const GradesManagementView = () => {
  const [loading, setLoading] = useState(false)
  const [grades, setGrades] = useState([])
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  // Cargar datos iniciales
  useEffect(() => {
    const loadGrades = async () => {
      await handleGetAllGrades(
        setGrades,
        setLoading,
        setErrorMessage
      )
    }
    loadGrades()
  }, [])

  return (
    <SectionLayout title='Gestión de Calificaciones'>
      <div className='grade-management-view'>
        <h1>Gestión de Calificaciones</h1>
        <p className='description'>
          Aquí puedes gestionar las calificaciones de los exámenes de los estudiantes.
        </p>
        <GradesSearchBar/>
        <GradesTable
          grades={grades}
          loading={loading}
        />
      </div>
      {renderMessages()}
    </SectionLayout>
  )
}

export default GradesManagementView