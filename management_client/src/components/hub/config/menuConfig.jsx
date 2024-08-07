// src/config/menuConfig.js
import { logout } from '../../../global_helpers/logoutHandler'

// Question views
import AddQuestionView from '../../questions/add_questions/view/AddQuestionView'
import ModifyQuestionView from '../../questions/modify_questions/view/ModifyQuestionView'
import DeleteQuestionView from '../../questions/delete_questions/view/DeleteQuestionView'

// componente por defecto para las rutas padre
// feature temporal
// TODO: implementar un compononente para cada padre
const DefaultComponent = ({ label }) => (
  <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
    componente por defecto para {label}
  </div>
)

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
      { key: 'dashboard', label: 'Dashboard', path: '/dashboard', parentComponent: DefaultComponent },
      {
        key: 'inscripciones',
        label: 'Gestión de Inscripciones',
        path: '/inscripciones',
        parentComponent: DefaultComponent,
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
        parentComponent: DefaultComponent,
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
        parentComponent: DefaultComponent,
        subItems: [
          { key: 'ingresar', label: 'Ingresar Preguntas', path: '/examenes/ingresar', component: AddQuestionView },
          { key: 'modificar', label: 'Modificar Preguntas', path: '/examenes/modificar', component: ModifyQuestionView },
          { key: 'eliminar', label: 'Eliminar Preguntas', path: '/examenes/eliminar', component: DeleteQuestionView },
          { key: 'generar', label: 'Generar Exámenes', path: '/examenes/generar' },
          { key: 'presentar', label: 'Presentar Exámenes', path: '/examenes/presentar' }
        ]
      },
      {
        key: 'resultados',
        label: 'Gestión de Resultados',
        path: '/resultados',
        parentComponent: DefaultComponent,
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
        parentComponent: DefaultComponent,
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
