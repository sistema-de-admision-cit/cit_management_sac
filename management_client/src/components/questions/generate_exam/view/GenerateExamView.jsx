// GenerateExamView.jsx
import { useState } from 'react'
import Button from '../../../global/atoms/Button'
import ExamDatePicker from '../molecules/ExamDatePicker'
import GeneratedExam from '../molecules/GeneratedExam'
import { generateExam, saveExamHandler, discardExamHandler } from '../helpers/handlers'
import '../../../../assets/styles/questions/view.css'
import '../../../../assets/styles/questions/generate-exam.css'
import PopupComponent from '../../../popups/PopupComponent'
import { getNearestAvailableDate } from '../helpers/datesHelper'

const GenerateExamView = () => {
  const [exam, setExam] = useState([])
  const [errorMessage, setErrorMessage] = useState('')
  const [successMessage, setSuccessMessage] = useState('')
  const [loading, setLoading] = useState(false)
  const [examDate, setExamDate] = useState(getNearestAvailableDate(new Date()))

  return (
    <div className='section-container'>
      <title>Generar Examen</title>
      <div className='generate-exam-container'>
        <h1>Generar Examen</h1>
        <ExamDatePicker examDate={examDate} setExamDate={setExamDate} />

        {errorMessage && (
          <PopupComponent message={errorMessage} onClose={() => setErrorMessage('')} type='error' />
        )}
        {successMessage && (
          <PopupComponent message={successMessage} onClose={() => setSuccessMessage('')} type='confirmation' />
        )}

        <Button onClick={() => generateExam(setLoading, setErrorMessage, setExam, examDate)} className='btn btn-primary'>
          Generar Examen
        </Button>

        {loading && <p>Cargando...</p>}

        {exam.length > 0 && (
          <GeneratedExam
            exam={exam}
            examDate={examDate}
            setExam={setExam}
            setExamDate={setExamDate}
            setSuccessMessage={setSuccessMessage}
            setErrorMessage={setErrorMessage}
            setLoading={setLoading}
            saveExamHandler={saveExamHandler}
            discardExamHandler={discardExamHandler}
          />
        )}
      </div>
    </div>
  )
}

export default GenerateExamView
