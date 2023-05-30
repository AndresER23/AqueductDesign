import React from "react";
import { useState, useEffect } from "react";
import { Modal, ModalHeader, ModalFooter, ModalBody } from "reactstrap";

const initialForm = {
  // sedimentationVelocity : 0,
  // hazenNumber : 0,
  // timeBottomTank : 0,
  // hydraulicRetention  : 0,
  // volumeOftheSandTrap : 0,
  // surfaceArea : 0,
  // sandTrapwidth : 0,
  // sandTraplength : 0,
  // surfaceLoad : 0,
  // critiqueDiameter : 0,
  // horizontalVelocity : 0,
  // maxHorizontalVelocity : 0,
  // dragVelocity : 0, }

  averageTownTemperature: 0,
  particleDiameter: 0,
  sandTrapGrade: 0,
  removalRate: 0,
  depth: 0,
  relationWidthHeight: 0,
  aqueductId: 0,
};

const initialError = {
  message: "",
  status: false,
};

const SandTrapForm = ({
  setSandTrap,
  SandTrapModal,
  setSandTrapModal,
  propAqueductId,
}) => {
  //States
  const [localForm, setLocalForm] = useState(initialForm);
  const [error, setError] = useState(initialError);

  //Setting the initial id to the aqueductId
  useEffect(() => {
    setLocalForm({
      ...localForm,
      aqueductId: propAqueductId,
    });
  }, []);

  //Handlers
  const handleChange = (e) => {
    setLocalForm({
      ...localForm,
      [e.target.name]: e.target.value,
    });
  };

  const handleCancel = () => {
    setSandTrapModal(false);
  };

  const clearErrors = () => {
    const showError = setTimeout(() => {
      setError({
        message: false,
      });
      clearTimeout(showError);
    }, 5000);
  };

  const handleSubmit = (e) => {
    e.preventDefault();


    if (!localForm.removalRate > 100 || localForm.removalRate < 0) {
      setError({
        message:"Al ser un porcentaje, este campo no puede ser mayor a 100% ni menor a cero.",
        status: true,
          })
          return
    }else if (localForm.averageTownTemperature > 50 || localForm.averageTownTemperature < -10 ){
      setError({
        message:"¿Estas seguro de la temperatura?",
        status: true,
          })
          return
    }else if (localForm.relationWidthHeight > 10 || localForm.relationWidthHeight < 4 ){
      setError({
        message:"La relació  argo ancho debe encontrarse entre 4:1 y 10:1, recuerda solo poner el primer numero.",
        status: true,
          })
          return
    }

    const url ="http://localhost:8080/sandtrap";

    //Setting the request head.
    const requestInit = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(localForm),
    };

    fetch(url, requestInit)
      .then((res) => res.json())  
      .then((res) => setSandTrap(res))

    setSandTrapModal(false);
  };

  return (
    <>
      <div className="container">
        <div className="row">
          <div className="col">
            <Modal isOpen={SandTrapModal}>
              <ModalHeader className="w-100">
              <div className="container">
                <div className="row">
                  <div className="col">
                    <h5 className>Diseño del Desarenador.</h5>
                  </div>
                </div>
              </div>
              </ModalHeader>
              <ModalBody>
                <form onSubmit={handleSubmit}>
                  <div className="container">
                    <div className="row">
                      <div className="col">
                        <label htmlFor="averageTownTemperature">
                          Temperatura promedio
                        </label>
                        <input
                          type="text"
                          name="averageTownTemperature"
                          id="averageTownTemperature"
                          placeholder="En °C"
                          onChange={handleChange}
                          required
                        />
                        <br />
                        <label htmlFor="particleDiameter">
                          Diametro de la párticula a remover
                        </label>
                        <input
                          type="text"
                          name="particleDiameter"
                          id="particleDiameter"
                          placeholder="En milimetros"
                          onChange={handleChange}
                          required
                        />
                        <br />
                        <label htmlFor="removalRate">
                          Porcentaje de remoción esperado.
                        </label>
                        <input
                          type="text"
                          name="removalRate"
                          id="removalRate"
                          placeholder="Sin %"
                          onChange={handleChange}
                          required
                        />
                      </div>
                      <div className="col">
                        <label htmlFor="depth">
                          Profundidad del desarenador
                        </label>
                        <input
                          type="text"
                          name="depth"
                          id="depth"
                          placeholder="En metros"
                          onChange={handleChange}
                          required
                        />
                        <br />
                        <label htmlFor="relationWidthHeight">
                          Relacion largo/ancho del desarenador
                        </label>
                        <input
                          type="text"
                          name="relationWidthHeight"
                          id="relationWidthHeight"
                          placeholder="Ej. 10:1 = 10"
                          onChange={handleChange}
                          required
                        />
                        <br />
                        <label htmlFor="sandTrapGrade">
                          Grado de complejidad del desarenador
                        </label>
                        <input
                          type="text"
                          name="sandTrapGrade"
                          id="sandTrapGrade"
                          placeholder="Ej. 1"
                          onChange={handleChange}
                          required
                        />
                      </div>
                    </div>
                    <ModalFooter>
                      <button type="submit" className="btn buttonsave">
                        Guardar
                      </button>
                      <button
                        type="reset"
                        className="btn buttoncancel"
                        onClick={handleCancel}
                        required
                      >
                        Atras
                      </button>
                    </ModalFooter>
                  </div>
                </form>
              </ModalBody>
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
      </div>

      <style jsx>{`

        .modalheader{
          margin-top: 10px;
          text-align: center;
          justify-content: center;
        }
        .depth {
          margin-top: 10px;
          text-align: center;
          justify-content: center;
        }
        label {
          display: flex;
          text-align: center;
          justify-content: center;
        }
        p {
          text-align: center;
          justify-content: center;
        }
        h5 {
          align-items: center;
          font-size: 25px;
          justify-content: center;
          text-align: center;
          color: #89bbfe;
          width: 100%;
        }
        input {
          border-radius: 30px;
          text-align: center;
          border: 1px solid #89bbfe;
          margin-bottom: 2px;
          text-align: center;
          justify-content: center;
          width: 100%;
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
        }
        .buttoncancel {
          width: 80px;
          background: #f98c8c;
          border: none solid transparent !important;
          padding: 5px;
          border-radius: 25px;
          margin-top: 10px;
        }
      `}</style>
    </>
  );
};

export default SandTrapForm;
