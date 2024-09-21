import DateRangePicker from '../molecules/DateRangePicker'
import InputField from '../../../../core/global/atoms/InputField'

const DateApplicationSection = ({ allYear, startDate, endDate, onAllYearChange, onStartDateChange, onEndDateChange }) => (
  <div className='application-dates'>
    <h2>Fechas de Aplicación <span className='required'>*</span></h2>
    <InputField
      field={{ type: 'checkbox', name: 'allYear', label: 'Todo el año' }}
      value={allYear}
      handleChange={onAllYearChange}
    />
    {!allYear && (
      <DateRangePicker
        startDate={startDate}
        endDate={endDate}
        onStartDateChange={onStartDateChange}
        onEndDateChange={onEndDateChange}
      />
    )}
  </div>
)

export default DateApplicationSection
