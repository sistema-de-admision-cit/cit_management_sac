import { useEffect, useState } from 'react'
import SectionLayout from '../../../core/global/molecules/SectionLayout'
import useMessages from '../../../core/global/hooks/useMessages'
import EnglishGradesTable from '../organisms/EnglishGradesTable'
import { handleGetAllEnglishGrades } from '../helpers/handlers'
import '../../../../assets/styles/grades/grades-management-view.css'

const EnglishGradesManagementView = () => {
  const [loading, setLoading] = useState(false)
  const [grades, setGrades] = useState([])
  const { setErrorMessage, renderMessages } = useMessages()

  // Cargar datos iniciales
  useEffect(() => {
    const loadGrades = async () => {
      await handleGetAllEnglishGrades(
        setGrades,
        setLoading,
        setErrorMessage
      )
    }
    loadGrades()
  }, [])

  return (
    <SectionLayout title='Resultados de examenes de Ingles'>
      <div className='grade-management-view'>
        <h1>Resultados de examenes de Ingles</h1>
        <p className='description'>
          Aquí puedes visualizar los resultados de los exámenes de Ingles.
        </p>
        <EnglishGradesTable
          grades={grades}
          loading={loading}
        />
      </div>
      {renderMessages()}
    </SectionLayout>
  )
}

export default EnglishGradesManagementView
