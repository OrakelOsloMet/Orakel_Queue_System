export const updateObject = (oldObject: any, updatedProperties: any) => {
    return {
        ...oldObject,
        ...updatedProperties
    };
};

/**
 * Converts all string-values in an object which can be converted to boolean or number types to their respective primitive
 * datatype.
 *
 * At the time of writing, use cases for this function is when posting an object to the API, which expects primitives
 * rather than string values.
 *
 * @param originalObject The object which should have its string values converted to primitives.
 * @return A clone of the original object with its string values converted to primitives.
 */
export const convertObjectStringsToPrimitives = (originalObject: any) => {
    const convertedObject = {...originalObject}

    for (let [key, value] of Object.entries(convertedObject)) {
        if (typeof value == "string") {

            if (value === "true" || value === "false") {
                convertedObject[key] = (value == "true");
            }

            if (!isNaN(Number(value))) {
                convertedObject[key] = parseFloat(value);
            }
        }
    }

    return convertedObject;
}

export const objectConditionalByEnvironment = (devObject: any, productionObject: any) => {
    return process.env.NODE_ENV === "production" ? productionObject : devObject;
};