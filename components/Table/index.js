const Table = ({ obj, title }) => {
    let keys = [];
    let values = []
    for (let [key,  value] of Object.keys(obj)) {
        keys.push(key)
        values.push(value)
    }
  return (
    <>
    <h2 style={{fontSize:'50'}}>Hola</h2>
    </>
  );
};

export default Table;
