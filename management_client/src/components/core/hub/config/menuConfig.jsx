//
import { ROLE_SUPERADMIN, ROLE_ADMIN, ROLE_TEACHER, ROLE_PSYCHOLOGIST } from '../../global/helpers/constants.js'
// Question views
import AddQuestionView from '../../../modules/questions/add_questions/view/AddQuestionView.jsx'
import ModifyQuestionView from '../../../modules/questions/modify_questions/view/ModifyQuestionView.jsx'
import DeleteQuestionView from '../../../modules/questions/delete_questions/view/DeleteQuestionView.jsx'
import HubIcon from './HubIcon.jsx'
import ExamMenuPageView from '../../../modules/questions/menu/view/ExamMenuPageView.jsx'

// grades views
import AcademicManagementView from '../../../modules/grades/views/AcademicManagementView.jsx'
import DaiGradesManagementView from '../../../modules/grades/views/DaiManagementView.jsx'

// enrollments views
import EnrollmentsMenuPageView from '../../../modules/enrollments/menu/views/EnrollmentsMenuPageView.jsx'
import EnrollmentManagementView from '../../../modules/enrollments/management/view/EnrollmentManagementView.jsx'

// configurations views
import ConfigurationMenuPageView from '../../../modules/system_configuration/menu/views/ConfigurationMenuPageView.jsx'
import ExamScheduleConfiguratorView from '../../../modules/system_configuration/exam_schedule/view/ExamScheduleConfiguratorView.jsx'
import NotificationSettingsView from '../../../modules/system_configuration/notifications/view/NotificationSettingsView.jsx'
import QuestionConfiguratorView from '../../../modules/system_configuration/exam_question_quantity/view/QuestionConfiguratorView.jsx'

// results views
import ResultsMenuPageView from '../../../modules/results/menu/views/ResultsMenuPageView.jsx'
import ExamScoreManagementView from '../../../modules/results/load_results/view/ExamScoreManagementView.jsx'
// import GradesManagmentView from '../../../modules/results/grades_managment/view/GradesManagementView.jsx'
import AnalyzeResultsView from '../../../modules/results/analyze_results/view/AnalyzeResultsView.jsx'

// reports views
import ReportsMenuPageView from '../../../modules/reports/menu/views/ReportsMenuPageView.jsx'
import PercentagesConfiguratorView from '../../../modules/system_configuration/percentages/view/PercentagesConfiguratorView.jsx'
import AccessManegementView from '../../../modules/system_configuration/access_management/view/AccessManegementView.jsx'

// report views
import GraphicalReportsView from '../../../modules/reports/graphical/view/GraphicalReportsView.jsx'
import ReportsView from '../../../modules/reports/pdf_csv/view/ReportsView.jsx'
import DashboardView from '../../../modules/dashboard/menu/view/DashboardMenuPageView.jsx'

// componente por defecto para las rutas padre
// feature temporal

