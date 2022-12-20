import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";

type Props = {
    title: string,
    contentText?: string,
}

const SwalConfirmModal = (props: Props) => {
    const {title, contentText} = props;
    const mySwal = withReactContent(Swal)

    return mySwal.fire({
        title: title,
        html: contentText,
        icon: "warning",
        showDenyButton: true,
        confirmButtonText: "Confirm",
        denyButtonText: "Cancel",

    }).then((result => {
        if (result.isConfirmed) {
            mySwal.fire("Done!", "", "success");
            return true;
        } else if (result.isDenied) {
            mySwal.fire("Aborted", "", "info");
            return false;
        }
    }));
};

export default SwalConfirmModal