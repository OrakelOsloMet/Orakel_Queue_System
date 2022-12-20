type Props = {
    defaultColumns: Array<string>,
    loggedInColumns: Array<string>,
    isAuthenticated: boolean
}

const tableHead = (props: Props) => {

    let columns: Array<JSX.Element> = [];
    props.defaultColumns.forEach(column => {
        columns.push(<th key={`${column} Header`} scope="col">{column}</th>);
    });

    if (props.isAuthenticated) {
        props.loggedInColumns.forEach(column => {
            columns.push(<th key={`${column} Header`} scope="col">{column}</th>);
        });
    }

    return (
        <thead key={"tableHead"} className="table-dark">
        <tr>
            {columns}
        </tr>
        </thead>
    );
};

export default tableHead;