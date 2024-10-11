import { useState } from 'react'
import useMessages from '../../../../core/global/hooks/useMessages'
import Spinner from '../../../../core/global/atoms/Spinner'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import { fetchTrackTestScores, handleEnglishScoresFileUpload, handleEnglishScoresFileProcess } from '../helpers/handlers'
import MethodSelection from '../molecules/MethodSelection'
import LogsList from '../molecules/LogsList'
import CSVUploadSection from '../organisms/CSVUploadSection'
import APILoadSection from '../organisms/APILoadSection'
import '../../../../../assets/styles/results/load_results/exam-score-management-view.css'

const ExamScoreManagementView = () => {
  const [loading, setLoading] = useState(false)
  const [englishScores, setEnglishScores] = useState({})
  const [uploadMethod, setUploadMethod] = useState('csv')
  const [logs, setLogs] = useState([])
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  const handleAPILoad = async () => {
    setLoading(true)
    setLogs([])
    setLogs((prevLogs) => [...prevLogs, {
      status: 'info',
      message: 'Iniciando carga desde API...'
    }])
    try {
      await fetchTrackTestScores(setSuccessMessage, setErrorMessage)
      setLogs((prevLogs) => [...prevLogs, {
        status: 'info',
        message: 'Carga desde API completada exitosamente.'
      }])
    } catch (error) {
      setErrorMessage('Error al cargar las notas desde la API.')
      setLogs((prevLogs) => [...prevLogs, {
        status: 'error',
        message: error.message
      }])
    } finally {
      setLoading(false)
    }
  }

  return (
    <SectionLayout title='Cargar Notas de Ingles'>
      <div className='container'>
        <h1>Cargar Notas de Ingles</h1>
        {loading && <Spinner />}
        {renderMessages()}
        {/* <MethodSelection uploadMethod={uploadMethod} handleChange={(e) => setUploadMethod(e.target.value)} /> */}

        <CSVUploadSection
          handleFileLoad={(file, e) => handleEnglishScoresFileUpload(file, e, setEnglishScores, setLoading, setErrorMessage)}
          processSelectedFile={() => handleEnglishScoresFileProcess(englishScores, setSuccessMessage, setErrorMessage, setLogs)}
          setErrorMessage={setErrorMessage}
        />

        {/* {
          uploadMethod === 'csv' &&
            <CSVUploadSection
              handleFileLoad={(file, e) => handleEnglishScoresFileUpload(file, e, setEnglishScores, setLoading, setErrorMessage)}
              processSelectedFile={() => handleEnglishScoresFileProcess(englishScores, setSuccessMessage, setErrorMessage, setLogs)}
              setErrorMessage={setErrorMessage}
            />
        }
        {
          uploadMethod === 'api' &&
            <APILoadSection handleAPILoad={handleAPILoad} loading={loading} />
        } */}

        <LogsList logs={logs} />
      </div>
    </SectionLayout>
  )
}

export default ExamScoreManagementView
