import { Modal, ModalBody, ModalHeader, ModalFooter } from "reactstrap";
import { useState } from "react";
import { colors } from "../../styles/theme";

const initialForm = {
  aqueductName: "",
  idAqueduct: 0
};
const ModalForm = ({ AqueductModal, setAqueductModal, setAqueduct }) => {
  const [form, setForm] = useState(initialForm);

  const url = "http://localhost:8080/desing";

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    const requestInit = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form),
    };
    fetch(url, requestInit)
      .then((res) => res.json())
      .then((res) => setAqueduct(res));
    setAqueductModal(false);
  };

  const handleCancel = () => {
    setAqueductModal(false);
  };

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
