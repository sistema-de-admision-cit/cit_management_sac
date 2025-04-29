import React from 'react';
import { TextField } from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import es from 'date-fns/locale/es';

const today = new Date(Date.now())

const MuiDatePicker = ({ 
  name, 
  label, 
  value, 
  onChange, 
  required = false, 
  showLabel = true,
  disabled = false,
  minDate,
  maxDate
}) => {
  const handleDateChange = (newValue) => {
    onChange({
      target: {
        name,
        value: newValue
      }
    });
  };

  return (
    <LocalizationProvider dateAdapter={AdapterDateFns} adapterLocale={es}>
      <DatePicker
        label={showLabel ? label : ''}
        value={value}
        onChange={handleDateChange}
        disabled={disabled}
        minDate={minDate}
        maxDate={today}
        renderInput={(params) => (
          <TextField
            {...params}
            fullWidth
            required={required}
            size="small"
            variant="outlined"
            name={name}
            error={params.error}
            helperText={params.helperText}
          />
        )}
      />
    </LocalizationProvider>
  );
};

export default MuiDatePicker;
