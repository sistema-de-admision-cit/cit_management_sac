import InputField from '../../../../core/global/atoms/InputField'

const HoursSection = ({ startTime, endTime, onStartTimeChange, onEndTimeChange }) => (
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
    <InputField
      field={{
        type: 'time',
        name: 'endTime',
        label: 'Hora Final',
        required: true
      }}
      className='end-time'
      value={endTime}
      handleChange={onEndTimeChange}
    />
  </div>
)

export default HoursSection
