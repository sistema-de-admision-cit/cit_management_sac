import { useState, useEffect } from 'react'
import Button from '../../../../core/global/atoms/Button.jsx'
import Spinner from '../../../../core/global/atoms/Spinner.jsx'
import ConfirmationModal from '../../../../ui/confirmation_modal/view/ConfirmationModal.jsx'
import Pagination from '../../../../core/global/molecules/Pagination.jsx'
import '../../../../../assets/styles/questions/question-list.css'
import { handleGetAllQuestions } from '../../delete_questions/helpers/formHandlers'

const mapExamType = (examType) => {
  const examTypeMap = {
    both: null,
    ACA: 'ACA',
    DAI: 'DAI'
  }

  return examTypeMap[examType]
}

const QuestionList = ({ onDelete, onModify, actionType, searchQuery = '', searchExamType = 'both' }) => {
  const [questions, setQuestions] = useState([])
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
  const [loading, setLoading] = useState(false)
  const [selectedQuestionCode, setSelectedQuestionCode] = useState('')
  const [isModalOpen, setIsModalOpen] = useState(false)
  const pageSize = 10

  const fetchQuestions = (page = 0) => {
    handleGetAllQuestions(page, pageSize, searchQuery, mapExamType(searchExamType), setQuestions, setTotalPages, setLoading)
  }

  useEffect(() => {
    setCurrentPage(0)
    fetchQuestions(0)
  }, [searchQuery, searchExamType])

  // Cada vez que se cambie la página, se hace la consulta
  useEffect(() => {
    fetchQuestions(currentPage)
  }, [currentPage])

  const handleDelete = (code) => {
    setSelectedQuestionCode(code)
    setIsModalOpen(true)
  }

  const onConfirmDelete = () => {
    onDelete(selectedQuestionCode)
    setIsModalOpen(false)
  }

  const onPageChange = (page) => {
    setCurrentPage(page)
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
                  <li key={question.id}>
                    <span>{question.questionText}</span>
                    {actionType === 'delete'
                      ? (
                        <Button className='btn btn-danger' onClick={() => handleDelete(question.id)}>
                          Eliminar
                        </Button>
                        )
                      : (
                        <Button className='btn btn-primary' onClick={() => onModify(question)}>
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
      {totalPages > 1 && (
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={onPageChange}
        />
      )}
      {isModalOpen && actionType === 'delete' && (
        <ConfirmationModal
          title='Eliminar Pregunta'
          message='¿Estás seguro de que deseas eliminar esta pregunta?'
          onClose={() => setIsModalOpen(false)}
          onConfirm={onConfirmDelete}
          extraMessage='Una vez eliminada, deberas pedirle a un administrador que la restaure.'
          cancelLabel='Conservar'
          confirmLabel='Eliminar'
        />
      )}
    </div>
  )
}

export default QuestionList
