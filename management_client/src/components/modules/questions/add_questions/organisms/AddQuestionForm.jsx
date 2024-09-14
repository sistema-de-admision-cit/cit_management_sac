import QuestionForm from '../../base/organism/QuestionForm'
import { handleCreateQuestionSubmit } from '../../helpers/formHandlers'

const AddQuestionForm = () => {
  const initialData = {
    examType: '',
    question: '',
    images: [],
    options: ['', '', '', ''],
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
