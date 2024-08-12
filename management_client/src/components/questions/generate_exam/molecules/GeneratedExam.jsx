// GeneratedExam.jsx
import Button from '../../../global/atoms/Button'

const GeneratedExam = ({ exam, examDate, setExam, setExamDate }) => (
  <div className='generated-exam-container'>
    <h3>Examen Generado:</h3>
    <p><strong>Fecha del Examen:</strong> {`${examDate.toLocaleDateString()}`}</p>

    {exam.map((question, index) => (
      <div key={index}>
        <p>{index + 1}. {question.questionText}</p>
      </div>
    ))}

    <div className='generated-exam-buttons'>
      <Button className='btn btn-primary' onClick={() => console.log('Examen guardado', examDate)}>
        Guardar Examen
      </Button>
      <Button className='btn btn-secondary' onClick={() => { setExam([]); setExamDate(new Date()); console.log('Examen descartado') }}>
        Descartar Examen
      </Button>
    </div>
  </div>
)

export default GeneratedExam
