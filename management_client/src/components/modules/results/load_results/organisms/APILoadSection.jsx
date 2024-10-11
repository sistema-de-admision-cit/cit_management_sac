import Button from '../../../../core/global/atoms/Button'

const APILoadSection = ({ handleAPILoad, loading }) => (
  <div className='api-load-section'>
    <h2>Cargar Notas desde API</h2>
    <Button className='btn btn-primary' onClick={handleAPILoad} disabled={loading}>
      Cargar Notas
    </Button>
  </div>
)
export default APILoadSection
