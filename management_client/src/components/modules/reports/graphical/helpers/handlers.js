import axios from '../../../../../config/axiosConfig'

/**
 * Fetch data for the exam-source pie chart.
 * @param {string} startDate - ISO string for the start date (YYYY-MM-DD).
 * @param {string} endDate - ISO string for the end date (YYYY-MM-DD).
 * @param {string} [gradeCsv='All'] - Comma-separated list of grade codes or 'All'.
 * @param {string} [sector='All'] - Sector filter ('Primaria', 'Secundaria', or 'All').
 * @returns {Promise<any>} API response data for exam source.
 */
export const fetchExamSource = async (
  startDate,
  endDate,
  gradeCsv = 'All',
  sector = 'All'
) => {
  const { data } = await axios.get('reports/exam-source', {
    params: { startDate, endDate, grade: gradeCsv, sector }
  })
  return data
}

/**
 * Fetch data for the exam-attendance bar chart.
 * @param {string} startDate - ISO string for the start date (YYYY-MM-DD).
 * @param {string} endDate - ISO string for the end date (YYYY-MM-DD).
 * @param {string} [gradeCsv='All'] - Grade filter, or 'All'.
 * @param {string} [sector='All'] - Sector filter, or 'All'.
 * @returns {Promise<any>} API response data for exam attendance.
 */
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

/**
 * Fetch data for the admission-final bar chart.
 * @param {string} startDate - ISO string for the start date (YYYY-MM-DD).
 * @param {string} endDate - ISO string for the end date (YYYY-MM-DD).
 * @param {string} [gradeCsv='All'] - Grade filter, or 'All'.
 * @param {string} [sector='All'] - Sector filter, or 'All'.
 * @returns {Promise<any>} API response data for admission final.
 */
export const fetchAdmissionFinal = async (
  startDate,
  endDate,
  gradeCsv = 'All',
  sector = 'All'
) => {
  const { data } = await axios.get('reports/admission-final', {
    params: { startDate, endDate, grade: gradeCsv, sector }
  })
  return data
}

/**
 * Fetch data for the academic exam charts (distribution + grade averages).
 * @param {string} startDate - ISO string for the start date (YYYY-MM-DD).
 * @param {string} endDate - ISO string for the end date (YYYY-MM-DD).
 * @param {string} [gradeCsv='All'] - Grade filter, or 'All'.
 * @param {string} [sector='All'] - Sector filter, or 'All'.
 * @returns {Promise<any>} API response data for academic exam.
 */
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

/**
 * Fetch data for the DAI exam charts (details + area averages).
 * @param {string} startDate - ISO string for the start date (YYYY-MM-DD).
 * @param {string} endDate - ISO string for the end date (YYYY-MM-DD).
 * @param {string} [gradeCsv='All'] - Grade filter, or 'All'.
 * @param {string} [sector='All'] - Sector filter, or 'All'.
 * @returns {Promise<any>} API response data for DAI exam.
 */
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

/**
 * Fetch data for the Funel trend chart (interested -> eligible -> accepted)
 * @param {string} startDate - ISO string for the start date (YYYY-MM-DD).
 * @param {string} endDate - ISO string for the end date (YYYY-MM-DD).
 * @param {string} [gradeCsv='All'] - Grade filter, or 'All'.
 * @param {string} [sector='All'] - Sector filter, or 'All'.
 * @returns {Promise<
 * {
 * "enrollmentDate": string,
 * "interestedCount": int,
 * "eligibleCount": int,
 * "acceptedCount": int,
 * "pctInterestedToEligible": float,
 * "pctEligibleToAccepted": float
 * }[]>} API response data for Funel trend.
 */
export const fetchFunnelTrend = async (
  startDate,
  endDate,
  gradeCsv = 'All',
  sector = 'All'
) => {
  const { data } = await axios.get('reports/admission-funnel', {
    params: { startDate, endDate, grade: gradeCsv, sector }
  })
  return data
}

/**
 * Fetch data for the lead source effectiveness chart (interested -> eligible -> accepted)
 * @param {string} startDate - ISO string for the start date (YYYY-MM-DD).
 * @param {string} endDate - ISO string for the end date (YYYY-MM-DD).
 * @param {string} [gradeCsv='All'] - Grade filter, or 'All'.
 * @param {string} [sector='All'] - Sector filter, or 'All'.
 * @returns {
 * Promise<
 * {
 * "examSource": string,
 * "studentCount": int,
 * "acceptanceRate": double,
 * "avgExamScore": double
 * } []
 * >
 * } API response data for lead source effectiveness.
*/
export const fetchLeadSourceEffectiveness = async (
  startDate,
  endDate,
  gradeCsv = 'All',
  sector = 'All'
) => {
  const { data } = await axios.get('reports/lead-source-effectiveness', {
    params: { startDate, endDate, grade: gradeCsv, sector }
  })
  return data
}
