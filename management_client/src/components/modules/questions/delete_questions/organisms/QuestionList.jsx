import Button from '../../../../core/global/atoms/Button'
import '../../../../../assets/styles/questions/question-list.css'
import { useState } from 'react'
import ConfirmationModal from '../../../../ui/confirmation_modal/view/ConfirmationModal'
import Spinner from '../../../../core/global/atoms/Spinner'

const QuestionList = ({ questions, onDelete, onModify, loading, actionType }) => {
  const [selectedQuestionCode, setSelectedQuestionCode] = useState('')
  const [isModalOpen, setIsModalOpen] = useState(false)

  const handleDelete = (code) => {
    setSelectedQuestionCode(code)
    setIsModalOpen(true)
  }

  const handleOnModify = (item) => {
    onModify(item)
  }

  const onConfirmDelete = () => {
    onDelete(selectedQuestionCode)
    setIsModalOpen(false)
  }

  return (
    <div className='container question-list'>
      <h1>Lista de Preguntas</h1>
      <ul>
        {loading
          ? (
            <Spinner />
            )
          : questions.length
            ? (
                questions.map((question) => (
                  <li key={question.code}>
                    <span>{question.question}</span>
                    {actionType === 'delete'
                      ? (
                        <Button className='btn btn-danger' onClick={() => handleDelete(question.code)}>
                          Eliminar
                        </Button>
                        )
                      : (
                        <Button className='btn btn-primary' onClick={() => handleOnModify(question)}>
                          Modificar
                        </Button>
                        )}
                  </li>
                ))
              )
            : (
              <p>No se encontraron preguntas</p>
              )}
      </ul>
      {isModalOpen && actionType === 'delete' && (
        <ConfirmationModal
          title='Eliminar Pregunta'
          message='¿Estás seguro de que deseas eliminar esta pregunta?'
          onClose={() => setIsModalOpen(false)}
          onConfirm={onConfirmDelete}
          extraMessage='Una vez eliminada, no podrás recuperarla.'
          cancelLabel='Conservar'
          confirmLabel='Eliminar'
        />
      )}
    </div>
  )
}

export default QuestionList
