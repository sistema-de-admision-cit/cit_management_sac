import { useEffect, useState } from 'react'
import FindQuestion from '../../base/molecules/FindQuestion'
import ModifyQuestionForm from '../organisms/ModifyQuestionForm'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import QuestionList from '../../base/organism/QuestionList.jsx'
import { getQuestionById } from '../helpers/helpers'
import useMessages from '../../../../core/global/hooks/useMessages'
import '../../../../../assets/styles/global/view.css'

const mapIncomingQuestionOptionsData = (questionOptions) => {
  if (!Array.isArray(questionOptions)) return { questionOptionsText: [], correctOption: '' }
  const questionOptionsText = questionOptions.map(q => q.option)
  const correctIndex = questionOptions.findIndex(q => q.isCorrect)
  return {
    questionOptionsText,
    correctOption: correctIndex !== -1 ? correctIndex : ''
  }
}

const ModifyQuestionView = () => {
  const [questionData, setQuestionData] = useState(null)
  const [incomingData, setIncomingData] = useState(null)
  const [searchQuery, setSearchQuery] = useState('')
  const [searchExamType, setSearchExamType] = useState('both')
  const { setErrorMessage, renderMessages } = useMessages()

  const handleQuestionFound = (data) => {
    getQuestionById(data.id, setIncomingData, setErrorMessage)
  }

  useEffect(() => {
    if (incomingData) {
      const transformedOptions = mapIncomingQuestionOptionsData(incomingData.questionOptions)
      setQuestionData({
        ...incomingData,
        ...transformedOptions
      })
    }
  }, [incomingData])

  return (
    <SectionLayout title='Modificar pregunta'>
      <div className='modify-question-container'>
        <div className='search-section'>
          <FindQuestion
            query={searchQuery}
            setQuery={setSearchQuery}
            searchExamType={searchExamType}
            setSearchExamType={setSearchExamType}
          />
        </div>
        {!questionData && (
          <div className='list-section'>
            <QuestionList
              actionType='modify'
              onModify={handleQuestionFound}
              searchQuery={searchQuery}
              searchExamType={searchExamType}
            />
          </div>
        )}
        {questionData && (
          <div className='form-section'>
            <ModifyQuestionForm
              questionData={questionData}
              setQuestionData={setQuestionData}
            />
          </div>
        )}
      </div>
      {renderMessages()}
    </SectionLayout>
  )
}

export default ModifyQuestionView
