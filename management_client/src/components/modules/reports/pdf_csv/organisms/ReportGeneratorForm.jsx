import React, { useState, useEffect } from 'react'
import {
  Button,
  Grid,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Box,
  useTheme,
  Stack
} from '@mui/material'
import DateRangePicker from '../molecules/DateRangePicker'
import { getEnumOptions } from '../helpers/handler'

const ReportGeneratorForm = ({ onGenerate, isLoading }) => {
  const theme = useTheme()
  const [request, setRequest] = useState({
    startDate: null,
    endDate: null,
    reportType: '',
    knownThroughFilter: 'ALL',
    gradeFilter: 'ALL',
    statusFilter: 'ALL',
    provinceFilter: 'ALL',
    gradeTypeFilter: 'ALL'
  })

  const [options, setOptions] = useState({
    reportTypes: [],
    knownThroughOptions: [],
    gradeOptions: [],
    processStatusOptions: [],
    gradeTypeOptions: [],
    provinceOptions: []
  })

  useEffect(() => {
    const loadOptions = async () => {
      try {
        const opts = await getEnumOptions()
        setOptions({
          reportTypes: opts.reportTypes,
          knownThroughOptions: [{ value: 'ALL', label: 'Todos' }, ...opts.knownThroughOptions],
          gradeOptions: [{ value: 'ALL', label: 'Todos' }, ...opts.gradeOptions],
          processStatusOptions: [{ value: 'ALL', label: 'Todos' }, ...opts.processStatusOptions],
          gradeTypeOptions: [{ value: 'ALL', label: 'Todos' }, ...opts.gradeTypeOptions],
          provinceOptions: [{ value: 'ALL', label: 'Todos' }, ...(opts.provinceOptions || [])]
        })
      } catch (err) {
        onGenerate(null, null, 'Error cargando las opciones del formulario')
      }
    }
    loadOptions()
  }, [onGenerate])

  const handleChange = (field, value) => {
    setRequest(prev => ({
      ...prev,
      [field]: value
    }))
  }

  const validateRequest = () => {
    const { startDate, endDate, reportType } = request
    const today = new Date()
    today.setHours(0, 0, 0, 0)

    if (!startDate || !endDate) {
      return 'Por favor seleccione ambas fechas'
    }

    const start = new Date(startDate)
    const end = new Date(endDate)

    if (start > end) {
      return 'La fecha de inicio no puede ser posterior a la fecha de fin'
    }

    if (start > today || end > today) {
      return 'No se pueden seleccionar fechas futuras'
    }

    if (!reportType) {
      return 'Por favor seleccione un tipo de reporte'
    }

    return null
  }

  const handleSubmit = (format) => {
    const validationError = validateRequest()
    if (validationError) {
      onGenerate(null, null, validationError)
      return
    }

    const reportRequest = {
      startDate: request.startDate,
      endDate: request.endDate,
      reportType: request.reportType,
      knownThroughFilter: request.knownThroughFilter === 'ALL' ? null : request.knownThroughFilter,
      gradeFilter: request.gradeFilter === 'ALL' ? null : request.gradeFilter,
      statusFilter: request.statusFilter === 'ALL' ? null : request.statusFilter,
      provinceFilter: request.provinceFilter === 'ALL' ? null : request.provinceFilter,
      gradeTypeFilter: request.gradeTypeFilter === 'ALL' ? null : request.gradeTypeFilter
    }

    onGenerate(reportRequest, format)
  }

  const getVisibleFilters = () => {
    const filters = []

    if (request.reportType === 'KNOWN_THROUGH') {
      filters.push(
        <Grid item xs={12} md={6} key='knownThrough'>
          <FormControl fullWidth>
            <InputLabel id='known-through-label'>Conocido por</InputLabel>
            <Select
              labelId='known-through-label'
              value={request.knownThroughFilter}
              label='Conocido por'
              onChange={(e) => handleChange('knownThroughFilter', e.target.value)}
            >
              {options.knownThroughOptions.map((opt) => (
                <MenuItem key={opt.value} value={opt.value}>
                  {opt.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
      )
    }

    if (request.reportType === 'GRADE_TO_ENROLL') {
      filters.push(
        <Grid item xs={12} md={6} key='grade'>
          <FormControl fullWidth>
            <InputLabel id='grade-filter-label'>Grado a matricular</InputLabel>
            <Select
              labelId='grade-filter-label'
              value={request.gradeFilter}
              label='Grado a matricular'
              onChange={(e) => handleChange('gradeFilter', e.target.value)}
            >
              {options.gradeOptions.map((opt) => (
                <MenuItem key={opt.value} value={opt.value}>
                  {opt.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
      )
    }

    if (request.reportType === 'GRADES') {
      filters.push(
        <Grid item xs={12} md={6} key='gradeType'>
          <FormControl fullWidth>
            <InputLabel id='grade-type-filter-label'>Tipo de calificación</InputLabel>
            <Select
              labelId='grade-type-filter-label'
              value={request.gradeTypeFilter}
              label='Tipo de calificación'
              onChange={(e) => handleChange('gradeTypeFilter', e.target.value)}
            >
              {options.gradeTypeOptions.map((opt) => (
                <MenuItem key={opt.value} value={opt.value}>
                  {opt.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
      )
    }

    if (request.reportType === 'PROCESS_STATUS') {
      filters.push(
        <Grid item xs={12} md={6} key='status'>
          <FormControl fullWidth>
            <InputLabel id='status-filter-label'>Estado del proceso</InputLabel>
            <Select
              labelId='status-filter-label'
              value={request.statusFilter}
              label='Estado del proceso'
              onChange={(e) => handleChange('statusFilter', e.target.value)}
            >
              {options.processStatusOptions.map((opt) => (
                <MenuItem key={opt.value} value={opt.value}>
                  {opt.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
      )
    }

    if (request.reportType === 'PROVINCE') {
      filters.push(
        <Grid item xs={12} md={6} key='province'>
          <FormControl fullWidth>
            <InputLabel id='province-filter-label'>Provincia</InputLabel>
            <Select
              labelId='province-filter-label'
              value={request.provinceFilter}
              label='Provincia'
              onChange={(e) => handleChange('provinceFilter', e.target.value)}
            >
              {options.provinceOptions.map((opt) => (
                <MenuItem key={opt.value} value={opt.value}>
                  {opt.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
      )
    }

    return filters
  }

  return (
    <Box sx={{
      maxWidth: 1200,
      margin: '0 auto',
      p: 3,
      backgroundColor: theme.palette.background.paper,
      borderRadius: 2,
      boxShadow: theme.shadows[1]
    }}
    >
      <Grid container spacing={3}>
        {/* Selector de Tipo de Reporte */}
        <Grid item xs={12} md={6}>
          <FormControl fullWidth>
            <InputLabel id='report-type-label'>Tipo de Reporte</InputLabel>
            <Select
              labelId='report-type-label'
              value={request.reportType}
              label='Tipo de Reporte'
              onChange={(e) => handleChange('reportType', e.target.value)}
              sx={{ minWidth: 250 }}
            >
              {options.reportTypes.map((type) => (
                <MenuItem key={type.value} value={type.value}>
                  {type.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        {/* Selector de Rango de Fechas */}
        <Grid item xs={12}>
          <DateRangePicker
            startDate={request.startDate}
            endDate={request.endDate}
            onStartDateChange={(date) => handleChange('startDate', date)}
            onEndDateChange={(date) => handleChange('endDate', date)}
          />
        </Grid>

        {/* Filtros dinámicos */}
        {getVisibleFilters()}

        {/* Espacio adicional entre filtros y botones */}
        <Grid item xs={12} sx={{ mb: 4 }} />

        {/* Botones para Generar Reporte */}
        <Grid item xs={12}>
          <Stack
            direction='row'
            spacing={2}
            justifyContent='center'
            sx={{ mt: 6 }} // Margen superior aumentado
          >
            <Button
              variant='contained'
              onClick={() => handleSubmit('PDF')}
              disabled={isLoading}
              sx={{
                backgroundColor: '#2ba98e',
                minWidth: 200,
                padding: '10px 24px',
                fontSize: '1rem',
                fontWeight: 'bold',
                '&:hover': {
                  backgroundColor: '#228f75'
                }
              }}
            >
              {isLoading ? 'Generando...' : 'Generar PDF'}
            </Button>
            <Button
              variant='outlined'
              onClick={() => handleSubmit('CSV')}
              disabled={isLoading}
              sx={{
                minWidth: 200,
                padding: '10px 24px',
                fontSize: '1rem',
                fontWeight: 'bold',
                borderColor: '#2ba98e',
                color: '#2ba98e',
                '&:hover': {
                  borderColor: '#228f75',
                  color: '#228f75'
                }
              }}
            >
              {isLoading ? 'Generando...' : 'Generar CSV'}
            </Button>
          </Stack>
        </Grid>
      </Grid>
    </Box>
  )
}

export default ReportGeneratorForm
