import { useState } from 'react'
import useMessages from '../../../../core/global/hooks/useMessages'
import Spinner from '../../../../core/global/atoms/Spinner'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import { fetchTrackTestScores } from '../helpers/handlers'
import MethodSelection from '../molecules/MethodSelection'
import LogsList from '../molecules/LogsList'
import CSVUploadSection from '../organisms/CSVUploadSection'
import APILoadSection from '../organisms/APILoadSection'
import '../../../../../assets/styles/results/load_results/exam-score-management-view.css'

const ExamScoreManagementView = () => {
  const [loading, setLoading] = useState(false)
  const [uploadMethod, setUploadMethod] = useState('csv')
  const [logs, setLogs] = useState([])
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  const handleAPILoad = async () => {
    setLoading(true)
    setLogs((prevLogs) => [...prevLogs, 'Iniciando carga desde API...'])
    try {
      await fetchTrackTestScores(setSuccessMessage, setErrorMessage)
      setLogs((prevLogs) => [...prevLogs, 'Carga desde API completada exitosamente.'])
    } catch (error) {
      setErrorMessage('Error al cargar las notas desde la API.')
      setLogs((prevLogs) => [...prevLogs, 'Error al cargar desde API.'])
    } finally {
      setLoading(false)
    }
  }

  const handleCSVLoad = (file) => {
    setLoading(true)
    setLogs((prevLogs) => [...prevLogs, 'Iniciando carga desde CSV...'])
    // Procesa el archivo CSV aquí
    setLoading(false)
    setLogs((prevLogs) => [...prevLogs, 'Carga desde CSV completada.'])
  }

  return (
    <SectionLayout title='Cargar Notas de Ingles'>
      <div className='container'>
        <h1>Cargar Notas de Ingles</h1>
        {loading && <Spinner />}
        {renderMessages()}
        <MethodSelection uploadMethod={uploadMethod} handleChange={(e) => setUploadMethod(e.target.value)} />

        {uploadMethod === 'csv' && <CSVUploadSection handleCSVLoad={handleCSVLoad} />}
        {uploadMethod === 'api' && <APILoadSection handleAPILoad={handleAPILoad} loading={loading} />}

        <LogsList logs={logs} />
      </div>
    </SectionLayout>
  )
}

export default ExamScoreManagementView
