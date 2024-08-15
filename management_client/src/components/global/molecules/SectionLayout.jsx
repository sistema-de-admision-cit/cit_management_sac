import '../../../assets/styles/global/section-layout.css'

const SectionLayout = ({ title, children }) => (
  <div className='section-container'>
    <title>{title}</title>
    {children}
  </div>
)

export default SectionLayout
