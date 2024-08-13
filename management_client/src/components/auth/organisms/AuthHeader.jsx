import '../../../assets/styles/auth/left-column.css'

const AuthHeader = ({ sectionName }) => {
  return (
    <div className='left-column'>
      <div className='hero'>
        <p className='kicker'>{sectionName === 'Iniciar Sesión' ? 'Acceso Seguro' : 'Bienvenido'}</p>
        <h1>
          {sectionName === 'Iniciar Sesión' ? 'Inicia Sesión' : 'Únete a Nosotros'}<br />
          {sectionName === 'Iniciar Sesión' ? 'para Gestionar Exámenes' : '!'}
          <span className='dot'>.</span>
        </h1>
        <p>
          {sectionName === 'Iniciar Sesión' ? 'Ingresa a tu cuenta para administrar preguntas de examen, generar reportes y más de forma rápida y segura.' : 'Regístrate y forma parte de nuestro sistema de gestión de exámenes de admisión.'}
        </p>
      </div>
    </div>
  )
}

export default AuthHeader
