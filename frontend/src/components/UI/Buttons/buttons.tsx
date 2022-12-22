import {FC, MouseEventHandler, ReactNode} from 'react';

type Props = {
    onClick?: MouseEventHandler,
    className?: string,
    style?: object,
    disabled?: boolean,
    children: ReactNode
}

export const SubmitButton: FC<Props> = (props) =>
    <button
        style={props.style}
        type="submit"
        disabled={props.disabled}
        className={`btn btn-primary ${props.className}`}
        onClick={props.onClick}>{props.children}
    </button>

export const CancelButton: FC<Props> = (props) => (
    <button
        style={props.style}
        disabled={props.disabled}
        className={`btn btn-danger ${props.className}`}
        onClick={props.onClick}>{props.children}
    </button>
);

export const ConfirmButton: FC<Props> = (props) => (
    <button
        style={props.style}
        disabled={props.disabled}
        className={`btn btn-success ${props.className}`}
        onClick={props.onClick}>{props.children}
    </button>
);

export const DeleteButton: FC<Props> = (props) => (
    <button
        style={props.style}
        disabled={props.disabled}
        className={`btn btn-danger ${props.className}`}
        onClick={props.onClick}>{props.children}
    </button>
);