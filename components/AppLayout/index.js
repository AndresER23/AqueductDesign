import Header from "../commons/header";

const AppLayout = ({children}) => {
  return (
    <main>
      <div>
      <Header src={"/logo.png"}>Diseño de acueductos</Header>
      {children}
      </div>
      <style jsx>{`
        div:{
          height: 100vh;
          width: 100vh;
        }
        `}</style>
    </main>
  );
}
 
export default AppLayout;