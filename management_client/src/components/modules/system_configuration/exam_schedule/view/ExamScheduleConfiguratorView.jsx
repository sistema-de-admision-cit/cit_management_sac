import React, { useState } from 'react'
import InputField from '../../../../core/global/atoms/InputField'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import '../../../../../assets/styles/questions/view.css'
import '../../../../../assets/styles/sytem_config/exam_schedule_configurator.css'
import Button from '../../../../core/global/atoms/Button'
import DatePicker from '../../../../core/global/atoms/DatePicker'

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

  return (
    <SectionLayout title='Configurar Citas'>
      <div className='container exam-schedule-configurator'>
        <h1>Configuración de citas</h1>

        {/* Fechas de Aplicación */}
        <div className='application-dates'>
          <h2>Fechas de Aplicación <span className='required'>*</span></h2>
          <InputField
            field={{ type: 'checkbox', name: 'allYear', label: 'Todo el año' }}
            value={formValues.allYear}
            handleChange={(e) => handleChange('allYear', e.target.checked)}
          />
          {!formValues.allYear && (
            <div className='date-range'>
              {/* name, label, placeholder, value, onChange, availableDates, required = false */}

              <DatePicker
                name='startDate'
                label='Fecha Inicial'
                value={formValues.startDate}
                onChange={(date) => handleChange('startDate', date)}
                required
              />

              <DatePicker
                name='endDate'
                label='Fecha Final'
                value={formValues.endDate}
                onChange={(date) => handleChange('endDate', date)}
                required
              />
            </div>
          )}
        </div>

        {/* Días de Aplicacion */}
        <div className='application-days'>
          <h2>Días de Aplicación <span className='required'>*</span></h2>
          <div className='days'>
            {['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes'].map((day) => (
              <div key={day}>
                <InputField
                  field={{ type: 'checkbox', name: day, label: day }}
                  value={formValues.applicationDays.includes(day)}
                  handleChange={() => handleCheckboxChange(day)}
                />
              </div>
            ))}
          </div>
        </div>

        {/* Horas de Aplicacion */}
        <div className='application-hours'>
          <h2>Horas de Aplicación</h2>
          <div className='hours-range'>
            <InputField
              field={{
                type: 'time',
                name: 'startTime',
                label: 'Hora Inicial',
                required: true
              }}
              className='start-time'
              value={formValues.startTime}
              handleChange={(e) => handleChange('startTime', e.target.value)}
            />
            <InputField
              field={{
                type: 'time',
                name: 'endTime',
                label: 'Hora Final',
                required: true
              }}
              className='end-time'
              value={formValues.endTime}
              handleChange={(e) => handleChange('endTime', e.target.value)}
            />

          </div>

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
