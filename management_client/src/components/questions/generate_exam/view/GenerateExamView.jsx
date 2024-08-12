// GenerateExamView.jsx
import { useState } from 'react'
import Button from '../../../global/atoms/Button'
import ExamDatePicker from '../molecules/ExamDatePicker'
import GeneratedExam from '../molecules/GeneratedExam'
import { generateExam } from '../helpers/examHelper'
import '../../../../assets/styles/questions/view.css'
import '../../../../assets/styles/questions/generate-exam.css'

const GenerateExamView = () => {
  const [exam, setExam] = useState([])
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const [examDate, setExamDate] = useState(new Date())

  return (
    <div className='section-container'>
      <title>Generar Examen</title>
      <div className='generate-exam-container'>
        <h1>Generar Examen</h1>
        <ExamDatePicker examDate={examDate} setExamDate={setExamDate} />

        <Button onClick={() => generateExam(setLoading, setError, setExam)} className='btn btn-primary'>
          Generar Examen
        </Button>

        {error && <p>{error}</p>}
        {loading && <p>Cargando...</p>}

        {exam.length > 0 && (
          <GeneratedExam exam={exam} examDate={examDate} setExam={setExam} setExamDate={setExamDate} />
        )}
      </div>
    </div>
  )
}

export default GenerateExamView
