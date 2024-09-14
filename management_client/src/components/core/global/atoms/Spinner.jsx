import '../../../../assets/styles/global/spinner.css'

const Spinner = () => {
  return (
    <div className='spinner-container'>
      <div
        className='spinner'
        role='status'
        aria-live='polite'
        aria-label='Cargando'
      >
        <span className='visually-hidden'>Cargando...</span>
      </div>
    </div>
  )
}

export default Spinner
