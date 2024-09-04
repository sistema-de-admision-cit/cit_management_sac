import InputField from '../../../../core/global/atoms/InputField'

const HoursSection = ({ startTime, onStartTimeChange }) => (
  <div className='hours-range'>
    <InputField
      field={{
        type: 'time',
        name: 'startTime',
        label: 'Hora Inicial',
        required: true
      }}
      className='start-time'
      value={startTime}
      handleChange={onStartTimeChange}
    />
  </div>
)

export default HoursSection
