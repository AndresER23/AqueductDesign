import { useState, useEffect } from "react";
import { Modal, ModalBody, ModalFooter, ModalHeader } from "reactstrap";

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
const ProjectionsForm = ({ setForm, propAqueductId }) => {
  const [localForm, setLocalForm] = useState(initialForm);
  const [error, setError] = useState(initialError);
  console.log(propAqueductId);

  const handleChange = (e) => {
    setLocalForm({
      ...localForm,
      [e.target.name]: e.target.value,
    });
  };
  const url = "http://localhost:8080/projection/arithmetic";

  useEffect(() => {
    setLocalForm({
      ...localForm,
      aqueductId: propAqueductId,
    });
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();

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

    const requestInit = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(localForm),
    };

    fetch(url, requestInit)
      .then((res) => res.json())
      .then((res) => console.log(res));
  };
  return (
    <>
      <Modal>
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
              <div className="col">
                <input
                  type="text"
                  placeholder="Periodo de diseño"
                  name="finalTime"
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
                <form onSubmit={handleSubmit}>
                  <label htmlFor="populationInitialCensus">
                    Poblacion inicial
                  </label>
                  <br></br>
                  <input
                    type="number"
                    placeholder="Población censo inicial"
                    name="populationInitialCensus"
                    onChange={handleChange}
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
                    required
                  ></input>
                  <br></br>
                  <input type="submit" className="btn btn-primary" />
                </form>
              </div>
            </div>
          </div>
        </ModalBody>
        <ModalFooter>
          
        </ModalFooter>
      </Modal>

      {error.status == true && (
        <div className="container">
          <div className="row"></div>
          <div className="col">
            <div className="alert alert-danger" role="alert">
              {`Error: ${error.message} `}

              {setTimeout(() => {
                setError({
                  message: false,
                });
              }, 5000)}
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default ProjectionsForm;