const menuConfig = [
  {
    title: 'Sistema de Admisión CIT',
    icon: <HubIcon />,
    items: [
      {
        key: 'dashboard',
        label: 'Dashboard',
        path: '/dashboard',
        roleRequired: [ROLE_SUPERADMIN, ROLE_ADMIN, ROLE_TEACHER, ROLE_PSYCHOLOGIST],
        description: 'Vista general del sistema con accesos rápidos.',
        parentComponent: DashboardView
      },
      {
        key: 'enrollments',
        label: 'Gestión de Inscripciones',
        path: '/inscripciones',
        parentComponent: EnrollmentsMenuPageView,
        roleRequired: [ROLE_SUPERADMIN, ROLE_ADMIN],
        description: 'Módulo para gestionar el proceso de inscripción de estudiantes.',
        imagePath: '/images/inscripciones.PNG',
        subItems: [
          {
            key: 'visit-requests',
            label: 'Gestionar Inscripciones',
            path: '/inscripciones/consultar',
            component: EnrollmentManagementView,
            description: 'Consultar y revisar las inscripciones existentes.',
            imagePath: '/images/inscripciones.PNG'
          }
        ]
      },
      {
        key: 'configurations',
        label: 'Configuración del Sistema',
        path: '/configuracion',
        parentComponent: ConfigurationMenuPageView,
        roleRequired: [ROLE_SUPERADMIN],
        description: 'Configuraciones generales del sistema.',
        imagePath: '/images/configuracion.PNG',
        subItems: [
          {
            key: 'appointment-settings',
            label: 'Configurar Citas',
            path: '/configuracion/citas',
            component: ExamScheduleConfiguratorView,
            description: 'Configurar horarios y citas para exámenes y reuniones.',
            imagePath: '/images/configuracion_2.PNG'
          },
          {
            key: 'percentages-settings',
            label: 'Configurar Porcentajes',
            path: '/configuracion/porcentajes',
            component: PercentagesConfiguratorView,
            description: 'Definir porcentajes de evaluación y criterios.',
            imagePath: '/images/configuracion_2.PNG'
          },
          {
            key: 'questions-settings',
            label: 'Configurar Cantidad de Preguntas',
            path: '/configuracion/preguntas',
            component: QuestionConfiguratorView,
            description: 'Definir la cantidad de preguntas en los exámenes.',
            imagePath: '/images/configuracion_2.PNG'
          },
          {
            key: 'notifications-settings',
            label: 'Configurar Notificaciones',
            path: '/configuracion/notificaciones',
            component: NotificationSettingsView,
            description: 'Configurar las notificaciones automáticas del sistema.',
            imagePath: '/images/configuracion_2.PNG'
          },
          {
            key: 'manage-access',
            label: 'Gestionar Accesos',
            path: '/configuracion/accesos',
            component: AccessManegementView,
            description: 'Gestionar accesos y permisos de los usuarios en el sistema.',
            imagePath: '/images/configuracion_2.PNG'
          }
        ]
      },
      {
        key: 'exams',
        label: 'Gestión de Exámenes',
        path: '/examenes',
        parentComponent: ExamMenuPageView,
        roleRequired: [ROLE_SUPERADMIN, ROLE_TEACHER, ROLE_PSYCHOLOGIST],
        description: 'Gestión y configuración de exámenes y preguntas.',
        imagePath: '/images/examenes.PNG',
        subItems: [
          {
            key: 'add-question',
            label: 'Ingresar Preguntas',
            path: '/examenes/ingresar',
            component: AddQuestionView,
            description: 'Agregar nuevas preguntas a los exámenes.',
            imagePath: '/images/examenes_2.PNG'
          },
          {
            key: 'modify-question',
            label: 'Modificar Preguntas',
            path: '/examenes/modificar',
            component: ModifyQuestionView,
            description: 'Modificar preguntas existentes en los exámenes.',
            imagePath: '/images/examenes_2.PNG'
          },
          {
            key: 'delete-question',
            label: 'Eliminar Preguntas',
            path: '/examenes/eliminar',
            component: DeleteQuestionView,
            description: 'Eliminar preguntas de los exámenes.',
            imagePath: '/images/examenes_2.PNG'
          }
        ]
      },
      {
        key: 'results',
        label: 'Gestión de Resultados',
        path: '/resultados',
        parentComponent: ResultsMenuPageView,
        roleRequired: [ROLE_SUPERADMIN, ROLE_ADMIN, ROLE_TEACHER, ROLE_PSYCHOLOGIST],
        description: 'Gestión y análisis de los resultados de los exámenes.',
        imagePath: '/images/analisis.PNG',
        subItems: [
          {
            key: 'load-results',
            label: 'Cargar Notas de Inglés',
            path: '/resultados/cargar',
            component: ExamScoreManagementView,
            description: 'Cargar y registrar las notas obtenidas.',
            imagePath: '/images/analisis_2.PNG'
          },
          {
            key: 'academic-exam',
            label: 'Visualizar Exámenes Académicos',
            path: '/resultados/academicos',
            component: AcademicManagementView,
            description: 'Ver notas de los examenes academicos.',
            imagePath: '/images/analisis_2.PNG'
          },
          {
            key: 'dai-exam',
            label: 'Visualizar Exámenes DAI',
            path: '/resultados/dai',
            component: DaiGradesManagementView,
            description: 'Ver notas de los examenes DAI.',
            imagePath: '/images/analisis_2.PNG'
          },
          {
            key: 'notify-results',
            label: 'Notificar Resultados',
            path: '/resultados/notificar',
            component: AnalyzeResultsView,
            description: 'Notificar a los usuarios sobre los resultados obtenidos.',
            imagePath: '/images/analisis_2.PNG'
          }
        ]
      },
      {
        key: 'reports',
        label: 'Reportes y Análisis',
        path: '/reportes',
        parentComponent: ReportsMenuPageView,
        roleRequired: [ROLE_SUPERADMIN, ROLE_ADMIN],
        description: 'Generar y visualizar reportes del sistema.',
        imagePath: '/images/reporte.PNG',
        subItems: [
          {
            key: 'graphical-reports',
            label: 'Reportes Gráficos',
            path: '/reportes/graficos',
            component: GraphicalReportsView,
            description: 'Visualizar reportes en formato gráfico.',
            imagePath: '/images/reporte_2.PNG'
          },
          {
            key: 'pdf-reports',
            label: 'Reportes PDF/CSV',
            path: '/reportes/pdf',
            component: ReportsView,
            description: 'Generar reportes en formatos PDF y CSV.',
            imagePath: '/images/reporte_2.PNG'
          }
        ]
      }
    ]
  }
]

export default menuConfig
