import axios from '../../../../../config/axiosConfig'

/** Fetch data for the exam-source pie chart */
export const fetchExamSource = async () => {
  const { data } = await axios.get('reports/exam-source')
  return data
}

/** Fetch data for the exam-attendance bar chart */
export const fetchExamAttendance = async (
  startDate,
  endDate,
  gradeCsv = 'All',
  sector = 'All'
) => {
  const { data } = await axios.get('reports/exam-attendance', {
    params: { startDate, endDate, grade: gradeCsv, sector }
  })
  return data
}

/** Fetch data for the exam-grade bar chart */
export const fetchAdmissionFinal = async (startDate, endDate, gradeCsv = 'All', sector = 'All') => {
  const { data } = await axios.get('reports/admission-final', {
    params: { startDate, endDate, grade: gradeCsv, sector }
  })
  return data
}

/** Fetch data for the Academic Exam (distribution + grade-averages) */
export const fetchAcademicExam = async (
  startDate,
  endDate,
  gradeCsv = 'All',
  sector = 'All'
) => {
  const { data } = await axios.get('reports/academic', {
    params: { startDate, endDate, grade: gradeCsv, sector }
  })
  return data
}

/** Fetch data for the DAI Exam (details + area-averages) */
export const fetchDaiExam = async (
  startDate,
  endDate,
  gradeCsv = 'All',
  sector = 'All'
) => {
  const { data } = await axios.get('reports/dai', {
    params: { startDate, endDate, grade: gradeCsv, sector }
  })
  return data
}
