import axios from '../../../../../config/axiosConfig';
import { format, parseISO } from 'date-fns';

const mapDays = (days) => {
  const daysMap = {
    Lunes: 'M',
    Martes: 'K',
    Miércoles: 'W',
    Jueves: 'T',
    Viernes: 'F',
    Sábado: 'S'
  };

  return days.map(day => daysMap[day]);
};

const saveExamScheduleUrl = import.meta.env.VITE_SAVE_EXAM_SCHEDULE_ENDPOINT;

const possibleErrors = {
  startDate: 'La fecha inicial no puede ser mayor a la fecha final',
  endDate: 'La fecha final no puede ser menor a la fecha inicial',
  applicationDays: 'Debes seleccionar al menos un día de aplicación',
  startTime: 'Debes seleccionar una hora de aplicación',
  invalidDate: 'La fecha proporcionada no es válida',
  default: 'Ocurrió un error al guardar la configuración'
};

export const handleSubmit = async (formValues, setLoading, setErrorMessage, setSuccessMessage) => {
  try {
    setLoading(true);
    setErrorMessage(null);

    const getFirstAndLastDayOfYear = () => {
      const year = new Date().getFullYear();
      return {
        firstDay: `${year}-01-01`,
        lastDay: `${year}-12-31`
      };
    };

    const { firstDay, lastDay } = getFirstAndLastDayOfYear();

    // Validar fechas si no es "todo el año"
    if (!formValues.allYear) {
      if (!(formValues.startDate instanceof Date) || isNaN(formValues.startDate.getTime())) {
        throw new Error(possibleErrors.invalidDate);
      }
      if (!(formValues.endDate instanceof Date) || isNaN(formValues.endDate.getTime())) {
        throw new Error(possibleErrors.invalidDate);
      }
    }

    const startDate = formValues.allYear ? firstDay : format(formValues.startDate, 'yyyy-MM-dd');
    const endDate = formValues.allYear ? lastDay : format(formValues.endDate, 'yyyy-MM-dd');

    if (!isFormValid(formValues)) {
      throw new Error(possibleErrors.default);
    }

    const sendingData = {
      allYear: formValues.allYear,
      startDate,
      endDate,
      examDays: mapDays(formValues.applicationDays).map(day => ({
        examDay: day,
        startTime: formValues.startTime
      }))
    };

    const response = await axios.post(saveExamScheduleUrl, sendingData);
    setSuccessMessage('Configuración guardada correctamente');
    return response.data;
  } catch (error) {
    console.error('Error en handleSubmit:', error);
    const errorMessage = error.response?.data?.message || 
                        possibleErrors[error.message] || 
                        error.message || 
                        possibleErrors.default;
    setErrorMessage(errorMessage);
    throw error;
  } finally {
    setLoading(false);
  }
};

export const onStartDateChange = (date, formValues, setErrorMessage, handleChange) => {
  try {
    if (!date) {
      throw new Error('invalidDate');
    }

    const newDate = date instanceof Date ? date : parseISO(date);
    
    if (isNaN(newDate.getTime())) {
      throw new Error('invalidDate');
    }

    if (formValues.endDate && newDate > new Date(formValues.endDate)) {
      throw new Error('startDate');
    }

    handleChange('startDate', newDate);
    setErrorMessage(null);
  } catch (error) {
    setErrorMessage(possibleErrors[error.message] || possibleErrors.default);
  }
};

export const onEndDateChange = (date, formValues, setErrorMessage, handleChange) => {
  try {
    if (!date) {
      throw new Error('invalidDate');
    }

    const newDate = date instanceof Date ? date : parseISO(date);
    
    if (isNaN(newDate.getTime())) {
      throw new Error('invalidDate');
    }

    if (formValues.startDate && newDate < new Date(formValues.startDate)) {
      throw new Error('endDate');
    }

    handleChange('endDate', newDate);
    setErrorMessage(null);
  } catch (error) {
    setErrorMessage(possibleErrors[error.message] || possibleErrors.default);
  }
};

export const isFormValid = (formValues) => {
  try {
    // Validar fechas si no es "todo el año"
    if (!formValues.allYear) {
      const startDateValid = formValues.startDate instanceof Date && !isNaN(formValues.startDate.getTime());
      const endDateValid = formValues.endDate instanceof Date && !isNaN(formValues.endDate.getTime());
      
      if (!startDateValid || !endDateValid) {
        return false;
      }

      if (formValues.startDate > formValues.endDate) {
        return false;
      }
    }

    // Validar días y hora
    if (formValues.applicationDays.length === 0 || !formValues.startTime) {
      return false;
    }

    return true;
  } catch (error) {
    console.error('Error en isFormValid:', error);
    return false;
  }
};

export const handleCheckboxChange = (day, setFormValues) => {
  setFormValues((prevValues) => {
    const newDays = prevValues.applicationDays.includes(day)
      ? prevValues.applicationDays.filter(d => d !== day)
      : [...prevValues.applicationDays, day];
    return {
      ...prevValues,
      applicationDays: newDays
    };
  });
};