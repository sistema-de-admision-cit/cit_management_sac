import Button from '../../../../core/global/atoms/Button'

const GeneratedExam = ({
  exam,
  examDate,
  setExam,
  setExamDate,
  setSuccessMessage,
  setErrorMessage,
  setIsSaving,
  isSaving,
  saveExamHandler,
  discardExamHandler
}) => (
  <div className='generated-exam-container'>
    <h3>Examen Generado:</h3>
    <p><strong>Fecha del Examen:</strong> {`${examDate.toLocaleDateString()}`}</p>

    {exam.map((question, index) => (
      <div key={index}>
        <p>{index + 1}. {question.questionText}</p>
      </div>
    ))}

    <div className='generated-exam-buttons'>
      <Button className='btn btn-primary' onClick={() => saveExamHandler(exam, examDate, setSuccessMessage, setErrorMessage, setIsSaving, setExam)} disabled={isSaving}>
        {isSaving ? 'Guardando...' : 'Guardar Examen'}
      </Button>
      <Button className='btn btn-secondary' onClick={() => discardExamHandler(setExam, setExamDate)}>
        Descartar Examen
      </Button>
    </div>
  </div>
)

export default GeneratedExam
