import {useRef, useEffect} from "react";

const usePreviousState = (state: any) => {
    const ref = useRef();

    useEffect(() => {
        ref.current = state;
    });

    return ref.current;
}

export default usePreviousState;