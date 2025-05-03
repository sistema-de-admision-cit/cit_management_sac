import { useEffect, useState } from 'react';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { styled } from '@mui/material/styles';
import Checkbox from '@mui/material/Checkbox';
import FormControlLabel from '@mui/material/FormControlLabel';
import { statusOptionsForEnrollment, isCommentRequired, isEnabled } from '../helpers/helpers';
import LeftArrowIcon from '../../../../../assets/icons/arrow-left-svgrepo-com.svg'
import InputField from '../../../../core/global/atoms/InputField';
import Button from '@mui/material/Button';
import '../../../../../assets/styles/enrollments/enrollment-info-edit.css'
import { handleGetExamPeriods, handleIsDateAllowed, handleEditSubmit, verifyAllRequiredFieldsFilled } from '../helpers/handlers';

const FormContainer = styled('div')({
  display: 'flex',
  flexDirection: 'column',
  width: '100%',
  maxWidth: '600px',
  margin: '0 auto',
  padding: '25px 30px',
  boxSizing: 'border-box',
  color: 'black',
});

const FullWidthField = styled('div')({
  width: '100%',
  marginBottom: '20px',
  '& label': {
    textAlign: 'left',
    width: '100%',
    display: 'block'
  },
  color: 'inherit',
});

const SectionTitle = styled('h3')({
  textAlign: 'left',
  margin: '8px 0',
  fontSize: '1rem',
  fontWeight: '600',
  color: 'inherit'
});

const CheckboxContainer = styled('div')({
  display: 'flex',
  width: '100%',
  margin: '0',
  gap: '10px',
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

const EnrollmentInfoEdit = ({ enrollment, setIsEditing, setErrorMessage, setSuccessMessage }) => {
  const [year, month, day] = enrollment.examDate.split('-').map(Number);

  const [formData, setFormData] = useState({
    enrollmentId: enrollment.id,
    status: enrollment.status,
    examDate: enrollment.examDate ? new Date(year, month - 1, day) : null,
    whatsappNotification: enrollment.whatsappNotification,
    previousGrades: enrollment.student.previousGrades,
    comment: '',
    changedBy: 1
  })
  const [examPeriods, setExamPeriods] = useState([])

  useEffect(() => {
    handleGetExamPeriods(setExamPeriods)
  }, [])

  const handleDateChange = (newValue) => {
    setFormData({ ...formData, examDate: newValue });
  }

  return (
    <LocalizationProvider dateAdapter={AdapterDateFns}>
      <FormContainer>
        <div className='edit-header'>
          <Button
            className={`enabled`}
            onClick={() => setIsEditing(false)}
            disabled={false}
          >
            <img src={LeftArrowIcon} alt='icono de editar' />
          </Button>
          <h2>
            Editar Inscripción
          </h2>
        </div>
        <div>
          <form onSubmit={(e) => handleEditSubmit(e, enrollment, formData, setIsEditing, setErrorMessage, setSuccessMessage)} style={{ width: '100%' }}>
            {/* Estado */}
            <FullWidthField>
              <InputField
                field={{
                  type: 'dropdown',
                  name: 'status',
                  label: 'Estado de Inscripción',
                  options: statusOptionsForEnrollment,
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
                label="Cambiar fecha de examen"
                value={formData.examDate}
                onChange={handleDateChange}
                shouldDisableDate={(date) => !handleIsDateAllowed(date, examPeriods)}
                minDate={new Date()}
                format="dd/MM/yyyy" 
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

            <FullWidthField>
              <FormControlLabel control={<Checkbox
                label="Recibir notificación por WhatsApp"
                checked={formData.whatsappNotification}
                onChange={(e) => setFormData({ ...formData, whatsappNotification: e.target.checked })}
              />} label="Recibir notificación por WhatsApp" />
            </FullWidthField>

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
                  if ((parseFloat(value) >= 0 && parseFloat(value) <= 100)) {
                    setFormData({ ...formData, previousGrades: value });
                  }
                  if (value === '') {
                    setFormData({ ...formData, previousGrades: enrollment.student.previousGrades });
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
                disabled={!verifyAllRequiredFieldsFilled(formData, enrollment) || !isEnabled(formData, enrollment)}
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
        </div>
      </FormContainer>
    </LocalizationProvider>
  );
};

export default EnrollmentInfoEdit;