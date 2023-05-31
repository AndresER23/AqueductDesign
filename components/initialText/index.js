import { colors } from "../../styles/theme";

const InitialText = ({handleAqueductProp}) => {
    return (
        <>
        <p>
          Diseña un nuevo <b>Acueducto</b>
        </p>
        <div className="container">
          <div className="row">
            <button onClick={handleAqueductProp} className="btn button">
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
          background: #615D6C;
          color: white;
          margin-bottom:30px;
        }
        button:hover{
          background:#5E5B64;
          color:#DDE7F3;
          border: 1px solid #CAE5FF;
        }
      `}</style>
      </>
    );
}
 
export default InitialText;