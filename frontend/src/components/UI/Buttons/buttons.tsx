import React, {MouseEventHandler, FunctionComponent} from 'react';

type Props = {
    onClick?: MouseEventHandler,
    className?: string,
    disabled?: boolean,
}

export const SubmitButton: FunctionComponent<Props> = (props) =>
    <button
        type="submit"
        disabled={props.disabled}
        className={`btn btn-primary ${props.className}`}
        onClick={props.onClick}>{props.children}
    </button>

export const CancelButton: FunctionComponent<Props> = (props) => (
    <button
        disabled={props.disabled}
        className={`btn btn-danger ${props.className}`}
        onClick={props.onClick}>{props.children}</button>
);

export const ConfirmButton: FunctionComponent<Props> = (props) => (
    <button
        disabled={props.disabled}
        className={`btn btn-success ${props.className}`}
        onClick={props.onClick}>{props.children}</button>
);

export const DeleteButton: FunctionComponent<Props> = (props) => (
    <button
        disabled={props.disabled}
        className={`btn btn-danger ${props.className}`}
        onClick={props.onClick}>{props.children}</button>
);