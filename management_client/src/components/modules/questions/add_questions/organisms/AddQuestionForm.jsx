import QuestionForm from '../../base/organism/QuestionForm'
import { handleCreateQuestionSubmit } from '../../helpers/formHandlers'

const AddQuestionForm = () => {
  const initialData = {
    questionType: '',
    questionText: '',
    questionGrade: '',
    imageUrl: '',
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
