function check() {
    try {
        //var ok = false;
        console.log("Okay");
        var ok1 = valid("title", 50);
        var ok2 = valid("body", 5000);
        var ok3 = valid("author", 50);
        return ok1 && ok2 && ok3;
    } catch (e) {
        return false;
    }
}

function valid(name, highestLength) {
    var temp = $("#" + name).val();
    console.log(name + " " + temp + " " + temp.length);
    resName = "#res-" + name;
    $(resName).removeClass("valid");
    $(resName).removeClass("invalid");
    var ok = true;
    //name[0] += 32;
    if (temp.length <= 0) {
        $(resName).addClass("invalid");
        $(resName).text(name + " cannot be empty");
        ok = false;
    } else if (temp.length > highestLength) {
        $(resName).addClass("invalid");
        $(resName).text(name + " cannot be more than " + highestLength + " characters");
        ok = false;
    } else {
        $(resName).addClass("valid");
        $(resName).text("Looks okay");
    }
    return ok;


}