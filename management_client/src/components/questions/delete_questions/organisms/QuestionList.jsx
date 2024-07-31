import React from 'react'
import Button from '../../../global/atoms/Button'
import '../../../../assets/styles/questions/question-list.css'

const QuestionList = ({ questions, onDelete }) => {
  return (
    <div className='question-list'>
      <h2>Lista de Preguntas</h2>
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
