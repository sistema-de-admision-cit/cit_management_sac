import QuestionForm from '../../base/organism/QuestionForm'
import { handleModifySubmit } from '../../helpers/formHandlers'

const ModifyQuestionForm = ({ questionData, setQuestionData }) => {
  return (
    <QuestionForm
      title='Modificar Pregunta'
      initialData={questionData}
      onSubmit={(e, data, setErrorMessage, setSuccessMessage, setIsLoading, setQuestionData) =>
        handleModifySubmit(e, data, setErrorMessage, setSuccessMessage, setIsLoading, setQuestionData)}
      submitButtonText='Guardar Cambios'
      isModify
      searchAgain={() => setQuestionData(null)}
    />
  )
}

export default ModifyQuestionForm
