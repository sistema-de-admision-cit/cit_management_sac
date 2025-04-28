import React, { useState, useMemo, useEffect } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import ExamSourceSection from '../organisms/ExamSourceSection'
import AttendanceSection from '../organisms/AttendanceSection'
import AdmissionFinalSection from '../organisms/AdmissionFinalSection'
import '../../../../../assets/styles/reports/graphical-report-styles.css'

// Map of grade enums to their display labels
const GRADE_MAPPINGS = {
  Primaria: [
    { value: 'FIRST', label: 'Primero' },
    { value: 'SECOND', label: 'Segundo' },
    { value: 'THIRD', label: 'Tercero' },
    { value: 'FOURTH', label: 'Cuarto' },
    { value: 'FIFTH', label: 'Quinto' },
    { value: 'SIXTH', label: 'Sexto' }
  ],
  Secundaria: [
    { value: 'SEVENTH', label: 'Séptimo' },
    { value: 'EIGHTH', label: 'Octavo' },
    { value: 'NINTH', label: 'Noveno' },
    { value: 'TENTH', label: 'Décimo' }
  ]
}

// Flattened list of all grades
const ALL_GRADES = [...GRADE_MAPPINGS.Primaria, ...GRADE_MAPPINGS.Secundaria]

// Sector dropdown options
const SECTOR_OPTIONS = [
  { value: 'All', label: 'All' },
  { value: 'Primaria', label: 'Primaria' },
  { value: 'Secundaria', label: 'Secundaria' }
]

export default function GraphicalReportsView () {
  // Attendance filters state
  const [attendanceStartDate, setAttendanceStartDate] = useState(new Date('2025-04-01'))
  const [attendanceEndDate, setAttendanceEndDate] = useState(new Date('2025-04-30'))
  const [attendanceSector, setAttendanceSector] = useState('All')
  const [attendanceGrade, setAttendanceGrade] = useState('All')

  // Admission-final filters state
  const [admissionStartDate, setAdmissionStartDate] = useState(new Date('2025-04-01'))
  const [admissionEndDate, setAdmissionEndDate] = useState(new Date('2025-04-30'))
  const [admissionSector, setAdmissionSector] = useState('All')
  const [admissionGrade, setAdmissionGrade] = useState('All')

  // Compute grade options based on sector for attendance
  const attendanceGradeOptions = useMemo(() => {
    const opts = attendanceSector === 'Primaria'
      ? GRADE_MAPPINGS.Primaria
      : attendanceSector === 'Secundaria'
        ? GRADE_MAPPINGS.Secundaria
        : ALL_GRADES
    return [{ value: 'All', label: 'All' }, ...opts]
  }, [attendanceSector])

  useEffect(() => { setAttendanceGrade('All') }, [attendanceSector])

  // Compute grade options for admission-final
  const admissionGradeOptions = useMemo(() => {
    const opts = admissionSector === 'Primaria'
      ? GRADE_MAPPINGS.Primaria
      : admissionSector === 'Secundaria'
        ? GRADE_MAPPINGS.Secundaria
        : ALL_GRADES
    return [{ value: 'All', label: 'All' }, ...opts]
  }, [admissionSector])

  useEffect(() => { setAdmissionGrade('All') }, [admissionSector])

  return (
    <SectionLayout title='Reportes Gráficos'>
      <div className='graphical-reports-container container'>
        <div className='exam-source-section'>
          <ExamSourceSection />
        </div>

        <div className='attendance-section'>
          <AttendanceSection
            startDate={attendanceStartDate}
            endDate={attendanceEndDate}
            sector={attendanceSector}
            grade={attendanceGrade}
            setStartDate={setAttendanceStartDate}
            setEndDate={setAttendanceEndDate}
            setSector={setAttendanceSector}
            setGrade={setAttendanceGrade}
            sectorOptions={SECTOR_OPTIONS}
            gradeOptions={attendanceGradeOptions}
          />
        </div>

        <div className='admission-final-section'>
          <AdmissionFinalSection
            startDate={admissionStartDate}
            endDate={admissionEndDate}
            sector={admissionSector}
            grade={admissionGrade}
            setStartDate={setAdmissionStartDate}
            setEndDate={setAdmissionEndDate}
            setSector={setAdmissionSector}
            setGrade={setAdmissionGrade}
            sectorOptions={SECTOR_OPTIONS}
            gradeOptions={admissionGradeOptions}
          />
        </div>
      </div>
    </SectionLayout>
  )
}
