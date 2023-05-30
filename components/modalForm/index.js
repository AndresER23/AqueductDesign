import { Modal, ModalBody, ModalHeader, ModalFooter } from "reactstrap";
import { useState } from "react";
import { colors } from "../../styles/theme";

const initialForm = {
  aqueductName: "",
  idAqueduct: 0
};

const initialError = {
  message: "",
  status :"",
}
const ModalForm = ({ AqueductModal, setAqueductModal, setAqueduct }) => {
  const [form, setForm] = useState(initialForm);
  const [error, setError] = useState(initialError);
  const url = "http://localhost:8080/desing";

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault()

    if (form.aqueductName.length == 0) {
      setError({
        message: "El nombre del acueducto no puede estar vacio",
        status: true,
      })
      return
    }
    const requestInit = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form),
    };
    fetch(url, requestInit)
      .then((res) => res.json())
      .then((res) => {
        setAqueduct(res)
        window.localStorage.setItem("aqueductName",JSON.stringify(res))
      });
    setAqueductModal(false);
  };

  const handleCancel = () => {
    setAqueductModal(false);
  };
    
  const clearErrors = () => {
    const showError = setTimeout(() => {
      setError({
        message: false,
        status: false,
      });
      clearTimeout(showError)
    }, 5000);
  }

  return (
    <>
      <Modal isOpen={AqueductModal}>
        <ModalHeader className="modalheader">
          <div className="modalheader">
            <h5 className="modaltitle">¡Diseñemos un nuevo acueducto!</h5>
          </div>
        </ModalHeader>
        <ModalBody>
          <form onSubmit={handleSubmit}>
            <label htmlFor="aqueductName">Introduce el nombre del acueducto a diseñar.</label>
            <input
              type="text"
              placeholder="Nombre del acueducto"
              onChange={handleChange}
              name="aqueductName"
            />
            <ModalFooter>
              <input type="submit" value={"Seguir"} className="btn btn-send" />
              <input
                type="reset"
                value={"Cancelar"}
                onClick={handleCancel}
                className="btn btn-cancel"
              />
            </ModalFooter>
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
      <style jsx>{`
        .modalheader{
          text-align:center;
          align-items: center;
          width: 100%;
          
        }
        .modaltitle{
          width:100%;
        }
        h5{
          width:100%
        }
        form {
          align-items: center;
          text-align: center;
        }
        form input {
          margin: 10px;
        }
        .btn-send {
          background: ${colors.succes};
          color: ${colors.white};
        }
        .btn-cancel {
          background: ${colors.cancel};
          color: ${colors.white};
        }
      `}</style>
    </>
  );
};

export default ModalForm;
