import QuestionForm from '../../base/organism/QuestionForm'
import { handleCreateQuestionSubmit } from '../../helpers/formHandlers'

const AddQuestionForm = () => {
  const initialData = {
    questionType: '',
    questionText: '',
    imageUrl: '',
    questionGrade: ['', '', '', ''],
    selectionType: '',
    deleted: false,
    questionOptionsText: ['', '', '', ''],
    correctOption: ''
  }

  return (
    <QuestionForm
      title='Ingresar Pregunta'
      initialData={initialData}
      onSubmit={handleCreateQuestionSubmit}
      submitButtonText='Guardar Pregunta'
    />
  )
}

export default AddQuestionForm
