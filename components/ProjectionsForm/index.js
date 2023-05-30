import { useState, useEffect } from "react";
import { Modal, ModalBody, ModalFooter, ModalHeader } from "reactstrap";

// Initializer functions
const initialForm = {
  aqueductId: 0,
  populationLastCensus: 0,
  populationInitialCensus: 0,
  yearInitialCensus: 0,
  yearLastCensus: 0,
  finalTime: 0,
};

const initialError = {
  status: false,
  message: "",
};

//Main function

const ProjectionsForm = ({
  propAqueductId,
  setProjection,
  ProjectionModal,
  setProjectionModal,
}) => {
  //States
  const [localForm, setLocalForm] = useState(initialForm);
  const [error, setError] = useState(initialError);

  //Adding the provicded id to the initial form
  useEffect(() => {
    setLocalForm({
      ...localForm,
      aqueductId: propAqueductId,
    });
  }, []);


  //URL
  const url = "http://localhost:8080/projection/arithmetic";

  //HANDLERS
  //Setting the data of the form
  const handleChange = (e) => {
    setLocalForm({
      ...localForm,
      [e.target.name]: e.target.value,
    });
  };

  const handleCancel = () => {
    setProjectionModal(false);
  };

  /*Verifying to see if the the data provided is correct 
    and making a fetch request */
  const handleSubmit = (e) => {
    e.preventDefault();

    //Form control.
    if (localForm.finalTime < 25) {
      setError({
        message:
          "Por reglamentación técnica el periodo de de diseño no puede ser menor a 25 años",
        status: true,
      });
      return;
    } else if (localForm.yearInitialCensus > localForm.yearLastCensus) {
      setError({
        message: "El año inicial no puede ser mayor al año final",
        status: true,
      });
      return;
    } else if (
      localForm.populationInitialCensus > localForm.populationLastCensus
    ) {
      setError({
        message: "La población incial no puede ser mayor a la población final",
        status: true,
      });
      return;
    }

    //Setting the request head.
    const requestInit = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(localForm),
    };

    fetch(url, requestInit)
      .then((res) => res.json())
      .then((res)=> setProjection(res));
      setProjectionModal(false)
  };

  return (
    <>
      <Modal isOpen={ProjectionModal}>
        <ModalHeader>
          <h5>Periodo de diseño</h5>
        </ModalHeader>
        <ModalBody>
          <div className="container">
            <div className="row">
              <div className="col">
                <p>
                  Especifiquemos el periodo de diseño, se recomienda que este
                  periodo tanto para los componentes como para la proyección de
                  población sea de 25 años.
                </p>
              </div>
              <div className="col designPeriod">
                <label htmlFor="finalTime">Año actual + periodo diseño</label>
                <input
                  type="text"
                  placeholder="Periodo de diseño"
                  name="finalTime"
                  id="finalTime"
                  onChange={handleChange}
                  required
                ></input>
              </div>
            </div>
            <div className="row">
              <div className="col">
                <h5>Datos DANE</h5>
                <p>
                  Consulta los datos poblacionales mas confiables y recupera dos
                  censos con una brecha de tiempo entre ellos, necesitaremos el
                  año censado y el resultado del censo.
                </p>
              </div>
              <div className="col">
                <form onSubmit={handleSubmit} className="formprojection">
                  <label htmlFor="populationInitialCensus">
                    Poblacion inicial
                  </label>
                  <br></br>
                  <input
                    type="number"
                    placeholder="Población censo inicial"
                    name="populationInitialCensus"
                    onChange={handleChange}
                    id="populationInitialCensus"
                    required
                  ></input>
                  <br></br>

                  <label htmlFor="populationLastCensus">Poblacion final</label>
                  <br></br>
                  <input
                    type="number"
                    placeholder="Población censo final"
                    name="populationLastCensus"
                    onChange={handleChange}
                    id="populationLastCensus"
                    required
                  ></input>
                  <br></br>

                  <label htmlFor="yearInitialCensus">
                    Año del censo inicial
                  </label>
                  <br></br>
                  <input
                    type="number"
                    placeholder="Año inicial"
                    name="yearInitialCensus"
                    onChange={handleChange}
                    id="yearInitialCensus"
                    required
                  ></input>
                  <br></br>

                  <label htmlFor="yearLastCensus">Año del censo final</label>
                  <br></br>
                  <input
                    type="number"
                    placeholder="Año final"
                    name="yearLastCensus"
                    onChange={handleChange}
                    id="yearLastCensus"
                    required
                  ></input>
                  <br></br>
                  <ModalFooter>
                    <button
                      type="submit"
                      className="btn buttonsave"
                    >Guardar</button>
                    <button
                      type="reset"
                      className="btn buttoncancel"
                      onClick={handleCancel}
                    >Atras</button>
                  </ModalFooter>
                </form>
              </div>
            </div>
          </div>
        </ModalBody>

        {/* Conditional render */}
        {error.status == true && (
        <div className="container">
          <div className="row"></div>
          <div className="col">
            <div className="alert alert-danger" role="alert">
              {`Error: ${error.message}`}

              {setTimeout(() => {
                setError({
                  message: false,
                });
              }, 5000)}
            </div>
          </div>
        </div>
      )}
      </Modal>

      <style jsx>{`
        h5{
          text-align: center;
          color:#89BBFE;
        }
        input{
          border-radius: 30px;
          text-align: center;
          border: 1px solid #89BBFE;
          margin-bottom: 2px;
        }

        .designPeriod{
          text-align:center;
          margin-top:10%;
        }

        input:focus{
          background:#DDE7F3;
        }
        .formprojection{
          align-items: center;  
          text-align:center;
        }
        .buttonsave{
          width: 83px;
          background:#B2EEBD;
          border: none solid transparent !important;
          padding: 5px;
          border-radius: 25px;
          margin-top: 10px;
        }
        .buttoncancel{
          width: 83px;
          background:#F98C8C;
          border: none solid transparent !important;
          padding: 5px;
          border-radius: 25px;
          margin-top: 10px;
        }
        
      `}</style>
    </>
  );
};

export default ProjectionsForm;
