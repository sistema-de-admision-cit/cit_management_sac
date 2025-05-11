import React from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import FiltersPanel from '../molecules/helpers/FiltersPanel'
import ExamSourceSection from '../organisms/ExamSourceSection'
import AttendanceSection from '../organisms/AttendanceSection'
import AdmissionFinalSection from '../organisms/AdmissionFinalSection'
import AcademicSection from '../organisms/AcademicSection'
import DaiSection from '../organisms/DaiSection'
import '../../../../../assets/styles/reports/graphical-report-styles.css'
import { useSectionFilters } from '../hooks/useSectionFilters'
import FunnelTrendSection from '../organisms/FunnelTrendSection'
import LeadSourceEffectivenessSection from '../organisms/LeadSourceEffectivenessSection'
import PreviousGradesSection from '../organisms/PreviousGradesSection'

/**
 * Main view for graphical reports. Renders a top-level filter panel and multiple chart sections.
 * @component
 */
export default function GraphicalReportsView () {
  const {
    startDate,
    endDate,
    sector,
    grade,
    setStartDate,
    setEndDate,
    setSector,
    setGrade,
    sectorOptions,
    gradeOptions
  } = useSectionFilters()

  return (
    <SectionLayout title='Reportes Gráficos'>
      <div className='graphical-reports-container container'>
        <div className='filters-top'>
          <FiltersPanel
            startDate={startDate}
            endDate={endDate}
            onStartDateChange={setStartDate}
            onEndDateChange={setEndDate}
            sector={sector}
            onSectorChange={e => setSector(e.target.value)}
            grade={grade}
            onGradeChange={e => setGrade(e.target.value)}
            sectorOptions={sectorOptions}
            gradeOptions={gradeOptions}
          />
        </div>
        <div className='exam-source-section'>
          <ExamSourceSection
            startDate={startDate}
            endDate={endDate}
            grade={grade}
            sector={sector}
          />
        </div>

        <div className='lead-source-matrix-section'>
          <LeadSourceEffectivenessSection
            startDate={startDate}
            endDate={endDate}
            grade={grade}
            sector={sector}
          />
        </div>

        <div className='attendance-section'>
          <AttendanceSection
            startDate={startDate}
            endDate={endDate}
            grade={grade}
            sector={sector}
          />
        </div>

        <div className='admission-final-section'>
          <AdmissionFinalSection
            startDate={startDate}
            endDate={endDate}
            grade={grade}
            sector={sector}
          />
        </div>

        <div className='funnel-trend-section'>
          <FunnelTrendSection
            startDate={startDate}
            endDate={endDate}
            grade={grade}
            sector={sector}
          />
        </div>

        <div className='academic-section'>
          <AcademicSection
            startDate={startDate}
            endDate={endDate}
            grade={grade}
            sector={sector}
          />
        </div>

        <div className='previous-grades-section'>
          <PreviousGradesSection
            startDate={startDate}
            endDate={endDate}
            grade={grade}
            sector={sector}
          />
        </div>

        <div className='dai-section'>
          <DaiSection
            startDate={startDate}
            endDate={endDate}
            grade={grade}
            sector={sector}
          />
        </div>
      </div>
    </SectionLayout>
  )
}
