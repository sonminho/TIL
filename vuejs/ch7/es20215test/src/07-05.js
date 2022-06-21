// 구조분해할당(desctructuring assignment)
let arr = [1,2,3,4];
var [a1,a2,a3] = arr;
console.log(a1,a2,a3);

let p1 = {name:"홍길동", age:20};
let {name: n, age: a} = p1;
console.log(n, a);
