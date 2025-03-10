import { useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import useMessages from '../../../../core/global/hooks/useMessages'
import FindQuestion from '../../base/molecules/FindQuestion'
import QuestionList from '../../base/organism/QuestionList.jsx'
import '../../../../../assets/styles/global/view.css'
import { handleDeleteFromList } from '../helpers/formHandlers.js'
import { useAuth } from '../../../../../router/AuthProvider.jsx'
import { mapExamTypeFilter } from '../../base/helpers/questionFormOptions.js'

const DeleteQuestionView = () => {
  const { user } = useAuth()
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  const [searchQuery, setSearchQuery] = useState('')
  const [searchExamType, setSearchExamType] = useState(mapExamTypeFilter(user.role))

  const handleDelete = (code) => {
    handleDeleteFromList(code, setErrorMessage, setSuccessMessage)
  }

  return (
    <SectionLayout title='Eliminar pregunta'>
      <div className='delete-question-container'>
        <div className='search-section'>
          <FindQuestion
            query={searchQuery}
            setQuery={setSearchQuery}
            searchExamType={searchExamType}
            setSearchExamType={setSearchExamType}
            userRole={user.role}
          />
        </div>
        <div className='list-section'>
          <QuestionList
            actionType='delete'
            onDelete={handleDelete}
            searchQuery={searchQuery}
            searchExamType={searchExamType}
            userRole={user.role}
          />
        </div>
      </div>
      {renderMessages()}
    </SectionLayout>
  )
}

export default DeleteQuestionView
