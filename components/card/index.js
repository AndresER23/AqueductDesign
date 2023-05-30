import Image from "next/image";
import styles from "../../styles/card.module.css";
import { useState, useEffect } from "react";

const Card = ({ src, title, children, dependency, result }) => {
  return (
    <>
      <div className={`${!dependency || result ? styles.card : styles.cardActive} card`}  style={{width:400, marginRight:10, marginLeft:10, marginBottom:20}}>
        <Image
          alt={title}
          src={src}
          width={300}
          height={300}
          className="mx-0"
        />
        <div className="card-body">
          <h5 className="card-title">{title}</h5>
          <p className="card-text">
            {children}
            {result && result}
          </p>
        </div>
      </div>

      <style jsx>{`

      `}</style>
    </>
  );
};
export default Card;
