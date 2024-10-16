import * as XLSX from 'xlsx'

export const parseXlsxToArray = (file) => {
  return new Promise((resolve, reject) => {
    // eslint-disable-next-line no-undef
    const reader = new FileReader()
    reader.onload = (e) => {
      const data = new Uint8Array(e.target.result)
      const workbook = XLSX.read(data, { type: 'array' })
      const sheetName = workbook.SheetNames[0]
      const sheet = workbook.Sheets[sheetName]
      const json = XLSX.utils.sheet_to_json(sheet, { header: 1 })
      resolve(json)
    }
    reader.onerror = (e) => {
      reject(e)
    }
    reader.readAsArrayBuffer(file)
  })
}

export const parseCsvToArray = (file) => {
  return new Promise((resolve, reject) => {
    // eslint-disable-next-line no-undef
    const reader = new FileReader()
    reader.onload = (e) => {
      const text = e.target.result
      const rows = text.split('\n').map((row) => row.split(','))
      resolve(rows)
    }
    reader.onerror = (e) => {
      reject(e)
    }
    reader.readAsText(file)
  })
}

// Mapping between headers and camelCase keys
const headerMapping = {
  ID: 'id',
  'First and Middle Name': 'names',
  'Last Name(s)': 'lastNames',
  'Last test': 'lastTest',
  Level: 'level',
  Core: 'core'
}

// parse data from array to json and filter out unnecessary fields
export const convertToJson = (headers, data) => {
  // drop rows with less than 2 columns (empty rows)
  data = data.filter((row) => row.length > 1)

  return data.map((row) => {
    const obj = {}

    headers.forEach((header, index) => {
      const camelCaseKey = headerMapping[header]

      if (camelCaseKey) {
        if (camelCaseKey === 'lastTest') {
          // Formatear la fecha solo para 'lastTest'
          obj[camelCaseKey] = formatDateForJava(row[index])
        } else {
          obj[camelCaseKey] = row[index]
        }
      }
    })

    return obj
  })
}

// Helper function to format dates to 'yyyy-MM-dd' (compatible with Instant in Java)
const formatDateForJava = (dateString) => {
  const [day, month, year] = dateString?.split('.')
  return `${year}-${month}-${day}`.replace(/undefined/g, '').replace(/ /g, '')
}

export const formatLogMessage = (log) => {
  // Desestructuración del objeto log
  let {
    processId,
    enrollmentId,
    trackTestExamId,
    previousScore,
    newScore,
    examDate,
    status,
    errorMessage
  } = log

  // init message
  let message = ''

  // formatear segun el estado
  switch (status) {
    case 'success':
      if (previousScore !== newScore) {
        message = `La nota del estudiante con ID de inscripción ${enrollmentId}`
        message += previousScore ? ` se actualizó de ${previousScore} a ${newScore}.` : ` se creó con una nota de ${newScore}.`
      } else {
        message = `Advertencia: La nota del estudiante con ID de inscripción ${enrollmentId} es idéntica a la registrada anteriormente (${previousScore}). No se realizaron cambios.`
        status = 'warning'
      }
      break

    case 'error':
      if (enrollmentId) {
        message = `Error al actualizar la nota del estudiante con ID de inscripción ${enrollmentId}. Motivo: ${errorMessage}.`
      } else {
        message = errorMessage
      }
      break

    case 'warning':
      if (new Date(examDate) > new Date()) {
        message = `Advertencia: El examen con ID ${trackTestExamId} tiene una fecha de examen futura (${examDate}), por lo que no se actualizó la nota.`
        status = 'warning'
      }
      break

    default:
      message = `El proceso ${processId} actualizó la nota del estudiante con ID de inscripción ${enrollmentId}, pero se encontró un error: ${errorMessage}.`
  }

  // json with the formatted message and status
  return {
    status,
    message
  }
}
