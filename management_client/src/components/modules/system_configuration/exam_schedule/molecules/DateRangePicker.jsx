import React from 'react'
import DatePicker from '../../../../core/global/atoms/DatePicker'

const DateRangePicker = ({ startDate, endDate, onStartDateChange, onEndDateChange }) => (
  <div className='date-range'>
    <DatePicker
      name='startDate'
      label='Fecha Inicial'
      value={startDate}
      onChange={onStartDateChange}
      required
    />
    <DatePicker
      name='endDate'
      label='Fecha Final'
      value={endDate}
      onChange={onEndDateChange}
      required
    />
  </div>
)

export default DateRangePicker
