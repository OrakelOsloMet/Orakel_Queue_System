import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import {IHyperlink} from "../../../../models/types";

type Props = {
    title: string,
    iconType: "info" | "error" | "warning"
    contentText: string,
    hyperlinks?: IHyperlink[]
}

const SwalMessageModal = (props: Props) => {
    const {title, contentText, hyperlinks, iconType} = props;
    const mySwal = withReactContent(Swal)
    const hyperlinkDiv: HTMLDivElement = document.createElement("div");

    if (hyperlinks) {
        hyperlinkDiv.className += "text-center"
        const strongText = document.createElement("strong");

        hyperlinks.forEach(hyperlink => {
            strongText.innerHTML += `<a href="${hyperlink.url}" target="_blank">${hyperlink.text}</a></br>`
        })

        hyperlinkDiv.append(strongText);
    }

    return mySwal.fire({
            title: title,
            html: contentText,
            footer: hyperlinks ? hyperlinkDiv : null,
            icon: iconType,
            confirmButtonText: "Lukk"
    });
};

export default SwalMessageModal;