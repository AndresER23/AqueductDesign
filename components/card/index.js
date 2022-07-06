import Image from "next/image";
import styles from "../../styles/card.module.css";
import { useState, useEffect} from "react";

const Card = ({ src, title, children, dependency }) => {
  return (
    <>
    <div className={`${ !dependency ? styles.card : styles.cardActive}`}>
      <div className="container">
        <div className="row">
          <div className="col">
            <div className={styles.cardheader}>
              <h2>{title}</h2>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col-md-9 py-2">
            <div className={styles.cardbody}>
              <Image alt={title} src={src} width={200} height={200}  className="cardimage w-100"/>
            </div>
          </div>
          <div className="col">
            <div className={styles.cardbody}>
              <p className="cardtext">{children}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    </>
  );
};
export default Card;
