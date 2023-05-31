import Image from "next/image";
import styles from "../../styles/card.module.css";

const Card = ({ src, title, children, dependency, result }) => {
  return (
    <>
      <div className={`${!dependency || result ? styles.card : styles.cardActive} card`}  style={{width:400, marginRight:10, marginLeft:10, marginBottom:20}}>
        <article>
        <Image
          alt={title}
          src={src}
          width={'399'}
          height={300}
          className="mx-0"
        />
        </article>
        
        <section className="card-body">
          <h5 className="card-title">{title}</h5>
          <p className="card-text">
            {children}
            {result && result}
          </p>
        </section>
      </div>

      <style jsx>{`
          h5{
            font-weight : 700;
            text-align: center;
            padding-bottom : 10px;
          }
          section{
            margin-top : ${!result && '40px'};
            border-top : 1px solid #eee;
            display:flex;
            flex-direction: column;
            justify-content : center;
            align-items : center;
          }
          article{
            width: 400px;
          }
          
      `}</style>
    </>
  );
};
export default Card;
