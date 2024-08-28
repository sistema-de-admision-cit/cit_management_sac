// Question views
import AddQuestionView from '../../../modules/questions/add_questions/view/AddQuestionView.jsx'
import ModifyQuestionView from '../../../modules/questions/modify_questions/view/ModifyQuestionView.jsx'
import DeleteQuestionView from '../../../modules/questions/delete_questions/view/DeleteQuestionView.jsx'
import GenerateExamView from '../../../modules/questions/generate_exam/view/GenerateExamView.jsx'
import HubIcon from './HubIcon.jsx'
import ExamMenuPageView from '../../../modules/questions/menu/view/ExamMenuPageView.jsx'

// enrollments views
import EnrollmentsMenuPageView from '../../../modules/enrollments/menu/views/EnrollmentsMenuPageView.jsx'

// configurations views
import ConfigurationMenuPageView from '../../../modules/system_configuration/menu/views/ConfigurationMenuPageView.jsx'
import ExamScheduleConfiguratorView from '../../../modules/system_configuration/exam_schedule/view/ExamScheduleConfiguratorView.jsx'

// results views
import ResultsMenuPageView from '../../../modules/results/menu/views/ResultsMenuPageView.jsx'

// reports views
import ReportsMenuPageView from '../../../modules/reports/menu/views/ReportsMenuPageView.jsx'
import PercentagesConfiguratorView from '../../../modules/system_configuration/percentages/view/PercentagesConfiguratorView.jsx'

// componente por defecto para las rutas padre
// feature temporal
// TODO: implementar un componente para cada padre
const DefaultComponent = ({ label }) => (
  <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
    Componente por defecto para {label}
  </div>
)

const ROLE_ADMIN = 'admin'
const ROLE_TEACHER = 'teacher'
const ROLE_PSYCHOLOGIST = 'psychologist'

