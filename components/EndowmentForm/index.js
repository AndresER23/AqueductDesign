import { Modal, ModalHeader, ModalFooter, ModalBody } from "reactstrap";
import { useState, useEffect } from "react";
import Image from "next/image";

//Initial objects properties
const initialEndowmentForm = {
  netEndowment: 0,
  waterLosses: 0,
  consumptionCoefficient1: 0,
  consumptionCoefficient2: 0,
  aqueductId: 0,
};
const initialError = {
  message: "",
  status: false,
};

const url = "http://localhost:8080/endowments";

//Main function
const EndowmentForm = ({
  EndowmentModal,
  setEndowment,
  setEndowmentModal,
  propAqueductId,
  Projection,
}) => {
  const [localForm, setLocalForm] = useState(initialEndowmentForm);
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
    setEndowmentModal(false);
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

    //Setting the request head.
    const requestInit = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(localForm),
    };

    const k1 = localForm.consumptionCoefficient1;
    const k2 = localForm.consumptionCoefficient2;
    const population = Projection.populationFinal;

    if (population <= 12500 && (k1 > 1.3 || k2 > 1.6)) {
      setError({
        message:
          "Para poblaciones menores o iguales a 12500 habitantes, al periodo de diseño, en ningún caso el factor k1 será superior a 1.3 ni el factor k2 a 1.6",
        status: true,
      });

      return;
    } else if (population >= 12500 && (k1 > 1.2 || k2 > 1.5)) {
      setError({
        message:
          "Para poblaciones mayores a 12500 habitantes, al periodo de diseño, en ningún caso el factor k1 será superior a 1.2 ni el factor k2 a 1.5",
        status: true,
      });
      return;
    } else if (localForm.netEndowment < 120 || localForm.netEndowment > 140) {
      setError({
        status: true,
        message:
          "La dotación neta no puede ser menor a 120 ni mayor de 140 l/hab dia.",
      });
      return;
    }

    fetch(url, requestInit)
      .then((res) => res.json())
      .then((res) => setEndowment(res));

    setEndowmentModal(false);
  };

  return (
    <>
      <div className="main-content">
        <div className="container">
          <div className="row">
            <div className="col">
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit. In,
                maiores, asperiores voluptatibus soluta quos ex nulla sit quae
                perferendis adipisci reiciendis architecto nihil doloremque fuga
                illum sint similique corrupti nobis.
              </p>
            </div>
            <div className="col">
              <Modal isOpen={EndowmentModal}>
                <ModalHeader className="modalheader">
                  <h5>Demandas y dotaciones de agua</h5>
                </ModalHeader>
                <ModalBody>
                  <div className="container">
                    <div className="row">
                      <div className="col">
                        <Image
                          src="/dotacionNeta.png"
                          alt="Dotacion Neta"
                          width={490}
                          height={130}
                        ></Image>
                        <p>Tabla de dotación neta maxima en base a la altura</p>
                      </div>
                      <div className="row">
                        <div className="col">
                          <form
                            onSubmit={handleSubmit}
                            className="formprojection"
                          >
                            <label htmlFor="netEndowment">
                              <b>Demanda neta en base a la altura</b>
                            </label>
                            <br></br>
                            <input
                              type="number"
                              placeholder="Demanda neta"
                              name="netEndowment"
                              onChange={handleChange}
                              required
                            ></input>
                            <br></br>

                            <label htmlFor="waterLosses">
                              <b>Porcentaje de perdidas de agua sin %</b>
                            </label>
                            <br></br>
                            <input
                              type="number"
                              placeholder="Perdidas de agua"
                              name="waterLosses"
                              onChange={handleChange}
                              required
                            ></input>
                            <br></br>

                            <label htmlFor="consumptionCoefficient1">
                              <b>Coeficiente de consumo máximo k1</b>
                            </label>
                            <br></br>
                            <input
                              type="text"
                              placeholder="Coeficiente k1"
                              name="consumptionCoefficient1"
                              onChange={handleChange}
                              required
                            ></input>
                            <br></br>

                            <label htmlFor="consumptionCoefficient2">
                              <b>Coeficiente consumo maximo k2</b>
                            </label>
                            <br></br>
                            <input
                              type="text"
                              placeholder="Coeficiente k2"
                              name="consumptionCoefficient2"
                              onChange={handleChange}
                              required
                            ></input>

                            <ModalFooter>
                              <button type="submit" className="btn buttonsave">
                                Guardar
                              </button>
                              <button
                                type="reset"
                                className="btn buttoncancel"
                                onClick={handleCancel}
                              >
                                Atras
                              </button>
                            </ModalFooter>
                          </form>
                        </div>
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
                        {clearErrors()}
                      </div>
                    </div>
                  </div>
                )}
              </Modal>
            </div>
          </div>
        </div>
      </div>

      <style jsx>{`
        p {
          align-items: center;
          text-align: center;
          justify-content: center;
        }
        h5 {
          text-align: center;
          color: #89bbfe;
          width: 100%;
        }
        input {
          border-radius: 30px;
          text-align: center;
          border: 1px solid #89bbfe;
          margin-bottom: 2px;
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

export default EndowmentForm;
