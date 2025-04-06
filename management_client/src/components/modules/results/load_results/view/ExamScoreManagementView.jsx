import { useState } from 'react'
import useMessages from '../../../../core/global/hooks/useMessages'
import Spinner from '../../../../core/global/atoms/Spinner'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import { handleEnglishScoresFileUpload, handleEnglishScoresFileProcess } from '../helpers/handlers'
import LogsList from '../molecules/LogsList'
import CSVUploadSection from '../organisms/CSVUploadSection'
import '../../../../../assets/styles/results/load_results/exam-score-management-view.css'
import UploadGradeOptions from '../molecules/UploadGradeOptions'
import { UPLOAD_TYPES, validateScore } from '../helpers/helpers'
import ManualGradeSection from '../organisms/ManualGradeSection'
import ManualNotesPreview from '../organisms/ManualNotesPreview'

const IS_FILE_PROCESSING = false
const IS_MANUAL_PROCESSING = true

const ExamScoreManagementView = () => {
  const [loading, setLoading] = useState(false)
  const [englishScores, setEnglishScores] = useState([])
  const [logs, setLogs] = useState([])
  const [uploadType, setUploadType] = useState('file')
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  const handleAddManualNote = (note) => {
    const isValid = validateScore(note)

    if (!isValid) {
      console.log('valid score', note)
      console.trace()
      setErrorMessage('Nota inválida. Por favor verifica los datos.')
      return !isValid
    }

    setEnglishScores((prevScores) => [...prevScores, note])
    console.log(englishScores)
    setSuccessMessage('Nota añadida a la lista de notas manuales.')
    return isValid
  }

  const handleDeleteScore = (indexToRemove) => {
    setEnglishScores((prevScores) => prevScores.filter((_, index) => index !== indexToRemove))
  }

  return (
    <SectionLayout title='Cargar Notas de Ingles'>
      <div className='container'>
        <h1>Cargar Notas de Ingles</h1>
        <p className='description'>Cargar notas de forma manual o por medio de un archivo exportado desde TrackTest</p>
        {loading && <Spinner />}
        {renderMessages()}

        <div className='question-form upload-type-selection'>
          <UploadGradeOptions
            value={uploadType}
            handleChange={(e) => setUploadType(e.target.value)}
            options={UPLOAD_TYPES}
          />
        </div>

        {uploadType === 'file'
          ? (
            <CSVUploadSection
              handleFileLoad={(file, e) => handleEnglishScoresFileUpload(file, e, setEnglishScores, setLoading, setErrorMessage)}
              processSelectedFile={() => handleEnglishScoresFileProcess(englishScores, setSuccessMessage, setErrorMessage, setLogs, IS_FILE_PROCESSING)}
              setErrorMessage={setErrorMessage}
            />
            )
          : (
            <ManualGradeSection
              handleAddScore={handleAddManualNote}
              handleProcessScore={() => handleEnglishScoresFileProcess(englishScores, setSuccessMessage, setErrorMessage, setLogs, IS_MANUAL_PROCESSING)}
              handleDeleteScore={handleDeleteScore}
              scores={englishScores}
            />
            )}

        <ManualNotesPreview notes={englishScores} onDelete={handleDeleteScore} />

        <LogsList logs={logs} />
      </div>
    </SectionLayout>
  )
}

export default ExamScoreManagementView
