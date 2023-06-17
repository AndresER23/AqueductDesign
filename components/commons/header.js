import Image from "next/image";
import { colors } from "../../styles/theme";
import Link from "next/link";
const Header = ({ children, src }) => {
  return (
    <>
      <nav className="navbar navbar-light navbar">
        <Image
          src={src}
          width="70"
          height="50"
          className="d-inline-block align-top image"
          alt={children}
        />
        <Link className="navbar-brand" href="/">
            <p>{children}</p>
        </Link>
      </nav>
      <style jsx>{`
        nav {
          text-align: center;
          width: 100%;
          color: #fff;
          display: flex;
          align-items: center;
          justify-content: center;
        }
        p {
          color: ${colors.white};
          font-size: 25px;
          weight: 00px;
          margin-bottom: 0px;
        }
        p {
          margin-bottom: 0px;
        }
        .navbar {
          background: #615d6c;
        }
      `}</style>
    </>
  );
};

export default Header;
