import { useState } from "react";
import Head from "next/head";
import Image from "next/image";
import { useRouter } from "next/router";

import Header from "../components/commons/header";
import Footer from "../components/commons/footer";
import Card from "../components/card";
import ModalForm from "../components/modalForm";
import InitialText from "../components/initialText";
import { colors } from "../styles/theme";
import styles from "../styles/Home.module.css";
import ProjectionsForm from "../components/ProjectionsForm";
import EndowmentForm from "../components/EndowmentForm";
import BottomIntakeForm from "../components/BottomIntakeForm";
import SandTrapModal from "../components/SandTrapForm";
import SandTrapForm from "../components/SandTrapForm";
import AdductionChannelForm from "../components/adductionChannelForm";

export default function Home() {
  const router = useRouter();

  const [Aqueduct, setAqueduct] = useState(null);
  const [AqueductModal, setAqueductModal] = useState(false);

  const [Projection, setProjection] = useState(null);
  const [ProjectionModal, setProjectionModal] = useState(false);

  const [Endowment, setEndowment] = useState(null);
  const [EndowmentModal, setEndowmentModal] = useState(false);

  const [BottomIntake, setBottomIntake] = useState(null);
  const [BottomIntakeModal, setBottomIntakeModal] = useState(false);

  const [sandTrap, setSandTrap] = useState(null);
  const [sandTrapModal, setSandTrapModal] = useState(false);

  const [adductionChannel, setAdductionChannel] = useState(false);
  const [adductionChannelModal, setAdductionChannelModal] = useState(false);

  // Modal form handlers
  const handleAqueductProp = () => {
    setAqueductModal(true);
  };
  const handleProjectionProp = () => {
    setProjectionModal(true);
  };
  const handleEndowmentProp = () => {
    setEndowmentModal(true);
  };
  const handleBottomIntake = () => {
    setBottomIntakeModal(true);
  };
  const handleSandTrap = () => {
    setSandTrapModal(true);
  };
  const handleAdductionChannel = () => {
    setAdductionChannelModal(true);
  };

  const proyectionChildren = () => {
    window.localStorage.setItem(
      "proyectionChildren",
      JSON.stringify(Projection)
    );
    return (
      <div className="results">
        <p>
          Población proyectada al año {Projection.finalTime} :{" "}
          <b>{Projection.populationFinal}</b> habitantes.
        </p>
        <p>
          Tasa de crecimiento : <b>{Projection.growthRate}</b>
        </p>
        <p>
          Id de la proyección : <b>{Projection.idProyection}</b>
        </p>
      </div>
    );
  };
  const adductionChannelChildren = () => {
    window.localStorage.setItem(
      "adductionChannel",
      JSON.stringify(adductionChannel.result)
    );
    const {
      flowFullPipe,
      hydraulicRadius,
      inchesPipeDiameter,
      slope,
      velocityFullPipe,
    } = adductionChannel.result;
    //Endowment
    const averageDailyFlow = 0.032;
    let initalvaluForSearch = averageDailyFlow / flowFullPipe;
    let numberForSearch = (Math.round(initalvaluForSearch * 100) / 100).toFixed(
      2
    );

    let firstValue = numberForSearch.slice(2, 3);
    let secondValue = numberForSearch.slice(3, 4);

    const json = require("../public/hydraulicRelations.json");
    let data = json["Data"]["0"][firstValue][secondValue];
    let vVo = (data["V/Vo"] / 1000).toFixed(3);
    let dD = (data["d/D"] / 1000).toFixed(3);
    let rRo = (data["R/Ro"] / 1000).toFixed(3);

    let desingVelocity = (velocityFullPipe * vVo).toFixed(2);
    let sheetOfWater = (dD * (inchesPipeDiameter / 39.37)).toFixed(2);
    let designHydraulicRadius = (rRo * hydraulicRadius).toFixed(2);

    let dragForce = 9810 * designHydraulicRadius * slope;
    return (
      <div>
        <p>{numberForSearch}</p>
        <p>
          Velocidad de diseño : <b>{desingVelocity} m/s</b>
        </p>
        <p>
          Altura de la lamina de agua : <b>{sheetOfWater} m</b>
        </p>
        <p>
          Radio hidraulico al caudal de diseño: <b>{designHydraulicRadius} m</b>
        </p>
        <p>
          Pulgadas de la tuberia: <b>{inchesPipeDiameter}</b>
        </p>
      </div>
    );
  };
  const endowmentChildren = () => {
    window.localStorage.setItem("endowment", JSON.stringify(Endowment));
    return (
      <div className="results">
        <p>
          Caudal medio diario: <b>{Endowment.averageDailyFlow.toFixed(3)}</b>{" "}
          m3/s.
        </p>
        <p>
          Caudal máximo diario : <b>{Endowment.maximumDailyFlow.toFixed(3)}</b>{" "}
          m3/s.
        </p>
        <p>
          Caudal máximo horario :{" "}
          <b>{Endowment.maximumHourlyFlow.toFixed(3)}</b> m3/s.
        </p>
      </div>
    );
  };
  const bottomIntakeChildren = () => {
    window.localStorage.setItem(
      "bottomIntake",
      JSON.stringify(BottomIntake.result)
    );
    const {
      adductionCanalWidth,
      channelLength,
      LengthOfTheGrid,
      numberOfHoles,
      heigthWaterSheet,
    } = BottomIntake.result;

    return (
      <div className="results">
        <b>
          <h5>Canal</h5>
        </b>
        <p>
          Altura de la lamina de agua : <b>{heigthWaterSheet.toFixed(3)}</b> m.
        </p>
        <p>
          Ancho del canal de aducción <b>{adductionCanalWidth.toFixed(3)}</b>
        </p>
        <p>
          Longitud del canal : <b>{channelLength.toFixed(3)}</b>{" "}
        </p>

        <b>
          <h5>Rejilla</h5>
        </b>
        <p>
          Longitud de la rejilla <b>{LengthOfTheGrid.toFixed(3)}</b>{" "}
        </p>
        <p>
          Numero de orificios: <b>{numberOfHoles.toFixed(3)}</b>{" "}
        </p>
      </div>
    );
  };
  const sandTrapChildren = () => {
    window.localStorage.setItem("sandTrap", JSON.stringify(sandTrap.result));
    const {
      sedimentationVelocity,
      timeBottomTank,
      hydraulicRetention,
      volumeOftheSandTrap,
      surfaceArea,
      sandTrapwidth,
      sandTraplength,
      surfaceLoad,
      critiqueDiameter,
      horizontalVelocity,
      maxHorizontalVelocity,
      dragVelocity,
    } = sandTrap.result;
    return (
      <div className="results">
        <p>
          Volumen del desarenador: <b>{volumeOftheSandTrap.toFixed(2)} m³</b>
        </p>
        <p>
          Dimensiones del desarenador:{" "}
          <b>
            Largo: {Math.round(sandTraplength)}m, Ancho{" "}
            {Math.round(sandTrapwidth)}m
          </b>
        </p>
        <p>
          Retención hidraulica: <b>{hydraulicRetention.toFixed(2)}h</b>
        </p>
      </div>
    );
  };

  return (
    <div className="">
      {/* Header */}

      <Head>
        <title>Diseñador de Acueductos</title>
        <meta name="description" content="Generated by create next app" />
        <link rel="icon" href="/favicon.ico" />
      </Head>

      {/* Main */}
      <main className={styles.main}>
        <Header src={"/logo.png"}>Diseño de acueductos</Header>

        {/* Conditional render */}
        {!Aqueduct ? (
          <InitialText handleAqueductProp={handleAqueductProp} />
        ) : (
          <p>
            Diseñemos <b>{Aqueduct.aqueductName}</b>
          </p>
        )}

        <div className="components-container">
          <div onClick={Aqueduct && !Projection ? handleProjectionProp : null}>
            {!Projection ? (
              <Card
                src={"/population.png"}
                title={"Proyecciones de poblacion"}
                dependency={Aqueduct}
              >
                Las proyecciones de población proporcionan una referencia del
                futuro tamaño y estructura de una población, basados en un
                conjunto de supuestos sobre el comportamiento de los componentes
                demográficos fecundidad, mortalidad y otros.
              </Card>
            ) : (
              <Card
                src={"/population.png"}
                title={"Proyecciones de poblacion"}
                result={proyectionChildren()}
              ></Card>
            )}
          </div>
          <div onClick={Projection && !Endowment ? handleEndowmentProp : null}>
            {!Endowment ? (
              <Card
                src={"/Drinking.png"}
                title={"Demandas de agua"}
                dependency={Projection}
              >
                La demanda de agua representa el volumen de agua expresado en
                metros cúbicos, que son utilizados por las actividades
                socioeconómicas de la subcuenca en un espacio y tiempo
                determinado y se compone por la sumatoria de las demandas de los
                sectores
              </Card>
            ) : (
              <Card
                src={"/Drinking.png"}
                title={"Demandas de agua"}
                result={endowmentChildren()}
              ></Card>
            )}
          </div>

          <div
            onClick={Projection && !BottomIntake ? handleBottomIntake : null}
          >
            {!BottomIntake ? (
              <Card
                src={"/modelBottomIntake.png"}
                title={"Captación de fondo"}
                dependency={Endowment}
              >
                Las obras de toma o bocatomas son las estructuras hidráulicas
                construidas sobre un río o canal con el objeto de captar, es
                decir extraer, una parte o la totalidad del caudal de la
                corriente principal.
              </Card>
            ) : (
              <Card
                src={"/modelBottomIntake.png"}
                title={"Captación de fondo"}
                result={bottomIntakeChildren()}
              />
            )}
          </div>
          <div
            onClick={
              BottomIntake && !adductionChannel ? handleAdductionChannel : null
            }
          >
            {!adductionChannel ? (
              <Card
                src={"/adductionChannel.png"}
                title={"Canal de aducción"}
                dependency={BottomIntake}
              >
                {" "}
                De acuerdo con lo establecido por el RAS Aducción es aquel
                componente a través del cual se transporta agua cruda, ya sea a
                flujo libre o presión.
              </Card>
            ) : (
              <Card
                src={"/adductionChannel.png"}
                title={"Canal de aducción"}
                result={adductionChannelChildren()}
              />
            )}
          </div>

          <div onClick={adductionChannel && !sandTrap ? handleSandTrap : null}>
            {!sandTrap ? (
              <Card
                src={"/RealSandTrap.png"}
                title={"Desarenador"}
                dependency={adductionChannel}
              >
                Los desarenadores son estructuras ubicadas a continuación de una
                captación de agua y que permiten remover partículas como arenas
                arcillas, gravas finas y material orgánico de cierto tamaño
                contenidas en el agua que ingresa de una fuente superficial
              </Card>
            ) : (
              <Card
                src={"/RealSandTrap.png"}
                title={"Desarenador"}
                result={sandTrapChildren()}
              />
            )}
          </div>
        </div>
        <div className="generate-report-div">
          {sandTrap && (
            <button className="btn btn-primary"
            onClick={() => router.push("/GenerateReport")}
            >Generar Reporte.</button>
          )}
        </div>
      </main>

      <Footer></Footer>

      {/*Conditionals Renders*/}
      {AqueductModal && (
        <ModalForm
          AqueductModal={AqueductModal}
          setAqueductModal={setAqueductModal}
          setAqueduct={setAqueduct}
        ></ModalForm>
      )}
      {ProjectionModal && (
        <ProjectionsForm
          setProjection={setProjection}
          ProjectionModal={ProjectionModal}
          setProjectionModal={setProjectionModal}
          propAqueductId={Aqueduct.idAqueduct}
        />
      )}
      {EndowmentModal && (
        <EndowmentForm
          EndowmentModal={EndowmentModal}
          setEndowment={setEndowment}
          setEndowmentModal={setEndowmentModal}
          propAqueductId={Aqueduct.idAqueduct}
          Projection={Projection}
        ></EndowmentForm>
      )}
      {BottomIntakeModal && (
        <BottomIntakeForm
          BottomIntakeModal={BottomIntakeModal}
          setBottomIntakeModal={setBottomIntakeModal}
          propAqueductId={Aqueduct.idAqueduct}
          setBottomIntake={setBottomIntake}
          designFlowProp={Endowment.averageDailyFlow}
        />
      )}
      {sandTrapModal && (
        <SandTrapForm
          SandTrapModal={SandTrapModal}
          setSandTrapModal={setSandTrapModal}
          propAqueductId={Aqueduct.idAqueduct}
          setSandTrap={setSandTrap}
          designFlowProp={Endowment.averageDailyFlow}
        />
      )}
      {adductionChannelModal && (
        <AdductionChannelForm
          setAdductionChannel={setAdductionChannel}
          adductionChannelModal={adductionChannelModal}
          propAqueductId={Aqueduct.idAqueduct}
          setAdductionChannelModal={setAdductionChannelModal}
        />
      )}

      {/* Styles */}
      <style jsx>
        {`
          p {
            text-align: center;
            margin-top: 30px;
            font-size: 19px;
          }
          p b {
            font-size: 20px;
            color: ${colors.primary};
          }
          .components-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-around;
          }
          .generate-report-div {
            display: flex;
            justify-content: center;
          }
        `}
      </style>
    </div>
  );
}
