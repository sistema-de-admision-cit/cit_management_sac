import Form from '../molecules/Form'

import '../../../../assets/styles/auth/right-column.css'
import '../../../../assets/styles/auth/links.css'

const LoginContent = ({ fields, formData, handleChange, onSubmit }) => {
  return (
    <div className='right-column'>
      <div className='form'>
        <h2>Iniciar Sesión</h2>
        <Form
          fields={fields}
          formData={formData}
          handleChange={handleChange}
          onSubmit={onSubmit}
          sectionName='Iniciar Sesión'
        />
        <div className='copyright'>
          <p>Copyright &copy; 2024. Todos los derechos reservados.</p>
        </div>
      </div>
    </div>
  )
}

export default LoginContent
