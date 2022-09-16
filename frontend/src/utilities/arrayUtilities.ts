export const jsonArrayEqual = (a: Array<any>, b: Array<any>): boolean => {

    if (a.length !== b.length) return false;

    let allObjectsEqual = true;
    for (let i = 0; i < a.length; i++) {
        if (JSON.stringify(a[i]) !== JSON.stringify(b[i])) {
            allObjectsEqual = false;
            break;
        }
    }
    return allObjectsEqual;
}