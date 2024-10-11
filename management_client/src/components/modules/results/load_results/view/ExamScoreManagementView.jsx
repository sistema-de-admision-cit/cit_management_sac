import { useState } from 'react'
import useMessages from '../../../../core/global/hooks/useMessages'
import Spinner from '../../../../core/global/atoms/Spinner'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import { handleEnglishScoresFileUpload, handleEnglishScoresFileProcess } from '../helpers/handlers'
import LogsList from '../molecules/LogsList'
import CSVUploadSection from '../organisms/CSVUploadSection'
import '../../../../../assets/styles/results/load_results/exam-score-management-view.css'

const ExamScoreManagementView = () => {
  const [loading, setLoading] = useState(false)
  const [englishScores, setEnglishScores] = useState({})
  const [logs, setLogs] = useState([])
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  return (
    <SectionLayout title='Cargar Notas de Ingles'>
      <div className='container'>
        <h1>Cargar Notas de Ingles</h1>
        {loading && <Spinner />}
        {renderMessages()}

        <CSVUploadSection
          handleFileLoad={(file, e) => handleEnglishScoresFileUpload(file, e, setEnglishScores, setLoading, setErrorMessage)}
          processSelectedFile={() => handleEnglishScoresFileProcess(englishScores, setSuccessMessage, setErrorMessage, setLogs)}
          setErrorMessage={setErrorMessage}
        />

        <LogsList logs={logs} />
      </div>
    </SectionLayout>
  )
}

export default ExamScoreManagementView
