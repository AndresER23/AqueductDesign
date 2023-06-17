import Link from "next/link";
import AppLayout from "../components/AppLayout";

const HomePage = () => {
  return (<>
    <AppLayout>
      <div>
        <h2>¡Bienvenido!</h2>
        <section>
          <p>Este aplicativo web es una herramienta destinada al prediseño o diseño de bajo nivel de componentes de una planta de tratamiento de agua potable, tales como: Bocatoma de fondo, canal de aducción y desarenador. </p>
        </section>
        <Link href={'/designs'}>
          <button>Empezar a diseñar</button>
        </Link>
      </div>
    </AppLayout>
    <style jsx>{`
      div{
        display: flex;
        justify-content:center;
        height:80vh;
        width:100%;
        align-items:center;
        flex-direction: column;
        margin: 10px;

      }
      section{
        width: 700px;
        text-align: center;
      }
      button {
          border: none;
          padding: 10px 15px 10px 15px;
          border-radius: 40px;
          margin: 0 auto;
          background: #615D6C;
          color: white;
          margin-bottom:30px;
        }
      `}</style>
  </>);
}
 
export default HomePage;