import Image from "next/image";
import {colors} from "../../styles/theme"
const Header = ({ children, src}) => {
  return (
    <>
      <nav className="navbar navbar-light bg-secondary">
        <a className="navbar-brand" href="#">
          <Image
            src={src}
            width="70"
            height="50"
            className="d-inline-block align-top"
            alt={children}
          />
          <p>
          {children}
          </p>
        </a>
      </nav>
      <style jsx>{`
        a {
          text-align:center;
          width: 100%;
          color: #fff;
        }
        a p {
          color:${colors.white}
        }
        `}</style>

    </>
  );
};

export default Header;
