import AddQuestionForm from '../organisms/AddQuestionForm'
import '../../../../../assets/styles/questions/view.css'
import SectionLayout from '../../../../global/molecules/SectionLayout'

// <title> es el título que aparece en la pestaña del navegador
const AddQuestionView = () => {
  return (
    <SectionLayout title='Ingresar Pregunta'>
      <AddQuestionForm />
    </SectionLayout>
  )
}

export default AddQuestionView
