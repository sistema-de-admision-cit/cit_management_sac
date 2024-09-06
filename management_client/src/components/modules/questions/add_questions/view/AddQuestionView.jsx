import AddQuestionForm from '../organisms/AddQuestionForm'
import '../../../../../assets/styles/global/view.css'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'

// <title> es el título que aparece en la pestaña del navegador
const AddQuestionView = () => {
  return (
    <SectionLayout title='Ingresar Pregunta'>
      <AddQuestionForm />
    </SectionLayout>
  )
}

export default AddQuestionView
