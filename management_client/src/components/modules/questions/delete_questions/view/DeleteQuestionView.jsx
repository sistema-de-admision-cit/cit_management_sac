import { useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import useMessages from '../../../../core/global/hooks/useMessages'
import FindQuestion from '../../base/molecules/FindQuestion'
import QuestionList from '../../base/organism/QuestionList.jsx'
import '../../../../../assets/styles/global/view.css'
import { handleDeleteFromList } from '../helpers/formHandlers.js'

const DeleteQuestionView = () => {
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  const [searchQuery, setSearchQuery] = useState('')
  const [searchExamType, setSearchExamType] = useState('both')

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
          />
        </div>
        <div className='list-section'>
          <QuestionList
            actionType='delete'
            onDelete={handleDelete}
            searchQuery={searchQuery}
            searchExamType={searchExamType}
          />
        </div>
      </div>
      {renderMessages()}
    </SectionLayout>
  )
}

export default DeleteQuestionView
