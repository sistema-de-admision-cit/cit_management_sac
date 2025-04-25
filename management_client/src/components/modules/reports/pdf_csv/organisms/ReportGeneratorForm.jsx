import React, { useState, useEffect } from 'react';
import { 
  Button, 
  Grid, 
  Typography, 
  FormControl, 
  InputLabel, 
  Select, 
  MenuItem, 
  Box,
  useTheme,
  Alert,
  Snackbar
} from '@mui/material';
import DateRangePicker from '../molecules/DateRangePicker';
import { getEnumOptions } from '../helpers/handler';

const ReportGeneratorForm = ({ onGenerate, isLoading }) => {
  const theme = useTheme();
  const [request, setRequest] = useState({
    startDate: null,
    endDate: null,
    reportType: 'ALL',
    knownThroughFilter: 'ALL',
    gradeFilter: 'ALL',
    statusFilter: 'ALL',
    provinceFilter: 'ALL',
    gradeTypeFilter: 'ALL'
  });
  
  const [options, setOptions] = useState({
    reportTypes: [],
    knownThroughOptions: [],
    gradeOptions: [],
    processStatusOptions: [],
    gradeTypeOptions: [],
    provinceOptions: []
  });

  const [error, setError] = useState(null);
  const [openSnackbar, setOpenSnackbar] = useState(false);

  useEffect(() => {
    const loadOptions = async () => {
      try {
        const opts = await getEnumOptions();
        setOptions({
          ...opts,
          // Aseguramos que todas las opciones tengan "Todos"
          gradeOptions: [{ value: 'ALL', label: 'Todos' }, ...opts.gradeOptions],
          processStatusOptions: [{ value: 'ALL', label: 'Todos' }, ...opts.processStatusOptions],
          gradeTypeOptions: [{ value: 'ALL', label: 'Todos' }, ...opts.gradeTypeOptions],
          provinceOptions: [{ value: 'ALL', label: 'Todos' }, ...(opts.provinceOptions || [])]
        });
      } catch (err) {
        setError('Error cargando las opciones del formulario');
        setOpenSnackbar(true);
      }
    };
    loadOptions();
  }, []);

  const handleChange = (field, value) => {
    setRequest(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleCloseSnackbar = () => {
    setOpenSnackbar(false);
  };

  const handleSubmit = (format) => {
    if (!request.startDate || !request.endDate) {
      setError('Por favor seleccione ambas fechas');
      setOpenSnackbar(true);
      return;
    }
    
    if (!request.reportType) {
      setError('Por favor seleccione un tipo de reporte');
      setOpenSnackbar(true);
      return;
    }
    
    const reportRequest = {
      startDate: request.startDate,
      endDate: request.endDate,
      reportType: request.reportType === 'ALL' ? null : request.reportType,
      knownThroughFilter: request.knownThroughFilter === 'ALL' ? null : request.knownThroughFilter,
      gradeFilter: request.gradeFilter === 'ALL' ? null : request.gradeFilter,
      statusFilter: request.statusFilter === 'ALL' ? null : request.statusFilter,
      provinceFilter: request.provinceFilter === 'ALL' ? null : request.provinceFilter,
      gradeTypeFilter: request.gradeTypeFilter === 'ALL' ? null : request.gradeTypeFilter
    };
    
    onGenerate(reportRequest, format);
  };

  return (
    <Box sx={{ 
      maxWidth: 1200,
      margin: '0 auto',
      p: 3,
      backgroundColor: theme.palette.background.paper,
      borderRadius: 2,
      boxShadow: theme.shadows[1]
    }}>
      <Typography variant="h6" gutterBottom sx={{ 
        color: theme.palette.text.primary,
        mb: 3,
        fontWeight: 'medium'
      }}>
        Configuración del Reporte
      </Typography>
      
      <Snackbar
        open={openSnackbar}
        autoHideDuration={6000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
      >
        <Alert 
          onClose={handleCloseSnackbar} 
          severity="error"
          sx={{ width: '100%' }}
        >
          {error}
        </Alert>
      </Snackbar>
      
      <Grid container spacing={3}>
        {/* Selector de Tipo de Reporte */}
        <Grid item xs={12} md={6}>
          <FormControl fullWidth>
            <InputLabel id="report-type-label">Tipo de Reporte</InputLabel>
            <Select
              labelId="report-type-label"
              value={request.reportType}
              label="Tipo de Reporte"
              onChange={(e) => handleChange('reportType', e.target.value)}
              sx={{ minWidth: 250 }}
            >
              <MenuItem value="ALL">Todos los reportes</MenuItem>
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
        
        {/* Filtros condicionales - Todos con opción "Todos" */}
        {request.reportType === 'KNOWN_THROUGH' && (
          <Grid item xs={12} md={6}>
            <FormControl fullWidth>
              <InputLabel id="known-through-label">Conocido por</InputLabel>
              <Select
                labelId="known-through-label"
                value={request.knownThroughFilter}
                label="Conocido por"
                onChange={(e) => handleChange('knownThroughFilter', e.target.value)}
                sx={{ minWidth: 250 }}
              >
                {options.knownThroughOptions.map((opt) => (
                  <MenuItem key={opt.value} value={opt.value}>
                    {opt.label}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>
        )}
        
        {request.reportType === 'GRADE_TO_ENROLL' && (
          <Grid item xs={12} md={6}>
            <FormControl fullWidth>
              <InputLabel id="grade-label">Grado a matricular</InputLabel>
              <Select
                labelId="grade-label"
                value={request.gradeFilter}
                label="Grado a matricular"
                onChange={(e) => handleChange('gradeFilter', e.target.value)}
                sx={{ minWidth: 250 }}
              >
                {options.gradeOptions.map((opt) => (
                  <MenuItem key={opt.value} value={opt.value}>
                    {opt.label}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>
        )}
        
        {request.reportType === 'PROCESS_STATUS' && (
          <Grid item xs={12} md={6}>
            <FormControl fullWidth>
              <InputLabel id="status-label">Estado del proceso</InputLabel>
              <Select
                labelId="status-label"
                value={request.statusFilter}
                label="Estado del proceso"
                onChange={(e) => handleChange('statusFilter', e.target.value)}
                sx={{ minWidth: 250 }}
              >
                {options.processStatusOptions.map((opt) => (
                  <MenuItem key={opt.value} value={opt.value}>
                    {opt.label}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>
        )}
        
        {request.reportType === 'PROVINCE' && (
          <Grid item xs={12} md={6}>
            <FormControl fullWidth>
              <InputLabel id="province-label">Provincia</InputLabel>
              <Select
                labelId="province-label"
                value={request.provinceFilter}
                label="Provincia"
                onChange={(e) => handleChange('provinceFilter', e.target.value)}
                sx={{ minWidth: 250 }}
              >
                {options.provinceOptions.map((opt) => (
                  <MenuItem key={opt.value} value={opt.value}>
                    {opt.label}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>
        )}
        
        {request.reportType === 'GRADES' && (
          <Grid item xs={12} md={6}>
            <FormControl fullWidth>
              <InputLabel id="grade-type-label">Tipo de calificación</InputLabel>
              <Select
                labelId="grade-type-label"
                value={request.gradeTypeFilter}
                label="Tipo de calificación"
                onChange={(e) => handleChange('gradeTypeFilter', e.target.value)}
                sx={{ minWidth: 250 }}
              >
                {options.gradeTypeOptions.map((opt) => (
                  <MenuItem key={opt.value} value={opt.value}>
                    {opt.label}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>
        )}
        
        {/* Botones de Generación */}
        <Grid item xs={12}>
          <Box sx={{ 
            display: 'flex', 
            gap: 2, 
            justifyContent: 'center',
            mt: 3,
            flexWrap: 'wrap'
          }}>
            <Button
              variant="contained"
              onClick={() => handleSubmit('PDF')}
              disabled={isLoading}
              sx={{
                backgroundColor: '#2ba98e',
                '&:hover': {
                  backgroundColor: '#238f77',
                },
                px: 4,
                py: 1.5,
                fontWeight: 'bold',
                minWidth: 180
              }}
            >
              {isLoading ? 'Generando...' : 'Exportar a PDF'}
            </Button>
            <Button
              variant="outlined"
              onClick={() => handleSubmit('CSV')}
              disabled={isLoading}
              sx={{
                color: '#2ba98e',
                borderColor: '#2ba98e',
                '&:hover': {
                  borderColor: '#238f77',
                },
                px: 4,
                py: 1.5,
                fontWeight: 'bold',
                minWidth: 180
              }}
            >
              {isLoading ? 'Generando...' : 'Exportar a CSV'}
            </Button>
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};

export default ReportGeneratorForm;