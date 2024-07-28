import React, { useState } from 'react'
import FindQuestion from '../molecules/FindQuestion'
import ModifyQuestionForm from '../organisms/ModifyQuestionForm'
import '../../../../assets/styles/questions/view.css'

const ModifyQuestionView = () => {
  const [questionData, setQuestionData] = useState(null)

  return (
    <div className='section-container'>
      <title>Modificar Pregunta</title>
      {!questionData
        ? (
          <FindQuestion onQuestionFound={(data) => setQuestionData(data)} />
          )
        : (
          <ModifyQuestionForm questionData={questionData} setQuestionData={setQuestionData} />
          )}
    </div>
  )
}

export default ModifyQuestionView
