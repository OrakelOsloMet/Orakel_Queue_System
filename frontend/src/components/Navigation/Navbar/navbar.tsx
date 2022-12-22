import {FC} from "react";
import {Navbar as BootsrapNav, NavbarProps as BootstrapNavProps} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";
import Nav from "react-bootstrap/Nav";
import styles from "./navbar.module.css";
import {ADMIN_ROUTE, INDEX_ROUTE, USER_GUIDE_PATH} from "../../../constants/constants";
import SwalMessageModal from "../../UI/Modals/SwalModals/swalMessageModal";
import SwalLoginModal from "../../UI/Modals/SwalModals/swalLoginModal";
import {AuthDispatch} from "../../../store/types";
import osloMetSvartImage from "../../../assets/images/oslometsvart.png";
import osloMetHvitImage from "../../../assets/images/oslomethvit.png"

type Props = {
    onLoginSubmit: (username: string, password: string) => (dispatch: AuthDispatch) => Promise<boolean | undefined>;
    clearLoginError: () => void;
    logoutHandler: () => void;
    isAuthenticated: boolean;
}

const Navbar: FC<Props> = (props) => {

    const showDiscordMessage = () =>
        SwalMessageModal({
            title: "Discord",
            iconType: "info",
            contentText: "For digital veiledning benytter vi oss av Discord, og har vår egen server til dette. Inne på serveren setter vi pris på om du leser informasjonen i tekstkanalen kalt informasjon, og vi anbefaler alle å laste ned Discord klienten i stedet for å bruke tjenesten gjennom nettleseren.",
            hyperlinks: [{url: "https://discord.gg/jgzqYpX", text: "Orakel Discord"}]
        })

    const showErrorReportingMessage = () =>
        SwalMessageModal({
            title: "Feilrapportering",
            iconType: "info",
            contentText: "Orakels køsystem er et fritidsprosjekt som er utviklet og vedlikeholdes av en person, det er derfor en viss fare for bugs og feil. </br> </br> Problemer rapporteres til Orakeltjenesten på Facebook eller Discord, eller direkte til utvikler Fredrik Pedersen. Vi setter også pris på ønsker om tilleggsfunksjonalitet :)",
            hyperlinks: [{url: "https://www.facebook.com/OrakelOsloMet", text: "Orakels Facebookside"}, {url: "https://github.com/FredrikPedersen", text: "Fredrik Pedersen Github"}]
        })

    const showAboutMessage = () =>
        SwalMessageModal({
            title: "Om Orakels Køsystem",
            iconType: "info",
            contentText: "Coming Soon!",
            hyperlinks: [{url: USER_GUIDE_PATH, text: "Brukerveiledning"}]
        })

    const swalLogin = () => {
        SwalLoginModal({onLoginSubmit: props.onLoginSubmit, clearLoginError: props.clearLoginError})
    }

    const linkStyle = props.isAuthenticated ? styles.authenticatedLinkText : styles.defaultLinkText;
    const navbarProps: BootstrapNavProps = props.isAuthenticated ? {expand: "lg", bg: "warning"} : {
        variant: "dark",
        expand: "lg",
        bg: "primary"
    };

    const loginButton =
        <Nav.Link
            className={linkStyle}
            onClick={props.isAuthenticated ? props.logoutHandler : swalLogin}>
            {props.isAuthenticated ? "Logg Ut" : "Logg Inn"}
        </Nav.Link>;


    return (
        <BootsrapNav {...navbarProps}>
            <BootsrapNav.Brand className={styles.invisibleOnMobile}>
                <img
                    className={styles.brandImage}
                    alt="OsloMet Logo"
                    src={props.isAuthenticated ? osloMetSvartImage : osloMetHvitImage}
                />
            </BootsrapNav.Brand>
            <LinkContainer to={INDEX_ROUTE}>
                <Nav.Link>
                    <BootsrapNav.Brand
                        className={props.isAuthenticated ? styles.authenticatedBrandText : styles.brandText}>Orakel
                    </BootsrapNav.Brand>
                </Nav.Link>
            </LinkContainer>
            <BootsrapNav.Toggle aria-controls="responsive-navbar-nav"/>
            <BootsrapNav.Collapse id="responsive-navbar-nav">
                <Nav>
                    <Nav.Link className={linkStyle} onClick={showDiscordMessage}>Discord</Nav.Link>
                    <Nav.Link className={linkStyle} onClick={showErrorReportingMessage}>Feilrapportering</Nav.Link>
                    <Nav.Link className={linkStyle} onClick={showAboutMessage}>Om</Nav.Link>
                    {props.isAuthenticated ? <LinkContainer to={ADMIN_ROUTE}><Nav.Link className={linkStyle}>Admin</Nav.Link></LinkContainer> : null}
                    {loginButton}
                </Nav>
            </BootsrapNav.Collapse>
        </BootsrapNav>
    );
};

export default Navbar;