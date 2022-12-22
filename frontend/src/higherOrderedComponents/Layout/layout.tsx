import {FC, ReactNode} from 'react';
import NavbarConnected from "../../components/Navigation/Navbar/navbarConnected";

type Props = {
    children: ReactNode
}

const Layout: FC<Props> = (props) => {
    return (
        <>
            <NavbarConnected/>
            <main className="mb-5" style={{width: "100%"}}>
                {props.children}
            </main>
        </>
    );
}

export default Layout;