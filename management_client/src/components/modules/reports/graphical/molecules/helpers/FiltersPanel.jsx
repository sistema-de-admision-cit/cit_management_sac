import React from 'react'
import InputField from '../../../../../core/global/atoms/InputField'

/**
 * FiltersPanel component provides a UI for selecting date ranges, sector, and grade filters.
 *
 * @component
 * @param {Object} props
 * @param {string} props.startDate - Selected start date (YYYY-MM-DD).
 * @param {string} props.endDate - Selected end date (YYYY-MM-DD).
 * @param {(value: string) => void} props.onStartDateChange - Callback to update the start date.
 * @param {(value: string) => void} props.onEndDateChange - Callback to update the end date.
 * @param {string} props.sector - Selected sector value.
 * @param {(value: string) => void} props.onSectorChange - Callback to update the sector.
 * @param {string} props.grade - Selected grade value.
 * @param {(value: string) => void} props.onGradeChange - Callback to update the grade.
 * @param {{ label: string, value: string }[]} props.sectorOptions - Available sector options for the dropdown.
 * @param {{ label: string, value: string }[]} props.gradeOptions - Available grade options for the dropdown.
 * @returns {JSX.Element}
 */
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
