// DeleteQuestionView.jsx
import { useState, useEffect } from 'react'
import QuestionList from '../../base/organism/QuestionList.jsx'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import useMessages from '../../../../core/global/hooks/useMessages'
import { handleGetAllQuestions } from '../helpers/formHandlers'
import Pagination from '../../../../core/global/molecules/Pagination.jsx'

const DeleteQuestionView = () => {
  const [questions, setQuestions] = useState([])
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  const [loading, setLoading] = useState(false)

  const fetchQuestions = (page = 0, size = 10) => {
    handleGetAllQuestions(page, size, setQuestions, setTotalPages, setLoading, setErrorMessage)
  }

  useEffect(() => {
    fetchQuestions(currentPage)
  }, [currentPage])

  const handleDelete = (code) => {
    // handleDeleteFromList(code, questions, setQuestions, setErrorMessage, setSuccessMessage)
  }

  const onPageChange = (page) => {
    setCurrentPage(page)
  }

  return (
    <SectionLayout title='Eliminar pregunta'>
      <QuestionList
        questions={questions}
        onDelete={handleDelete}
        loading={loading}
        actionType='delete'
      />
      {totalPages > 1 && (
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={onPageChange}
        />
      )}
      {renderMessages()}
    </SectionLayout>
  )
}

export default DeleteQuestionView
