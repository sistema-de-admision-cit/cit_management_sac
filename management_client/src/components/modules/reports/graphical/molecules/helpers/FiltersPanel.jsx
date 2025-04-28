import React from 'react'
import InputField from '../../../../../core/global/atoms/InputField'

const FiltersPanel = ({
  startDate,
  endDate,
  onStartDateChange,
  onEndDateChange,
  sector,
  onSectorChange,
  grade,
  onGradeChange,
  sectorOptions,
  gradeOptions
}) => (
  <div className='filters flex space-x-4'>
    <InputField
      field={{ name: 'startDate', type: 'date' }}
      value={startDate}
      handleChange={onStartDateChange}
      className='w-full'
      showLabel={false}
    />
    <InputField
      field={{ name: 'endDate', type: 'date' }}
      value={endDate}
      handleChange={onEndDateChange}
      className='w-full'
      showLabel={false}
    />
    <InputField
      field={{ name: 'sector', type: 'dropdown', options: sectorOptions }}
      value={sector}
      handleChange={onSectorChange}
      className='w-full'
      showLabel={false}
    />
    <InputField
      field={{ name: 'grade', type: 'dropdown', options: gradeOptions }}
      value={grade}
      handleChange={onGradeChange}
      className='w-full'
      showLabel={false}
    />
  </div>
)

export default React.memo(FiltersPanel)
