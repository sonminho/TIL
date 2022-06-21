let msg = "GLOBAL";

function outer(e) {
    let msg = "OUTER";
    console.log(msg);
    if(true) {
        let msg = "BLOCK";
        console.log(msg);
    }
}