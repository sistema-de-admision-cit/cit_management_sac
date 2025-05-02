import { useState, useEffect } from 'react';
import SectionLayout from '../../../../core/global/molecules/SectionLayout';
import '../../../../../assets/styles/global/view.css';
import '../../../../../assets/styles/sytem_config/exam_schedule_configurator.css';
import Button from '../../../../core/global/atoms/Button';
import ApplicationDaysSelector from '../molecules/ApplicationDaysSelector';
import HoursSection from '../organisms/HoursSection';
import useMessages from '../../../../core/global/hooks/useMessages';
import useFormState from '../../../../core/global/hooks/useFormState';
import ExamPeriodsTable from '../organisms/ExamPeriodsTable'
import { 
  handleSubmit, 
  onStartDateChange, 
  onEndDateChange, 
  isFormValid, 
  handleCheckboxChange, handleGetAllExamPeriods, onDeleteSelectedItems 
} from '../handlers/handlers';
import DateApplicationSection from '../organisms/DateApplicationSection';

const ExamScheduleConfiguratorView = () => {
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages();
  const [loading, setLoading] = useState(false);
  const [examPeriods, setExamPeriods] = useState([])

  const { formData: formValues, setFormData: setFormValues } = useFormState({
    allYear: false,
    startDate: new Date(),
    endDate: new Date(),
    applicationDays: [],
    startTime: ''
  });

  useEffect(() =>
    handleGetAllExamPeriods(setExamPeriods, setLoading, setErrorMessage, setSuccessMessage)
    , [])

  const handleChange = (field, value) => {
    setFormValues({
      ...formValues,
      [field]: value
    })
  }

  const handleFormSubmit = async () => {
    try {
      await handleSubmit(formValues, setLoading, setErrorMessage, setSuccessMessage);
    } catch (error) {
      console.error('Error en handleFormSubmit:', error);
    }
  }

  const handleOnDelete = (selectedItems, setSelectedItems) => {
    onDeleteSelectedItems(selectedItems, setSelectedItems, setLoading, setErrorMessage)
    setExamPeriods(examPeriods.filter(period => !selectedItems.includes(period.id)))
  }

  return (
    <SectionLayout title='Configurar Citas'>
      <div className='container'>
        <h1>Configuración de citas</h1>
        <p className='description'>Configura las fechas y horarios de aplicación de los exámenes.</p>
        <div className='exam-schedule-configurator'>
          {/* Fechas de Aplicación */}
          <DateApplicationSection
            allYear={formValues.allYear}
            startDate={formValues.startDate}
            endDate={formValues.endDate}
            onAllYearChange={(e) => handleChange('allYear', e.target.checked)}
            onStartDateChange={(date) => onStartDateChange(
              date, 
              formValues, 
              setErrorMessage, 
              (field, value) => handleChange(field, value)
            )}
            onEndDateChange={(date) => onEndDateChange(
              date, 
              formValues, 
              setErrorMessage, 
              (field, value) => handleChange(field, value)
            )}
          />

          {/* Días de Aplicacion */}
          <div className='application-days'>
            <h2>Días de Aplicación <span className='required'>*</span></h2>
            <ApplicationDaysSelector 
              selectedDays={formValues.applicationDays} 
              onDayChange={(day) => handleCheckboxChange(day, setFormValues)} 
            />
          </div>

          {/* Horas de Aplicacion */}
          <div className='application-hours'>
            <h2>Hora de Aplicación <span className='required'>*</span></h2>
            <HoursSection
              startTime={formValues.startTime}
              onStartTimeChange={(e) => handleChange('startTime', e.target.value)}
            />
          </div>

          <div className='buttons'>
            <Button
              className='btn btn-primary'
              onClick={handleFormSubmit}
              disabled={!isFormValid(formValues) || loading}
            >
              {loading ? 'Creanando...' : 'Crear'}
            </Button>
            <Button className='btn btn-secondary'>Cancelar</Button>
          </div>
          <ExamPeriodsTable
            examPeriods={examPeriods}
            onDelete={handleOnDelete}
            onCreate={() => { }}
            loading={loading}
          />
        </div>
      </div>
      {renderMessages()}
    </SectionLayout>
  );
};

export default ExamScheduleConfiguratorView;