import React from 'react';
import { TextField, Checkbox, FormControlLabel } from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';

const DateApplicationSection = ({
  allYear,
  startDate,
  endDate,
  onAllYearChange,
  onStartDateChange,
  onEndDateChange
}) => {
  return (
    <div className="date-application">
      <h2>Fechas de Aplicación <span className="required">*</span></h2>
      
      <FormControlLabel
        control={
          <Checkbox
            checked={allYear}
            onChange={onAllYearChange}
            color="primary"
          />
        }
        label="Todo el año"
      />
      
      {!allYear && (
        <LocalizationProvider dateAdapter={AdapterDateFns}>
          <div className="date-range" style={{ display: 'flex', gap: '1rem', marginTop: '1rem' }}>
            <DatePicker
              label="Fecha de inicio"
              value={startDate}
              onChange={onStartDateChange}
              renderInput={(params) => (
                <TextField 
                  {...params} 
                  fullWidth 
                  size="small" 
                />
              )}
            />
            
            <DatePicker
              label="Fecha de fin"
              value={endDate}
              onChange={onEndDateChange}
              renderInput={(params) => (
                <TextField 
                  {...params} 
                  fullWidth 
                  size="small" 
                />
              )}
              minDate={startDate}
            />
          </div>
        </LocalizationProvider>
      )}
    </div>
  );
};

export default DateApplicationSection;