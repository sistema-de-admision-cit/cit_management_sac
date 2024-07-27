import React from 'react'
import AddQuestionForm from '../organisms/AddQuestionForm'
import '../../../../assets/styles/questions/view.css'

// <title> es el título que aparece en la pestaña del navegador
const AddQuestionView = () => {
  return (
    <div className='section-container'>
      <title>Agregar Pregunta</title>
      <AddQuestionForm />
    </div>
  )
}

export default AddQuestionView
