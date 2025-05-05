const ErrorMessage = ({ message = 'Error loading data' }) => (
  <div className='error-message'>
    <p>{message}</p>
  </div>
)

export default ErrorMessage
