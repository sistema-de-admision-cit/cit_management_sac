export const statusText = {
  PENDING: 'Pendiente de Revisión',
  ELIGIBLE: 'Aceptado',
  INELIGIBLE: 'Rechazado',
  ACCEPTED: 'Aceptado',
  REJECTED: 'Rechazado'
}

export const buildGuardianAddress = (guardianAddress) => {
  const direction = `${guardianAddress.province}, ${guardianAddress.city}, ${guardianAddress.district}, ${guardianAddress.addressInfo}`
  return direction
}

export const guardianTabText = {
  M: 'Informacion de la Madre',
  F: 'Informacion del Padre',
  G: 'Informacion del Encargado'
}

export const statusOptions = [
  { value: 'PENDING', label: 'Pendiente de Revisión' },
  { value: 'ELIGIBLE', label: 'Aceptar' },
  { value: 'INELIGIBLE', label: 'Rechazar' },
  { value: 'ACCEPTED', label: 'Aceptado' },
  { value: 'REJECTED', label: 'Rechazado' }
]

export const statusOptionsForEnrollment = [
  { value: 'PENDING', label: 'Pendiente de Revisión' },
  { value: 'ELIGIBLE', label: 'Aceptar' },
  { value: 'INELIGIBLE', label: 'Rechazar' },
]

export const isCommentRequired = (formData, enrollment) => {
  // Verificar si hay cambios en el estado
  if (formData.status !== enrollment.status) {
    return true;
  }

  // Comparación segura de fechas
  const currentDate = formData.examDate;
  const originalDate = enrollment.examDate ? new Date(enrollment.examDate) : null;

  if ((!currentDate && originalDate) || (currentDate && !originalDate)) {
    return true;
  }

  try {
    const currentDateStr = currentDate.toISOString().split('T')[0];
    const originalDateStr = originalDate.toISOString().split('T')[0];
    if (currentDateStr !== originalDateStr) {
      return true; 
    }
  } catch (error) {
    return true; // Por seguridad, considera que hay cambio si hay error
  }

  // Verificar si hay cambios en el campo de whatsappNotification
  if (formData.whatsappNotification !== enrollment.whatsappNotification) {
    return true;
  }

  // Verificar si hay cambios en el campo de previousGrades
  if (formData.previousGrades !== (enrollment.student.previousGrades ?? '')) {
    return true;
  }
}

export const isEnabled = (formData, enrollment) => {
  // Verificar si hay cambios
  const hasChanges =
    formData.status !== enrollment.status ||
    !areDatesEqual(formData.examDate, enrollment.examDate) ||
    formData.whatsappNotification !== enrollment.whatsappNotification ||
    formData.previousGrades !== (enrollment.student.previousGrades ?? '');

  if (!hasChanges) return false;

  if (isCommentRequired(formData, enrollment) && !formData.comment.trim()) {
    return false;
  }

  return true;
};

// Función auxiliar para comparar fechas de forma segura
export const areDatesEqual = (date1, date2) => {
  if (!date1 && !date2) return true;
  if (!date1 || !date2) return false;

  try {
    const d1 = date1 instanceof Date ? date1 : new Date(date1);
    const d2 = date2 instanceof Date ? date2 : new Date(date2);

    return d1.toISOString().split('T')[0] === d2.toISOString().split('T')[0];
  } catch (error) {
    console.error('Error al comparar fechas:', error);
    return false;
  }
};

// Función para convertir fechas en strings a objetos Date
export const formatDateToObj = (obj) => {
  if (!obj) return obj;

  // Clonar el objeto para no modificar el original directamente
  const newObj = { ...obj };

  if (newObj.birthDate) {
    newObj.birthDate = new Date(newObj.birthDate);
  }

  if (newObj.enrollments && Array.isArray(newObj.enrollments)) {
    newObj.enrollments = newObj.enrollments.map(enrollment => {
      if (enrollment.examDate) {
        return {
          ...enrollment,
          examDate: new Date(enrollment.examDate)
        };
      }
      return enrollment;
    });
  }

  return newObj;
};

// Función para formatear fecha a "day/month/year"
export const formatDate = (date) => {
  if (!date) return '';

  // Asegurarse que es un objeto Date
  const [year, month, day] = date instanceof Date ? [date.getFullYear(), date.getMonth() + 1, date.getDate()] : date.split('-').map(Number);

  const dateObj = date instanceof Date ? date : new Date(year, month - 1, day);

  // Si es inválido, retornar string vacío
  if (isNaN(dateObj.getTime())) return '';

  const formattedDay = day < 10 ? `0${day}` : day;
  const formattedMonth = month < 10 ? `0${month}` : month;

  return `${formattedDay}/${formattedMonth}/${year}`;
};

// Función para formatear fecha a "year-month-day" (API)
export const formatDateForApi = (date) => {
  if (!date) return null;

  const dateObj = date instanceof Date ? date : new Date(date);
  if (isNaN(dateObj.getTime())) return null;

  const year = dateObj.getFullYear();
  const month = String(dateObj.getMonth() + 1).padStart(2, '0');
  const day = String(dateObj.getDate()).padStart(2, '0');

  return `${year}-${month}-${day}`;
};
