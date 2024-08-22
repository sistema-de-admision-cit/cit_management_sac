import React, { useState } from 'react'
import InputField from '../../../../core/global/atoms/InputField'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import '../../../../../assets/styles/questions/view.css'
import '../../../../../assets/styles/sytem_config/exam_schedule_configurator.css'
import Button from '../../../../core/global/atoms/Button'
import DateRangePicker from '../molecules/DateRangePicker'
import ApplicationDaysSelector from '../molecules/ApplicationDaysSelector'
import HoursSection from '../molecules/HoursSection'
import DateApplicationSection from '../organisms/DateApplicationSection'

const ExamScheduleConfiguratorView = () => {
  const [formValues, setFormValues] = useState({
    allYear: false,
    startDate: new Date(),
    endDate: new Date(),
    applicationDays: [],
    startTime: '',
    endTime: ''
  })

  const handleChange = (field, value) => {
    setFormValues({
      ...formValues,
      [field]: value
    })
  }

  const handleCheckboxChange = (day) => {
    setFormValues((prevValues) => {
      const newDays = prevValues.applicationDays.includes(day)
        ? prevValues.applicationDays.filter(d => d !== day)
        : [...prevValues.applicationDays, day]
      return {
        ...prevValues,
        applicationDays: newDays
      }
    })
  }

  const onStartDateChange = (date) => {
    handleChange('startDate', date)
  }

  const onEndDateChange = (date) => {
    handleChange('endDate', date)
  }

  return (
    <SectionLayout title='Configurar Citas'>
      <div className='container exam-schedule-configurator'>
        <h1>Configuración de citas</h1>

        {/* Fechas de Aplicación */}
        <DateApplicationSection
          allYear={formValues.allYear}
          startDate={formValues.startDate}
          endDate={formValues.endDate}
          onAllYearChange={(e) => handleChange('allYear', e.target.checked)}
          onStartDateChange={onStartDateChange}
          onEndDateChange={onEndDateChange}
        />

        {/* Días de Aplicacion */}
        <div className='application-days'>
          <h2>Días de Aplicación <span className='required'>*</span></h2>
          <ApplicationDaysSelector selectedDays={formValues.applicationDays} onDayChange={handleCheckboxChange} />
        </div>

        {/* Horas de Aplicacion */}
        <div className='application-hours'>
          <h2>Horas de Aplicación</h2>
          <HoursSection startTime={formValues.startTime} endTime={formValues.endTime} onStartTimeChange={(e) => handleChange('startTime', e.target.value)} onEndTimeChange={(e) => handleChange('endTime', e.target.value)} />
        </div>

        <div className='buttons'>
          <Button className='btn btn-primary'>Guardar</Button>
          <Button className='btn btn-secondary'>Cancelar</Button>
        </div>

      </div>
    </SectionLayout>
  )
}

export default ExamScheduleConfiguratorView
