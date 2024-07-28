// src/config/menuConfig.js
import { logout } from '../../../global_helpers/logoutHandler'
import AddQuestionView from '../../questions/add_questions/view/AddQuestionView'
import ModifyQuestionView from '../../questions/delete_questions/view/ModifyQuestionView'

const menuConfig = [
  {
    title: 'Sistema de Admisión CIT',
    icon: (
      <svg width='16px' height='16px' viewBox='0 0 24 24' fill='none' xmlns='http://www.w3.org/2000/svg'>
        <path d='M4 7L7 7M20 7L11 7' stroke='#1C274C' strokeWidth='1.5' strokeLinecap='round' />
        <path d='M20 17H17M4 17L13 17' stroke='#1C274C' strokeWidth='1.5' strokeLinecap='round' />
        <path d='M4 12H7L20 12' stroke='#1C274C' strokeWidth='1.5' strokeLinecap='round' />
      </svg>
    ),
    items: [
      { key: 'dashboard', label: 'Dashboard', path: '/dashboard' },
      {
        key: 'inscripciones',
        label: 'Gestión de Inscripciones',
        path: '/inscripciones',
        subItems: [
          { key: 'formulario', label: 'Llenar Formulario', path: '/inscripciones/formulario' },
          { key: 'consultar', label: 'Consultar Inscripciones', path: '/inscripciones/consultar' },
          { key: 'actualizar-fechas', label: 'Actualizar Fechas de Examen', path: '/inscripciones/actualizar-fechas' },
          { key: 'metodo-notificacion', label: 'Método de Notificación', path: '/inscripciones/metodo-notificacion' }
        ]
      },
      {
        key: 'configuracion',
        label: 'Configuración del Sistema',
        path: '/configuracion',
        subItems: [
          { key: 'citas', label: 'Configurar Citas', path: '/configuracion/citas' },
          { key: 'porcentajes', label: 'Configurar Porcentajes', path: '/configuracion/porcentajes' },
          { key: 'notificaciones', label: 'Configurar Notificaciones', path: '/configuracion/notificaciones' },
          { key: 'accesos', label: 'Gestionar Accesos', path: '/configuracion/accesos' },
          { key: 'roles', label: 'Gestionar Roles', path: '/configuracion/roles' }
        ]
      },
      {
        key: 'examenes',
        label: 'Gestión de Exámenes',
        path: '/examenes',
        subItems: [
          { key: 'ingresar', label: 'Ingresar Preguntas', path: '/examenes/ingresar', component: AddQuestionView },
          { key: 'modificar', label: 'Modificar Preguntas', path: '/examenes/modificar', component: ModifyQuestionView },
          { key: 'eliminar', label: 'Eliminar Preguntas', path: '/examenes/eliminar' },
          { key: 'generar', label: 'Generar Exámenes', path: '/examenes/generar' },
          { key: 'presentar', label: 'Presentar Exámenes', path: '/examenes/presentar' }
        ]
      },
      {
        key: 'resultados',
        label: 'Gestión de Resultados',
        path: '/resultados',
        subItems: [
          { key: 'almacenar', label: 'Almacenar Respuestas', path: '/resultados/almacenar' },
          { key: 'cargar', label: 'Cargar Notas', path: '/resultados/cargar' },
          { key: 'analizar', label: 'Analizar Resultados', path: '/resultados/analizar' },
          { key: 'notificar', label: 'Notificar Resultados', path: '/resultados/notificar' }
        ]
      },
      {
        key: 'reportes',
        label: 'Reportes y Análisis',
        path: '/reportes',
        subItems: [
          { key: 'graficos', label: 'Reportes Gráficos', path: '/reportes/graficos' },
          { key: 'pdf', label: 'Reportes PDF/CSV', path: '/reportes/pdf' }
        ]
      },
      { key: 'logout', label: 'Cerrar Sesión', path: '#', onClick: logout }
    ]
  }
]

export default menuConfig
