import { Page, Text, View, Document, StyleSheet} from "@react-pdf/renderer";
const PDF = ({ aqueduct, projections, endowment, bottomIntake, sandTrap}) => {
  const styles = StyleSheet.create({
    page: {
      backgroundColor: "#E4E4E4",
    },
    section: {
      margin: 10,
      padding: 10,
      flexGrow: 1,
    },
    title: {
      width: "100%",
      marginTop: 10,
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      height: 70,
      fontSize: 25
    },
    row: {
        borderBottom: "1px solid black",
        borderTop: "1px solid black",
        marginTop: 10,
        marginRight :20,
        marginBottom: 10,
        marginLeft :20,
    },
    key : {
      borderLeft : "1px solid black",
    },
    data :{
      marginLeft: 10,
      paddingTop: 5,
      paddingBottom: 5,
    },
    rowTitle : {
      width: "100%",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      height: 35,
      fontSize: 20,
      borderBottom: '1px solid black',
      backgroundColor: "#615d6c",
      color: "#fff",
    },
    rowData : {
      width : '100%',
      display: 'flex',
      flexDirection: 'row',
      alignItems: 'center',
      justifyContent: 'center',
    },
    results: {
      color: '#8ECA6C',
    },
    suppliedDataColumn : {
      borderRight : '1px solid black',
      borderLeft : '1px solid black',
      width: '50%',
      height: '100%',
    },
    subtitles : {
      borderBottom: '1px solid black',
      textAlign: 'center',
      height: '20px',
      fontSize: 13,
    },
    resultsColumn : {
      borderRight : '1px solid black',
      width: '50%',
      height: '100%',
      display: 'flex',
    }
    
  });

  return (
    <>
      <Document title={aqueduct.aqueductName} >
        <Page size="A4" style={styles.page}>
          <View style={styles.title}>
            <Text style={{fontSize:'20px', fontWeight: 400}}>Diseño del aqueducto {aqueduct.aqueductName}</Text>
          </View>

          <View style={styles.row}>
            <View style={styles.rowTitle}>
              <Text style={styles.data}>Proyección de poblacion</Text>
            </View>
            <View style={styles.rowData}>
              <Text style={styles.data}>Tasa de crecimiento : <Text style={styles.results}>{projections.growthRate}</Text>.</Text>
              <Text style={styles.data}>Población al año {projections.finalTime} : <Text style={styles.results}>{projections.populationFinal}</Text> habitantes.</Text>
            </View>
          </View>

          <View style={styles.row}>
            <View style={styles.rowTitle}>
              <Text style={styles.data}>Demandas de agua</Text>
            </View>
            <View style={styles.rowData}>
              <View style={styles.suppliedDataColumn}>
                <Text style={styles.subtitles}>Datos suministrados</Text>
                <Text style={styles.data}>Demanda neta : <Text style={styles.results}>{endowment.netEndowment}</Text>.</Text>
                <Text style={styles.data}>Perdidas de agua : <Text style={styles.results}>{endowment.waterLosses}% L/HAB*DIA</Text>.</Text>
                <Text style={styles.data}>Coeficiente de consumo k1 : <Text style={styles.results}>{endowment.consumptionCoefficient1}</Text>.</Text>
                <Text style={styles.data}>Coeficiente de consumo k2 : <Text style={styles.results}>{endowment.consumptionCoefficient2}</Text>.</Text>
              </View>
              <View style={styles.resultsColumn}>
                <Text style={styles.subtitles}>Resultados</Text>
                <Text style={styles.data}>Demanda bruta : <Text style={styles.results}>{endowment.totalGrossEndowment.toFixed(3)}</Text>.</Text>
                <Text style={styles.data}>Caudal máximo diario : <Text style={styles.results}>{endowment.maximumDailyFlow.toFixed(3)}</Text>.</Text>
                <Text style={styles.data}>Caudal medio diario : <Text style={styles.results}>{endowment.maximumHourlyFlow.toFixed(3)}</Text>.</Text>
                <Text style={styles.data}>Caudal máximo horario : <Text style={styles.results}>{endowment.averageDailyFlow.toFixed(3)}</Text>.</Text>
              </View>
            </View>
          </View>

          <View style={styles.row}>
            <View style={styles.rowTitle}>
              <Text style={styles.data}>Bocatoma de fondo</Text>
            </View>
            <View style={styles.rowData}>
              <View style={styles.resultsColumn}>
                  <Text style={styles.subtitles}>Canal</Text>
                  <Text style={styles.data}>Ancho del canal de aducción : <Text style={styles.results}>{bottomIntake.adductionCanalWidth}</Text>.</Text>
                  <Text style={styles.data}>Ancho del canal de aducción : <Text style={styles.results}>{bottomIntake.adductionCanalWidth}</Text>.</Text>
              </View>
              <View style={styles.resultsColumn}>
                <Text style={styles.subtitles}>Rejilla</Text>
                  <Text style={styles.data}>Longitud de la rejilla : <Text style={styles.results}>{bottomIntake.LengthOfTheGrid.toFixed(3)}</Text>.</Text>
                  <Text style={styles.data}>Numero de agujeros : <Text style={styles.results}>{bottomIntake.numberOfHoles.toFixed(3)}</Text>.</Text>
                  <Text style={styles.data}>Velocidad entre barrotes : <Text style={styles.results}>{bottomIntake.speedBetweenBars.toFixed(3)}</Text>.</Text>
                  <Text style={styles.data}>Velocidad sobre la presa : <Text style={styles.results}>{bottomIntake.speedOverDam.toFixed(3)}</Text>.</Text>
              </View>
            </View>
          </View>


          <View style={styles.row}>
            <View style={styles.rowTitle}>
              <Text style={styles.data}>Desarenador</Text>
            </View>
            <View style={styles.rowData}>
              <View style={styles.resultsColumn}>
                  <Text style={styles.subtitles}>Dimensiones</Text>
                  <Text style={styles.data}>Volumen : <Text style={styles.results}>{sandTrap.volumeOftheSandTrap.toFixed(3)} m³</Text>.</Text>
                  <Text style={styles.data}>Ancho del desarenador : <Text style={styles.results}>{sandTrap.sandTrapwidth.toFixed(3)} m³</Text>.</Text>
                  <Text style={styles.data}>Longitud del desarenador : <Text style={styles.results}>{sandTrap.sandTraplength.toFixed(3)} m³</Text>.</Text>
                  <Text style={styles.data}>Área de superficie : <Text style={styles.results}>{sandTrap.surfaceArea.toFixed(3)} m³</Text>.</Text>
                  <Text style={styles.data}>Carga superficial : <Text style={styles.results}>{sandTrap.surfaceLoad.toFixed(3)} m³</Text>.</Text>
              </View>
              <View style={styles.resultsColumn}>
                <Text style={styles.subtitles}>Especificaciones</Text>
                <Text style={styles.data}>Velocidad de sedimentación : <Text style={styles.results}>{sandTrap.sedimentationVelocity.toFixed(3) }</Text>.</Text>
                  <Text style={styles.data}>Retención hidraulica : <Text style={styles.results}>{sandTrap.hydraulicRetention.toFixed(3) }</Text>.</Text>
                  <Text style={styles.data}>Velocidad de arrastre : <Text style={styles.results}>{sandTrap.dragVelocity.toFixed(3) }</Text>.</Text>
                  <Text style={styles.data}>Diametro crítico : <Text style={styles.results}>{sandTrap.critiqueDiameter.toFixed(3) }</Text>.</Text>
                  <Text style={styles.data}>Máxima velocidad horizontal : <Text style={styles.results}>{sandTrap.maxHorizontalVelocity.toFixed(3)} m³</Text>.</Text>
              </View>
            </View>
          </View>

        </Page>
      </Document>
    </>
  );
};

export default PDF;
