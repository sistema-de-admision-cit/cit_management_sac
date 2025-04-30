import { useState } from 'react';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { styled } from '@mui/material/styles';
import Checkbox from '@mui/material/Checkbox';
import { statusOptions, isCommentRequired, isEnabled } from '../helpers/helpers';
import InputField from '../../../../core/global/atoms/InputField';
import Button from '@mui/material/Button';

const FormContainer = styled('div')({
  display: 'flex',
  flexDirection: 'column',
  width: '100%',
  maxWidth: '600px',
  margin: '0 auto',
  padding: '25px 30px',
  boxSizing: 'border-box'
});

const FullWidthField = styled('div')({
  width: '100%',
  marginBottom: '20px',
  '& label': {
    textAlign: 'left',
    width: '100%',
    display: 'block'
  }
});

const SectionTitle = styled('h3')({
  textAlign: 'left',
  margin: '8px 0',
  fontSize: '1rem',
  fontWeight: '600',
  color: 'inherit'
});

const CheckboxContainer = styled('div')({
  width: '100%',
  margin: '5px 0 20px 0',
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'flex-start'
});

const ButtonContainer = styled('div')({
  display: 'flex',
  justifyContent: 'center',
  marginTop: '30px',
  width: '100%'
});

const EnrollmentInfoEdit = ({ enrollment, setIsEditing, handleEnrollmentEdit }) => {
  const [formData, setFormData] = useState({
    enrollmentId: enrollment.id,
    status: enrollment.status,
    examDate: enrollment.examDate ? new Date(enrollment.examDate) : null,
    whatsappNotification: enrollment.whatsappNotification,
    previousGrades: enrollment.student.previousGrades ?? '',
    comment: '',
    changedBy: 1
  });

  const allRequiredFieldsFilled = () => {
    return formData.status && formData.examDate &&
      (!isCommentRequired(formData, enrollment) || formData.comment.trim());
  };

  const handleDateChange = (newValue) => {
    setFormData(prev => ({
      ...prev,
      examDate: newValue
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!allRequiredFieldsFilled()) return;

    const dataToSend = {
      ...formData,
      examDate: formData.examDate ? formData.examDate.toISOString().split('T')[0] : null
    };
    handleEnrollmentEdit(e, dataToSend, enrollment);
  };

  return (
    <LocalizationProvider dateAdapter={AdapterDateFns}>
      <FormContainer>
        <h2 style={{
          marginBottom: '25px',
          textAlign: 'left',
          width: '100%',
          borderBottom: '1px solid #eee',
          paddingBottom: '12px',
          fontSize: '1.4rem'
        }}>
          Inscripción - {enrollment.id}
        </h2>

        <form onSubmit={handleSubmit} style={{ width: '100%' }}>
          {/* Estado */}
          <FullWidthField>
            <InputField
              field={{
                type: 'dropdown',
                name: 'status',
                label: 'Estado',
                options: statusOptions,
                fullWidth: true
              }}
              value={formData.status}
              handleChange={(e) => setFormData({ ...formData, status: e.target.value })}
            />
          </FullWidthField>

          {/* Fecha de examen */}
          <FullWidthField>
            <SectionTitle>Fecha de examen</SectionTitle>
            <DatePicker
              value={formData.examDate}
              onChange={handleDateChange}
              slotProps={{
                textField: {
                  size: 'medium',
                  variant: 'outlined',
                  fullWidth: true,
                  error: !formData.examDate,
                  helperText: !formData.examDate ? 'Seleccione una fecha válida' : null,
                  sx: {
                    '& .MuiInputBase-input': {
                      padding: '12px 14px'
                    }
                  }
                }
              }}
            />
          </FullWidthField>

          {/* Checkbox solo, alineado a la izquierda */}
          <CheckboxContainer>
            <SectionTitle>Recibir notificación por WhatsApp</SectionTitle>
            <Checkbox
              checked={formData.whatsappNotification}
              onChange={(e) => setFormData({...formData, whatsappNotification: e.target.checked})}
              sx={{
                padding: '0',
                marginLeft: '-9px', // Compensa el padding interno del checkbox
                '& .MuiSvgIcon-root': {
                  fontSize: '1.5rem' // Tamaño ligeramente mayor
                }
              }}
            />
          </CheckboxContainer>

          {/* Nota Previa */}
          <FullWidthField>
            <InputField
              field={{
                type: 'number',
                name: 'previousGrades',
                label: 'Nota Previa',
                min: 0.0,
                max: 100.0,
                step: 0.1,
                fullWidth: true
              }}
              value={formData.previousGrades}
              handleChange={(e) => {
                const value = e.target.value;
                if (value === '' || (parseFloat(value) >= 0 && parseFloat(value) <= 100)) {
                  setFormData({ ...formData, previousGrades: value });
                }
              }}
            />
          </FullWidthField>

          {/* Comentario */}
          <FullWidthField>
            <InputField
              field={{
                type: 'textarea',
                name: 'comment',
                label: 'Motivo del Cambio',
                required: isCommentRequired(formData, enrollment),
                fullWidth: true,
                minRows: 3
              }}
              value={formData.comment}
              handleChange={(e) => setFormData({ ...formData, comment: e.target.value })}
              error={isCommentRequired(formData, enrollment) && !formData.comment.trim()}
              helperText={isCommentRequired(formData, enrollment) && !formData.comment.trim() ? 'Este campo es obligatorio' : ''}
            />
          </FullWidthField>

          {/* Botón */}
          <ButtonContainer>
            <Button
              variant="contained"
              size="large"
              aria-label="Guardar Cambios"
              disabled={!allRequiredFieldsFilled() || !isEnabled(formData, enrollment)}
              type="submit"
              sx={{
                backgroundColor: '#2ba98e',
                padding: '10px 40px',
                fontSize: '1rem',
                borderRadius: '8px',
                '&:hover': {
                  backgroundColor: '#23967d',
                },
                '&:disabled': {
                  backgroundColor: '#e0e0e0',
                  color: '#a0a0a0'
                }
              }}
            >
              Guardar Cambios
            </Button>
          </ButtonContainer>
        </form>
      </FormContainer>
    </LocalizationProvider>
  );
};

export default EnrollmentInfoEdit;