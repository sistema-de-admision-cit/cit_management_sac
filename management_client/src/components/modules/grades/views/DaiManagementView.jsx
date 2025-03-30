import { useEffect, useState } from 'react'
import SectionLayout from '../../../core/global/molecules/SectionLayout'
import useMessages from '../../../core/global/hooks/useMessages'
import DaiGradesTable from '../organisms/DaiGradesTable'
import {handleGetAllDAIGrades} from '../helpers/handlers'
import '../../../../assets/styles/grades/grades-management-view.css'

const DaiGradesManagementView = () => {
  const [loading, setLoading] = useState(false)
  const [grades, setGrades] = useState([])
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  // Cargar datos iniciales
  useEffect(() => {
    const loadGrades = async () => {
      await handleGetAllDAIGrades(
        setGrades,
        setLoading,
        setErrorMessage
      )
    }
    loadGrades()
  }, [])

  return (
    <SectionLayout title='Resultados de examenes DAI'>
      <div className='grade-management-view'>
        <h1>Resultados de examenes DAI</h1>
        <p className='description'>
          Aquí puedes visualizar los resultados de los exámenes DAI.
        </p>
        <DaiGradesTable
          grades={grades}
          loading={loading}
        />
      </div>
      {renderMessages()}
    </SectionLayout>
  )
}

export default DaiGradesManagementView