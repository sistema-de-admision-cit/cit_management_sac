import Button from '../../../../core/global/atoms/Button'
import '../../../../../assets/styles/questions/question-list.css'

const QuestionList = ({ questions, onDelete }) => {
  return (
    <div className='container question-list'>
      <h1>Lista de Preguntas</h1>
      <ul>
        {questions.map((question) => (
          <li key={question.code}>
            <span>{question.question}</span>
            <Button className='btn btn-danger' onClick={() => onDelete(question.code)}>
              Eliminar
            </Button>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default QuestionList
