import Button from '../../../../core/global/atoms/Button'
import '../../../../../assets/styles/questions/question-list.css'
import { useState } from 'react'
import ConfirmationModal from '../../../../ui/confirmation_modal/view/ConfirmationModal'

const QuestionList = ({ questions, onDelete }) => {
  const [selectedQuestionCode, setSelectedQuestionCode] = useState('')
  const [isModalOpen, setIsModalOpen] = useState(false)

  const handleDelete = (code) => {
    setSelectedQuestionCode(code)
    setIsModalOpen(true)
  }

  const onConfirm = () => {
    onDelete(selectedQuestionCode)
    setIsModalOpen(false)
  }

  return (
    <div className='container question-list'>
      <h1>Lista de Preguntas</h1>
      <ul>
        {questions.map((question) => (
          <li key={question.code}>
            <span>{question.question}</span>
            <Button className='btn btn-danger' onClick={() => handleDelete(question.code)}>
              Eliminar
            </Button>
          </li>
        ))}
      </ul>
      {isModalOpen && (
        <ConfirmationModal
          title='Eliminar Pregunta'
          message='¿Estás seguro de que deseas eliminar esta pregunta?'
          onClose={() => setIsModalOpen(false)}
          onConfirm={onConfirm}
          extraMessage='Una vez eliminada, no podrás recuperarla.'
          cancelLabel='Conservar'
          confirmLabel='Eliminar'
        />
      )}
    </div>
  )
}

export default QuestionList
