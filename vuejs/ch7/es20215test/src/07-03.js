function addContact(name, mobile, home="없음") {
    var str = `name=${name}, mobile=${mobile}, home=${home}`;
    console.log(str);
}

addContact('쌈디', '01012341234');
addContact('그레이', '01012341234', '한남동');