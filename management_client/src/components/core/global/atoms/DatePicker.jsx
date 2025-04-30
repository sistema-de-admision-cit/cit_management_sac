import React from 'react';
import { TextField } from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import es from 'date-fns/locale/es';

const today = new Date(Date.now());

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
        maxDate={maxDate || today}
        slots={{
          textField: (params) => (
            <TextField
              {...params}
              fullWidth
              required={required}
              size="small"
              variant="outlined"
              name={name}
              sx={{
                '& .MuiOutlinedInput-root': {
                  '&:hover fieldset': {
                    borderColor: '#2ba98e',
                  },
                  '&.Mui-focused fieldset': {
                    borderColor: '#2ba98e',
                  },
                },
                '& .MuiInputLabel-root.Mui-focused': {
                  color: '#2ba98e',
                },
              }}
            />
          ),
        }}
        slotProps={{
          layout: {
            sx: {
              '& .MuiPickersDay-root': {
                '&.Mui-selected': {
                  backgroundColor: '#2ba98e',
                  '&:hover': {
                    backgroundColor: '#2ba98e',
                  },
                },
                '&.MuiPickersDay-today': {
                  borderColor: '#2ba98e',
                },
              },
              '& .MuiPickersCalendarHeader-label': {
                color: '#2ba98e',
              },
              '& .MuiIconButton-root': {
                color: '#2ba98e',
                '&:hover': {
                  backgroundColor: 'rgba(43, 169, 142, 0.1)',
                },
              },
            },
          },
          actionBar: {
            actions: ['today', 'cancel', 'accept'],
            sx: {
              '& .MuiButton-root': {
                '&:hover': {
                  backgroundColor: 'rgba(43, 169, 142, 0.1)',
                },
                '&.MuiButton-textPrimary': {
                  color: '#2ba98e',
                },
              },
            },
          },
        }}
      />
    </LocalizationProvider>
  );
};

export default MuiDatePicker;