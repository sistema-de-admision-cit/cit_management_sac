export function deriveFilters (selections) {
  const values = selections.map(s => s.value)

  // si incluye el grupo 'Primaria' y no hay selec. finos en ese grupo:
  const onlyPrim = values.includes('Primaria') && !values.some(v => ['FIRST', 'SECOND', 'THIRD', 'FOURTH', 'FIFTH', 'SIXTH'].includes(v))
  const onlySec = values.includes('Secundaria') && !values.some(v => ['SEVENTH', 'EIGHTH', 'NINTH', 'TENTH'].includes(v))

  let sectorParam = 'All'
  if (onlyPrim) sectorParam = 'Primaria'
  else if (onlySec) sectorParam = 'Secundaria'

  // Para grades:
  // - Si el usuario eligió el grupo y no hay grados específicos, enviamos grades=''
  // - Si eligió grados específicos, ignoramos el grupo y enviamos solo esos
  const specificGrades = values.filter(v => v !== 'Primaria' && v !== 'Secundaria')
  const gradeParam = specificGrades.length > 0
    ? specificGrades.join(',')
    : 'All'

  return { gradeParam, sectorParam }
}
