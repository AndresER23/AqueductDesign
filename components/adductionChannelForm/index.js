import { Modal, ModalHeader, ModalFooter, ModalBody } from "reactstrap";
import { useState, useEffect } from "react";
import Image from "next/image";

const initialForm = {
  adductionLength: 0,
  roughnessCoefficient: 0,
  upperBound: 0,
  lowerBound: 0,
  aqueductId: 0,
};

const initialError = {
  message: "",
  status: false,
};

const AdductionChannelForm = ({
  setAdductionChannel,
  adductionChannelModal,
  propAqueductId,
  setAdductionChannelModal,
}) => {
  const [localform, setLocalform] = useState(initialForm);
  const [error, setError] = useState(initialError);

  useEffect(() => {
    setLocalform({
      ...localform,
      aqueductId: propAqueductId,
    });
  }, []);

  const handleChange = (e) => {
    setLocalform({
      ...localform,
      [e.target.name]: e.target.value,
    });
  };
  const clearErrors = () => {
    const showError = setTimeout(() => {
      setError({
        message: false,
      });
    }, 5000);

    clearTimeout(showError);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const url = "http://localhost:8080/AdductionChannel";
    //Setting the request head.
    const requestInit = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(localform),
    };

    fetch(url, requestInit)
      .then((res) => res.json())
      .then((res) => {
        console.log(res)
        setAdductionChannel(res)
      });

      setAdductionChannelModal(false);
  };

  const handleCancel = () => {
    setAdductionChannelModal(false);
  };

  return (
    <>
      <div className="container">
        <div className="row">
          <div className="col">
            <Modal isOpen={adductionChannelModal}>
              <ModalHeader>
                <div className="container">
                  <div className="row">
                    <div className="col">
                      <h5>Diseño del canal de aduccion.</h5>
                    </div>
                  </div>
                </div>
              </ModalHeader>
              <ModalBody>
                <div className="container d-flex justify-content-center">
                  <div className="row">
                    <div className="col md-9 ">
                      <label htmlFor="adductionLength">Longitud del tramo de aducción:</label>
                      <br></br>
                      <input
                        type="text"
                        id="adductionLength"
                        name="adductionLength"
                        onChange={handleChange}
                        placeholder="En metros"
                      />
                      <br></br>
                      <label htmlFor="roughnessCoefficient">Coeficiente de rugosidad:</label>
                      <br></br>
                      <input
                        type="text"
                        id="roughnessCoefficient"
                        name="roughnessCoefficient"
                        onChange={handleChange}
                      />
                      <br></br>
                      <label htmlFor="upperBound">Limite superior:</label>
                      <br></br>
                      <input
                        type="text"
                        id="upperBound"
                        name="upperBound"
                        onChange={handleChange}
                        placeholder="En metros"
                      />
                      <br></br>
                      <label htmlFor="lowerBound">Limite inferior:</label>
                      <br></br>
                      <input
                        type="text"
                        id="lowerBound"
                        name="lowerBound"
                        onChange={handleChange}
                        placeholder="En metros"
                      />
                    </div>
                  </div>
                </div>
              </ModalBody>
              <ModalFooter>
                <form onSubmit={handleSubmit}>
                  <button className="btn buttonsave">Seguir</button>
                </form>
                <button className="btn buttoncancel" onClick={handleCancel}>
                  Regresar
                </button>
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
export default AdductionChannelForm;
