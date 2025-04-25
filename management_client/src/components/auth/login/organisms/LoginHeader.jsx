import '../../../../assets/styles/auth/left-column.css'

const LoginHeader = () => {
  return (
    <div className='left-column'>
      <div className='hero'>
        <p className='kicker'>Acceso Seguro</p>
        <h1>
          Inicia Sesión<br />
          para el Sistema Admisión CIT
          <span className='dot'>.</span>
        </h1>
        <p>
          Ingresa a tu cuenta para administrar las inscripciones, gestionar exámenes, generar reportes y más de forma rápida y segura.
        </p>
      </div>
    </div>
  )
}

export default LoginHeader
