import * as XLSX from 'xlsx'

export const xlsxToJson = (file) => {
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

// parse data from array to json
export const convertToJson = (headers, data) => {
  return data.map((row) => {
    const obj = {}
    headers.forEach((header, index) => {
      obj[header] = row[index] || '' // Asignamos valor o un string vacío si falta algún dato
    })
    return obj
  })
}
