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
