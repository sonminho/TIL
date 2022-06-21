function Person(name, yearCount) {
    this.name = name;
    this.age = 0;
    var increAge = () => {
        this.age++;
    }
    for(var i=1; i <= yearCount; i++) { 
        increAge();
    }
}

var p1 = new Person('홍길동', 20);
console.log(p1);