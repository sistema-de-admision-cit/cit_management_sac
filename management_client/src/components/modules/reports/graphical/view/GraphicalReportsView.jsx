import React from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import ExamSourceSection from '../organisms/ExamSourceSection'
import AttendanceSection from '../organisms/AttendanceSection'
import AdmissionFinalSection from '../organisms/AdmissionFinalSection'
import '../../../../../assets/styles/reports/graphical-report-styles.css'
import { useSectionFilters } from '../hooks/useSectionFilters'
import AcademicSection from '../organisms/AcademicSection'
import DaiSection from '../organisms/DaiSection'

export default function GraphicalReportsView () {
  // Instantiating independent filters for each chart
  const examSourceFilters = useSectionFilters()
  const attendanceFilters = useSectionFilters()
  const admissionFinalFilers = useSectionFilters()
  const academicFilters = useSectionFilters()
  const daiFilters = useSectionFilters()

  return (
    <SectionLayout title='Reportes Gráficos'>
      <div className='graphical-reports-container container'>
        <div className='exam-source-section'>
          <ExamSourceSection {...examSourceFilters} />
        </div>

        <div className='attendance-section'>
          <AttendanceSection {...attendanceFilters} />
        </div>

        <div className='admission-final-section'>
          <AdmissionFinalSection {...admissionFinalFilers} />
        </div>

        <div className='academic-section'>
          <AcademicSection {...academicFilters} />
        </div>

        <div className='dai-section'>
          <DaiSection {...daiFilters} />
        </div>
      </div>
    </SectionLayout>
  )
}
