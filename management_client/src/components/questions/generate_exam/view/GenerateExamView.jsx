import '../../../../assets/styles/questions/view.css'
import '../../../../assets/styles/questions/generate-exam.css'
import Button from '../../../global/atoms/Button'
import questionsExample from '../helpers/dummyData'
import { useState } from 'react'
import InputField from '../../../global/atoms/InputField'

// simular el fetch de preguntas
// TODO: implementar fetch de preguntas
const fetchQuestions = () => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(questionsExample)
    }, 1000)
  })
}

const GenerateExamView = () => {
  const [exam, setExam] = useState([])
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const generateExam = async () => {
    try {
      setLoading(true)
      const allQuestions = await fetchQuestions()
      setLoading(false)

      if (allQuestions.length < 10) {
        setError('Preguntas insuficientes en el banco de preguntas.')
        return
      }

      setError('')
      const shuffledQuestions = allQuestions.sort(() => Math.random() - 0.5)
      const selectedQuestions = shuffledQuestions.slice(0, 10)

      setExam(selectedQuestions)
    } catch (err) {
      setError('Error al generar el examen.')
    }
  }

  return (
    <div className='section-container'>
      <title>Generar Examen</title>
      <div className='generate-exam-container'>
        <h1>Generar Examen</h1>

        <Button onClick={generateExam} className='btn btn-primary'>
          Generar Examen
        </Button>

        {error && <p>{error}</p>}

        {loading && <p>Cargando...</p>}

        {exam.length > 0 && (
          <div className='generated-exam-container'>
            <h3>Examen Generado:</h3>
            {exam.map((question, index) => (
              <div key={index}>
                <p>{index + 1}. {question.questionText}</p>
              </div>
            ))}

            <div className='generated-exam-buttons'>
              <Button className='btn btn-primary' onClick={() => console.log('Examen guardado')}>
                Guardar Examen
              </Button>
              <Button className='btn btn-secondary' onClick={() => { setExam([]); console.log('Examen descartado') }}>
                Descartar Examen
              </Button>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}

export default GenerateExamView
