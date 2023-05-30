import PDF from "../components/pdf";
import { PDFViewer } from '@react-pdf/renderer';

const GenerateReport = () => {
    const aqueduct = JSON.parse(window.localStorage.getItem("aqueductName"))
    const projection = JSON.parse(window.localStorage.getItem("proyectionChildren"))
    const endowment = JSON.parse(window.localStorage.getItem("endowment"))
    const bottomIntake = JSON.parse(window.localStorage.getItem("bottomIntake")) 
    const sandTrap = JSON.parse(window.localStorage.getItem("sandTrap")) 
    return (<>
    <PDFViewer style={{width:"100%", height:"100vh"}}>
        <PDF 
        aqueduct={aqueduct} 
        projections={projection}
        endowment={endowment}
        bottomIntake={bottomIntake} 
        sandTrap={sandTrap}
        />
    </PDFViewer>
    </>);
}
 
export default GenerateReport;