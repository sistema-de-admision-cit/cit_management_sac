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
  <div className='filters-panel'>
    <div className='filter-group'>
      <label htmlFor='startDate'>Start Date</label>
      <InputField
        field={{ name: 'startDate', type: 'date' }}
        value={startDate}
        handleChange={onStartDateChange}
      />
    </div>

    <div className='filter-group'>
      <label htmlFor='endDate'>End Date</label>
      <InputField
        field={{ name: 'endDate', type: 'date' }}
        value={endDate}
        handleChange={onEndDateChange}
      />
    </div>

    <div className='filter-group'>
      <label htmlFor='sector'>Sector</label>
      <InputField
        field={{ name: 'sector', type: 'dropdown', options: sectorOptions }}
        value={sector}
        handleChange={onSectorChange}
      />
    </div>

    <div className='filter-group'>
      <label htmlFor='grade'>Grade</label>
      <InputField
        field={{ name: 'grade', type: 'dropdown', options: gradeOptions }}
        value={grade}
        handleChange={onGradeChange}
      />
    </div>
  </div>
)

export default React.memo(FiltersPanel)
