import React from 'react';
import { TextField, Box } from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';

const DateRangePicker = ({ startDate, endDate, onStartDateChange, onEndDateChange }) => {
  return (
    <LocalizationProvider dateAdapter={AdapterDateFns}>
      <Box sx={{ 
        display: 'flex', 
        gap: 2,
        '& .MuiTextField-root': {
          backgroundColor: 'white',
          borderRadius: 1
        }
      }}>
        <DatePicker
          label="Fecha de Inicio"
          value={startDate}
          onChange={onStartDateChange}
          renderInput={(params) => <TextField {...params} fullWidth />}
        />
        <DatePicker
          label="Fecha de Fin"
          value={endDate}
          onChange={onEndDateChange}
          renderInput={(params) => <TextField {...params} fullWidth />}
          minDate={startDate}
        />
      </Box>
    </LocalizationProvider>
  );
};

export default DateRangePicker;