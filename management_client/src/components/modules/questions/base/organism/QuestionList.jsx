import { useState, useEffect } from 'react'
import Button from '../../../../core/global/atoms/Button.jsx'
import Spinner from '../../../../core/global/atoms/Spinner.jsx'
import ConfirmationModal from '../../../../ui/confirmation_modal/view/ConfirmationModal.jsx'
import Pagination from '../../../../core/global/molecules/Pagination.jsx'
import '../../../../../assets/styles/questions/question-list.css'
import { handleGetAllQuestions } from '../../delete_questions/helpers/formHandlers'

const BOTH_EXAM_TYPE = null
const ONLY_ACADEMIC_EXAM_TYPE = 'ACA'
const ONLY_EMOTIONAL_INTELLIGENCE_EXAM_TYPE = 'DAI'

const mapExamType = (examType) => {
  return examType === 'both' ? null : examType
}

const mapExamTypeBasedOnUserRole = (userRole) => {
  const examTypeMap = {
    SYS: BOTH_EXAM_TYPE,
    ADMIN: BOTH_EXAM_TYPE,
    PSYCHOLOGIST: ONLY_EMOTIONAL_INTELLIGENCE_EXAM_TYPE,
    TEACHER: ONLY_ACADEMIC_EXAM_TYPE
  }

  return examTypeMap[userRole]
}

const QuestionList = ({ onDelete, onModify, actionType, searchQuery = '', searchExamType, userRole }) => {
  const [questions, setQuestions] = useState([])
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
  const [loading, setLoading] = useState(false)
  const [selectedQuestionCode, setSelectedQuestionCode] = useState('')
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [examType, setExamType] = useState(mapExamTypeBasedOnUserRole(userRole))
  const pageSize = 10

  const updateNewExamType = (newExamType) => {
    setExamType((prevExamType) => {
      const newExamTypeMapped = mapExamType(newExamType)
      console.log(`Updating from ${prevExamType} to ${newExamTypeMapped}`)
      return newExamTypeMapped
    })
  }

  useEffect(() => {
    updateNewExamType(searchExamType)
  }, [searchExamType])

  const fetchQuestions = (page = 0) => {
    handleGetAllQuestions(page, pageSize, searchQuery, examType, setQuestions, setTotalPages, setLoading)
  }

  useEffect(() => {
    setCurrentPage(0)
    fetchQuestions(0)
    updateNewExamType(searchExamType)
  }, [searchQuery, examType])

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

    setTimeout(() => {
      fetchQuestions(currentPage)
    }, 300) // Delay to ensure backend updates before refetching
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
