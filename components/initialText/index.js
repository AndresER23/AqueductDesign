import { colors } from "../../styles/theme";

const InitialText = ({handleProp}) => {
    return (
        <>
        <p>
          Diseña un nuevo <b>Acueducto</b>
        </p>
        <div className="container">
          <div className="row">
            <button onClick={handleProp} className="btn btn-secondary">
              ¡Empezar!
            </button>
          </div>
        </div>
        <style jsx>{`
        p {
          text-align: center;
          margin-top: 30px;
          font-size: 19px;
        }
        p b {
          font-size: 20px;
          color: ${colors.primary};
        }
        button {
          border-radius: 40px;
          margin: 0 auto;
        }
      `}</style>
      </>
    );
}
 
export default InitialText;