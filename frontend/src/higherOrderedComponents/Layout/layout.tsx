import React, {FC} from 'react';
import NavbarConnected from "../../components/Navigation/Navbar/navbarConnected";

const Layout: FC = (props) => {
    return (
        <>
            <NavbarConnected/>
            <main className="mb-5">
                {props.children}
            </main>
        </>
    );
}

export default Layout;