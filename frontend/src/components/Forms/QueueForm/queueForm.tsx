import React, {FC, useEffect, useState} from "react";
import {useForm} from "react-hook-form";

import {FormElementType, PLACEMENTS_PATH} from "../../../constants/constants";
import {SubmitButton} from "../../UI/Buttons/buttons";
import {convertObjectStringsToPrimitives} from "../../../utilities/objectUtilities";
import {ISelectConfig, ITextConfig, IValidatedTextConfig} from "../../../models/inputModels";
import {createUseFormRef, inputHasError} from "../../../utilities/formUtilities";
import Input from "../Inputs/input";
import Select from "../Inputs/select";
import {IPlacement, IQueueEntity, ISubject} from "../../../models/types";
import {REST_INSTANCE as axios} from "../../../axiosAPI"
import SwalMessageModal from "../../UI/Modals/SwalModals/swalMessageModal";

enum FormElements {
    FIRSTNAME = "firstname",
    SUBJECT = "subject",
    PLACEMENT = "placement",
    YEAR = "year",
    COMMENT = "comment",
}

type FormValues = {
    firstname: string,
    placement: number,
    subject: number,
    year: string,
    digital: string
}

type Props = {
    subjects: Array<ISubject>;
    loading: boolean;
    error: string | null;
    addQueueEntity: (queueEntity: IQueueEntity) => void;
}

const QueueForm: FC<Props> = (props) => {

    /* ----- Initialize State, Subcomponents and get Props ----- */

    const {subjects, addQueueEntity} = props;
    const {register, handleSubmit, reset, errors, formState: {isSubmitSuccessful}} = useForm();

    const [placements, setPlacements] = useState<IPlacement[]>([]);

    const [nameInput] = useState<IValidatedTextConfig>({
        type: FormElementType.VALIDATED_TEXT,
        name: FormElements.FIRSTNAME,
        placeholder: "Fornavn",
        validation: {
            minLength: 3,
            errorMessage: "Vennligst oppgi et fornavn på minst 3 bokstaver"
        }
    });

    const [subjectSelect, setSubjectSelect] = useState<ISelectConfig>({
        type: FormElementType.SELECT,
        name: FormElements.SUBJECT,
        options: []
    });

    const [placementSelect, setPlacementSelect] = useState<ISelectConfig>({
        type: FormElementType.SELECT,
        name: FormElements.PLACEMENT,
        options: []
    });

    const [yearSelect] = useState<ISelectConfig>({
        type: FormElementType.SELECT,
        name: FormElements.YEAR,
        options: [
            {value: 1, displayValue: "1. år"},
            {value: 2, displayValue: "2. år"},
            {value: 3, displayValue: "3. år"}
        ]
    });

    const [commentInput] = useState<ITextConfig>({
        type: FormElementType.TEXT,
        name: FormElements.COMMENT,
        placeholder: "\"Har på rød lue\"",
    });

    //Use effect only to be triggered when the component is first rendered.
    useEffect(() => {
        if (typeof subjects !== 'undefined' && subjects.length > 0) {
            fillSubjectSelector();
        }

        getPlacementData().then((placementData) => {
            setPlacements(placementData);
            fillPlacementsSelector(placements);
        });

    }, [subjects])

    /* ----- Helper Functions ----- */

    //Use effect to run whenever the form is submitted successfully.
    useEffect(() => {
        if (isSubmitSuccessful) {
            reset();
        }
    }, [isSubmitSuccessful, reset])

    const fillSubjectSelector = () => {
        const subjectListUpdated = {...subjectSelect};
        subjectListUpdated.options = [];

        subjects?.forEach(subject => {
            subjectListUpdated.options.push({value: subject.id, displayValue: subject.name});
        });

        setSubjectSelect(subjectListUpdated);
    };

    const getPlacementData = (): Promise<IPlacement[]> => {
        return axios.get(PLACEMENTS_PATH)
            .then(response => {
                return response.data;
            });
    }

    const fillPlacementsSelector = (placements: IPlacement[]) => {
        const placementListUpdated = {...placementSelect};
        placementListUpdated.options = [];

        placements.forEach(placement => {
            const displayName = placement.name === "Discord" ? "Discord" : placement.name + " " + placement.number;

            placementListUpdated.options.push({
                value: placement.id,
                displayValue: displayName
            })
        });

        setPlacementSelect(placementListUpdated);
    }

    const showErrorMessage = (errorMessage: string) =>
        SwalMessageModal({
            title: "Noe har gått galt",
            iconType: "error",
            contentText: "Vennligst informer Orakel Koordinator via Facebook, Discord eller på Datatorget.</br></br> " + "<b>Feilmelding:</b> " + errorMessage,
            hyperlinks: [{url: "https://www.facebook.com/OrakelOsloMet", text: "Orakels Facebookside"}]
        });

    //TODO This is a temporary fix, remove once Placement-loading is fixed
    const showPlacementErrorMessage = () =>
        SwalMessageModal({
            title: "Innlastning av skjemaverdier feilet",
            iconType: "error",
            contentText: "Det er for øyeblikket problemer med innlastning av verdier til skjemaet, <b>refresh siden</b> og se om plasserings- og emnealternativer vises i dropdown-boksene. </br></br> Hvis dette ikke hjelper, vennligst informer Orakel Koordinator via Facebook, Discord eller på Datatorget.",
            hyperlinks: [{url: "https://www.facebook.com/OrakelOsloMet", text: "Orakels Facebookside"}]
        });

    const registrationHandler = (formData: FormValues) => {
        const primitiveFormData = convertObjectStringsToPrimitives(formData);
        const foundPlacement = placements.find(placement => placement.id === primitiveFormData.placement);
        const foundSubject = subjects.find(subject => subject.id === primitiveFormData.subject);

        if (typeof foundPlacement !== 'undefined' && typeof foundSubject !== 'undefined') {
            const queueEntity: IQueueEntity = {
                id: 0, //Set in the API
                createdDate: "", //Set in the API
                name: primitiveFormData.firstname,
                subject: foundSubject,
                placement: foundPlacement,
                comment: primitiveFormData.comment,
                studyYear: primitiveFormData.year
            };

            addQueueEntity(queueEntity);
        } else {

            //TODO revert back to commented out error message when data-loading is fixed
            //showErrorMessage("Error when creating QueueEntity: Placement or Subject is undefined");
            showPlacementErrorMessage();
        }
    };

    /* ----- JSX Layout ----- */
    const form =
        <form onSubmit={handleSubmit(registrationHandler)} className={"mt-5 mb-5"}>
            <label className={"text-center"}>Navn, Emne, Plassering, Studieår og Kommentar</label>
            <div className={"form-group form-inline d-flex justify-content-center"}>
                <Input inputConfig={nameInput} error={inputHasError(errors, nameInput)}
                       ref={createUseFormRef(nameInput, register)}/>
                <Select inputConfig={subjectSelect} ref={createUseFormRef(subjectSelect, register)}/>
                <Select inputConfig={placementSelect} ref={createUseFormRef(placementSelect, register)}/>
                <Select inputConfig={yearSelect} ref={createUseFormRef(yearSelect, register)}/>
                <Input inputConfig={commentInput} error={inputHasError(errors, commentInput)}
                       ref={createUseFormRef(commentInput, register)}/>
                <SubmitButton className={"ml-2 mr-2"}>Registrer</SubmitButton>
            </div>


        </form>

    return (
        <div className={"bg-light pb-1 pt-1"}>
            <h2 className={"mt-5 text-center font-weight-bold"}>Køregistrering</h2>
            {form}
        </div>
    );

}

export default QueueForm