const menuConfig = [
  {
    title: 'Sistema de Admisión CIT',
    icon: <HubIcon />,
    items: [
      {
        key: 'dashboard',
        label: 'Dashboard',
        path: '/dashboard',
        roleRequired: [ROLE_ADMIN, ROLE_TEACHER, ROLE_PSYCHOLOGIST],
        description: 'Vista general del sistema con estadísticas y accesos rápidos.',
        parentComponent: DefaultComponent
      },
      {
        key: 'enrollments',
        label: 'Gestión de Inscripciones',
        path: '/inscripciones',
        parentComponent: EnrollmentsMenuPageView,
        roleRequired: [ROLE_ADMIN],
        description: 'Módulo para gestionar el proceso de inscripción de estudiantes.',
        subItems: [
          {
            key: 'visit-requests',
            label: 'Consultar Inscripciones',
            path: '/inscripciones/consultar',
            component: DefaultComponent,
            description: 'Consultar y revisar las inscripciones existentes.',
            imagePath: '/images/temp-card-image.avif'
          },
          {
            key: 'update-dates',
            label: 'Actualizar Fechas de Examen',
            path: '/inscripciones/actualizar-fechas',
            component: DefaultComponent,
            description: 'Actualizar las fechas de los exámenes en el sistema.',
            imagePath: '/images/temp-card-image.avif'
          },
          {
            key: 'notification-method',
            label: 'Método de Notificación',
            path: '/inscripciones/metodo-notificacion',
            component: DefaultComponent,
            description: 'Configurar métodos de notificación para inscripciones.',
            imagePath: '/images/temp-card-image.avif'
          }
        ]
      },
      {
        key: 'configurations',
        label: 'Configuración del Sistema',
        path: '/configuracion',
        parentComponent: ConfigurationMenuPageView,
        roleRequired: [ROLE_ADMIN],
        description: 'Configuraciones generales del sistema.',
        subItems: [
          {
            key: 'appointment-settings',
            label: 'Configurar Citas',
            path: '/configuracion/citas',
            component: ExamScheduleConfiguratorView,
            description: 'Configurar horarios y citas para exámenes y reuniones.'
          },
          {
            key: 'percentages-settings',
            label: 'Configurar Porcentajes',
            path: '/configuracion/porcentajes',
            component: PercentagesConfiguratorView,
            description: 'Definir porcentajes de evaluación y criterios.'
          },
          {
            key: 'notifications-settings',
            label: 'Configurar Notificaciones',
            path: '/configuracion/notificaciones',
            component: DefaultComponent,
            description: 'Configurar las notificaciones automáticas del sistema.'
          },
          {
            key: 'manage-access',
            label: 'Gestionar Accesos',
            path: '/configuracion/accesos',
            component: DefaultComponent,
            description: 'Gestionar accesos y permisos de los usuarios en el sistema.'
          },
          {
            key: 'manage-roles',
            label: 'Gestionar Roles',
            path: '/configuracion/roles',
            component: DefaultComponent,
            description: 'Gestionar los roles y permisos de los usuarios.'
          }
        ]
      },
      {
        key: 'exams',
        label: 'Gestión de Exámenes',
        path: '/examenes',
        parentComponent: ExamMenuPageView,
        roleRequired: [ROLE_ADMIN, ROLE_TEACHER, ROLE_PSYCHOLOGIST],
        description: 'Gestión y configuración de exámenes y preguntas.',
        subItems: [
          {
            key: 'add-question',
            label: 'Ingresar Preguntas',
            path: '/examenes/ingresar',
            component: AddQuestionView,
            description: 'Agregar nuevas preguntas a los exámenes.',
            imagePath: '/images/temp-card-image.avif'
          },
          {
            key: 'modify-question',
            label: 'Modificar Preguntas',
            path: '/examenes/modificar',
            component: ModifyQuestionView,
            description: 'Modificar preguntas existentes en los exámenes.',
            imagePath: '/images/temp-card-image.avif'
          },
          {
            key: 'delete-question',
            label: 'Eliminar Preguntas',
            path: '/examenes/eliminar',
            component: DeleteQuestionView,
            description: 'Eliminar preguntas de los exámenes.',
            imagePath: '/images/temp-card-image.avif'
          },
          {
            key: 'generate-exam',
            label: 'Generar Exámenes',
            path: '/examenes/generar',
            component: GenerateExamView,
            description: 'Generar exámenes a partir de las preguntas configuradas.',
            imagePath: '/images/temp-card-image-2.avif'
          }
        ]
      },
      {
        key: 'results',
        label: 'Gestión de Resultados',
        path: '/resultados',
        parentComponent: ResultsMenuPageView,
        roleRequired: [ROLE_ADMIN, ROLE_TEACHER, ROLE_PSYCHOLOGIST],
        description: 'Gestión y análisis de los resultados de los exámenes.',
        subItems: [
          {
            key: 'store-results',
            label: 'Almacenar Respuestas',
            path: '/resultados/almacenar',
            component: DefaultComponent,
            description: 'Almacenar las respuestas obtenidas en los exámenes.'
          },
          {
            key: 'load-results',
            label: 'Cargar Notas',
            path: '/resultados/cargar',
            component: DefaultComponent,
            description: 'Cargar y registrar las notas obtenidas.'
          },
          {
            key: 'analyze-results',
            label: 'Analizar Resultados',
            path: '/resultados/analizar',
            component: DefaultComponent,
            description: 'Analizar los resultados y generar informes.'
          },
          {
            key: 'notify-results',
            label: 'Notificar Resultados',
            path: '/resultados/notificar',
            component: DefaultComponent,
            description: 'Notificar a los usuarios sobre los resultados obtenidos.'
          }
        ]
      },
      {
        key: 'reports',
        label: 'Reportes y Análisis',
        path: '/reportes',
        parentComponent: ReportsMenuPageView,
        roleRequired: [ROLE_ADMIN],
        description: 'Generar y visualizar reportes del sistema.',
        subItems: [
          {
            key: 'graphical-reports',
            label: 'Reportes Gráficos',
            path: '/reportes/graficos',
            component: DefaultComponent,
            description: 'Visualizar reportes en formato gráfico.'
          },
          {
            key: 'pdf-reports',
            label: 'Reportes PDF/CSV',
            path: '/reportes/pdf',
            component: DefaultComponent,
            description: 'Generar reportes en formatos PDF y CSV.'
          }
        ]
      },
      {
        key: 'logout',
        label: 'Cerrar Sesión',
        path: '#',
        roleRequired: [ROLE_ADMIN, ROLE_TEACHER, ROLE_PSYCHOLOGIST],
        description: 'Cerrar sesión del sistema.'
      }
    ]
  }
]

export default menuConfig
