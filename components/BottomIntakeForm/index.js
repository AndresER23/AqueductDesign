import { Modal, ModalHeader, ModalFooter, ModalBody } from "reactstrap";
import { useState, useEffect } from "react";
import Image from "next/image";

const initialBottomIntakeForm = {
    freeEdge : 0,
    channelSlope : 0,
    wallThickness : 0,
    damWidth : 0,
    lateralContractions : 0,
    barsDiameter :0,
    spacingBetweenBars : 0,
    speedBetweenBars :0 ,
    designFlow : 0,
    idAttachedAqueduct : 0
}

const initialError = {
  message: "",
  status: false,
};

const BottomIntakeForm = ({
  BottomIntakeModal,
  setBottomIntakeModal,
  propAqueductId,
  setBottomIntake,
  designFlowProp
}) => {

const [localform, setLocalform] = useState(initialBottomIntakeForm);
const [error, setError] = useState(initialError);

useEffect(() => {
  setLocalform({
    ...localform,
    idAttachedAqueduct: propAqueductId,
    designFlow : designFlowProp
  });
}, []);

const handleChange = (e)  => {  
  setLocalform({ 
    ...localform,
    [e.target.name] : e.target.value  
  });
}
const clearErrors = () => {
  const showError = setTimeout(() => {
    setError({
      message: false,
    });
  }, 5000);

  clearTimeout(showError)
}

const handleSubmit = (e) => {
  e.preventDefault();
  
  if(localform.channelSlope > 3 || localform.channelSlope < 0){
    setError({
      message:"La pendiente del canal de aducción no puede superar el 3%, a mayor pendiente hay una mayor velocidad, esta erosionará el muro. ",
          status: true,
        })
        return
    } else if (localform.freeEdge < 0.1 || localform.freeEdge > 0.3) {
        setError({
          message: "El borde libre se suele comprender entre 10 a 30 cm",
          status: true,
        })
        return
      }
      
    const url ="http://localhost:8080/bottomintake";
     
    //Setting the request head.
     const requestInit = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(localform),
    };

    fetch(url, requestInit)
      .then((res) => res.json())
      .then((res) => setBottomIntake(res))

      setBottomIntakeModal(false);
    }

const handleCancel = () => {
        setBottomIntakeModal(false);
    }

  return (
    <>
      <div className="contiainer">
        <div className="row">
          <div className="col"></div>
        </div>
        <div className="col">
          <Modal isOpen={BottomIntakeModal}>
            <ModalHeader>
              <div className="container">
                <div className="row">
                  <div className="col">
                    <h5>Diseño de bocatoma de fondo.</h5>
                  </div>
                </div>
              </div>
            </ModalHeader>
            <ModalBody>
              <div className="container d-flex justify-content-center">
                <div className="row">
                  <div className="col md-9 ">
                      <label htmlFor="freeEdge">Borde libre:</label>
                      <input type="text" id="freeEdge" name="freeEdge"onChange={handleChange} placeholder="En metros"/>
                      <br></br>
                      <label htmlFor="channelSlope">Pendiente del canal:</label>
                      <input type="text" id="channelSlope" name="channelSlope"onChange={handleChange} placeholder="Sin %"/>
                      <br></br>
                      <label htmlFor="wallThickness">Ancho del muro:</label>
                      <input type="text" id="wallThickness" name="wallThickness"onChange={handleChange} placeholder="En metros"/>
                      <label htmlFor="damWidth">Ancho de la presa:</label>
                      <input type="text" id="damWidth" name="damWidth" onChange={handleChange} placeholder="En metros"/>
                  </div>
                  <div className="col">
                      <label htmlFor="lateralContractions">Contracciones laterales.</label>
                      <input type="text" id="lateralContractions" name="lateralContractions"onChange={handleChange} placeholder="Numero de contracciones"/>
                      <label htmlFor="barsDiameter">Diametro de las barras:</label>
                      <input type="text" id="barsDiameter"name="barsDiameter" onChange={handleChange} placeholder="En metros"/>
                      <label htmlFor="spacingBetweenBars">Espacio entre las barras:</label>
                      <input type="text" id="spacingBetweenBars" name="spacingBetweenBars"onChange={handleChange} placeholder="En metros"/>
                      <label htmlFor="speedBetweenBars">Velocidad entre barrotes:</label>
                      <input type="text" id="speedBetweenBars" name="speedBetweenBars"onChange={handleChange} placeholder="En metros"/>
                  </div>
                </div>
              </div>
            </ModalBody>
            <ModalFooter>
            <form onSubmit={handleSubmit}>
                <button className="btn buttonsave">Seguir</button>
            </form>
                <button className="btn buttoncancel"onClick={handleCancel}>Regresar</button>
            </ModalFooter>
            {/* Conditional render */}
            {error.status == true && (
                  <div className="container">
                    <div className="row"></div>
                    <div className="col">
                      <div className="alert alert-danger" role="alert">
                        {`Error: ${error.message}`}
                        {clearErrors()}
                      </div>
                    </div>
                  </div>
                )}
          </Modal>
        </div>
      </div>

      <style jsx>{`
        p {
          text-align: center;
          justify-content: center;
        }
        h5 {
          text-align: center;
          color: #89bbfe;
          width: 100%;
        }
        .container{
            text-align:center;
        }

        input {
          border-radius: 30px;
          text-align: center;
          border: 1px solid #89bbfe;
          margin: 2px 10px;
        }
        .modal-footer {
          width: 100%;
        }

        .designPeriod {
          text-align: center;
          margin-top: 10%;
        }

        input:focus {
          background: #dde7f3;
        }
        .formprojection {
          align-items: center;
          text-align: center;
        }
        .buttonsave {
          width: 80px;
          background: #b2eebd;
          border: none solid transparent !important;
          padding: 5px;
          border-radius: 25px;
          margin-top: 10px;
          height: 40px;
          display: flex;
          text-align:center;
          justify-content: center;
        }
        .buttoncancel {
          width: 80px;
          background: #f98c8c;
          border: none solid transparent !important;
          padding: 5px;
          border-radius: 25px;
          margin-top: 10px;
          height: 40px;
          display: flex;
          text-align:center;
          justify-content: center;
        }
      `}</style>
    </>
  );
};

export default BottomIntakeForm;
