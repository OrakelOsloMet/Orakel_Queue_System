import {FC, useEffect, useState} from "react";
import {useForm} from "react-hook-form";

import {DISCORD_URL, FACEBOOK_URL, FormElementType, PLACEMENTS_PATH, UNSELECTED} from "../../../constants/constants";
import {SubmitButton} from "../../UI/Buttons/buttons";
import {convertObjectStringsToPrimitives} from "../../../utilities/objectUtilities";
import {ISelectConfig, ITextConfig, IValidatedTextConfig} from "../../../models/inputModels"
import {configureRegister, inputHasError} from "../../../utilities/formUtilities";
import Input from "../Inputs/input";
import Select from "../Inputs/select";
import {IPlacement, IQueueEntity, ISubject} from "../../../models/types"
import SwalMessageModal from "../../UI/Modals/SwalModals/swalMessageModal";
import {arrayHasContent} from "../../../utilities/arrayUtilities";

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
    placements: Array<IPlacement>;
    loading: boolean;
    error: string | null;
    addQueueEntity: (queueEntity: IQueueEntity) => void;
    getPlacementData: () => void;
    getSubjectData: () => void;
}

const QueueForm: FC<Props> = (props) => {

    /* ----- Initialize State, Subcomponents and get Props ----- */
    const {subjects, placements, addQueueEntity, getPlacementData, getSubjectData} = props;
    const {register, handleSubmit, reset, formState: {isSubmitSuccessful, errors}} = useForm<FormValues>();
    const [nameInput] = useState<IValidatedTextConfig>({
        type: FormElementType.VALIDATED_TEXT,
        name: FormElements.FIRSTNAME,
        placeholder: "Fornavn...",
        validation: {
            minLength: 3,
            errorMessage: "Vennligst oppgi et fornavn på minst 3 bokstaver"
        }
    });
    const [subjectSelect, setSubjectSelect] = useState<ISelectConfig>({
        placeholderdisplayvalue: "Velg emne...",
        type: FormElementType.SELECT,
        name: FormElements.SUBJECT,
        options: []
    });
    const [placementSelect, setPlacementSelect] = useState<ISelectConfig>({
        placeholderdisplayvalue: "Velg plassering...",
        type: FormElementType.SELECT,
        name: FormElements.PLACEMENT,
        options: []
    });
    const [yearSelect] = useState<ISelectConfig>({
        placeholderdisplayvalue: "Velg studieår...",
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
        placeholder: "Kommentar, f.eks: \"Har på rød lue\"...",
    });

    //Use effect only to be triggered when the component is first rendered.
    useEffect(() => {
        getPlacementData();
        getSubjectData();
    }, [getPlacementData, getSubjectData])

    useEffect(() => {
        if (arrayHasContent(subjects)) {
            fillSubjectSelector();
        }

        if (arrayHasContent(placements)) {
            fillPlacementsSelector();
        }
    }, [subjects, placements])

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

        subjects.forEach(subject => {
            subjectListUpdated.options.push({value: subject.id, displayValue: subject.name});
        });

        setSubjectSelect(subjectListUpdated);
    }

    const fillPlacementsSelector = () => {
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

    const showErrorMessage = (errorMessage: string, title: string = "Noe har gått galt") =>
        SwalMessageModal({
            title: title,
            iconType: "error",
            contentText: "Vennligst informer Orakel via Facebook, Discord eller på Datatorget dersom problemet vedvarer.</br></br> " + "<b>Feilmelding:</b> " + errorMessage,
            hyperlinks: [{url: FACEBOOK_URL, text: "Orakel Facebook"}, {url: DISCORD_URL, text: "Orakel Discord"}]
        });

    const registrationHandler = (formData: FormValues) => {
        const primitiveFormData = convertObjectStringsToPrimitives(formData);
        const foundPlacement = placements.find(placement => placement.id === primitiveFormData.placement);
        const foundSubject = subjects.find(subject => subject.id === primitiveFormData.subject);
        const foundYear = primitiveFormData.year;

        if (typeof foundPlacement !== 'undefined' && typeof foundSubject !== 'undefined' && foundYear !== UNSELECTED) {
            const queueEntity: IQueueEntity = {
                id: 0, //Set in the API
                createdDate: "", //Set in the API
                name: primitiveFormData.firstname,
                subject: foundSubject,
                placement: foundPlacement,
                comment: primitiveFormData.comment,
                studyYear: primitiveFormData.year
            }

            addQueueEntity(queueEntity);
        } else {
            showErrorMessage("Husk å velge et alternativ for emne, plassering og studieår", "Skjema ikke fylt ut");
        }
    }

    /* ----- JSX Layout ----- */
    const form =
        <form onSubmit={handleSubmit(registrationHandler)} className={"mt-5 mb-5 d-flex justify-content-center"}>
            <div className={"d-flex justify-content-center"}>
                <div className={"row"}>
                    <div className={"col"}>
                <Input inputConfig={nameInput} error={inputHasError(errors, nameInput)} register={configureRegister(nameInput, register)}/>
                    </div>
                    <div className={"col"}>
                <Select inputConfig={subjectSelect} register={configureRegister(subjectSelect, register)}/>
                    </div>
                    <div className={"col"}>
                <Select inputConfig={placementSelect} register={configureRegister(placementSelect, register)}/>
                    </div>
                    <div className={"col"}>
                <Select inputConfig={yearSelect} register={configureRegister(yearSelect, register)}/>
                    </div>
                    <div className={"col"}>
                <Input inputConfig={commentInput} error={inputHasError(errors, commentInput)} register={configureRegister(commentInput, register)}/>
                    </div>
                <SubmitButton className={"mt-4"}>Registrer</SubmitButton>
                </div>
            </div>
        </form>

    return (
        <div className={"bg-light pb-1 pt-1"}>
            <h2 className={"mt-5 text-center fw-bold"}>Køregistrering</h2>
            {form}
        </div>
    );

}

export default QueueForm