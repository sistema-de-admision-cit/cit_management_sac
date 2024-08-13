// GenerateExamView.jsx
import { useState } from 'react'
import Button from '../../../global/atoms/Button'
import ExamDatePicker from '../molecules/ExamDatePicker'
import GeneratedExam from '../molecules/GeneratedExam'
import { generateExam, saveExamHandler, discardExamHandler } from '../helpers/handlers'
import '../../../../assets/styles/questions/view.css'
import { getNearestAvailableDate } from '../helpers/datesHelper'
import SectionLayout from '../../../global/molecules/SectionLayout'
import useMessages from '../../../global/hooks/useMessages'

const GenerateExamView = () => {
  const [exam, setExam] = useState([])
  const [isLoading, setIsLoading] = useState(false)
  const [examDate, setExamDate] = useState(getNearestAvailableDate(new Date()))

  // Usa el hook useMessages
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  const handleGenerateExam = () => {
    generateExam(setIsLoading, setErrorMessage, setExam, examDate)
  }

  return (
    <SectionLayout title='Generar Examen'>
      <div className='container generate-exam-container'>
        <h1>Generar Examen</h1>
        <ExamDatePicker examDate={examDate} setExamDate={setExamDate} />

        <Button onClick={handleGenerateExam} className='btn btn-primary' disabled={isLoading}>
          {isLoading ? 'Generando...' : 'Generar Examen'}
        </Button>

        {exam.length > 0 && (
          <GeneratedExam
            exam={exam}
            examDate={examDate}
            setExam={setExam}
            setExamDate={setExamDate}
            setSuccessMessage={setSuccessMessage}
            setErrorMessage={setErrorMessage}
            setIsLoading={setIsLoading}
            saveExamHandler={saveExamHandler}
            discardExamHandler={discardExamHandler}
          />
        )}

        {renderMessages()} {/* Renderiza los mensajes */}
      </div>
    </SectionLayout>
  )
}

export default GenerateExamView
