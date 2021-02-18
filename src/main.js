const fs = require('fs');
const parse = require('csv-parse');

function generateJsonOutput(pathInput, options, callback) {
    let csvData = [];

    fs.createReadStream(pathInput)
        .pipe(parse(options))
        .on('data', function (csvrow) {
            csvData.push(csvrow);
        })
        .on('error', function (error) {
            if (error) throw error;
        })
        .on('end', function () {
            callback(sumJsonCovid(csvtoJSON(csvData)));           
        });

}

function csvtoJSON(csv) {

    let result = [];
    let headers = csv[0];
    csv.shift();

    csv.forEach(line => {
        let j = 0, currentLine = {};
        line.forEach(element => {
            currentLine[headers[j]] = element;
            j++;
        });
        result.push(currentLine);
    });

    return result;
}

function sumJsonCovid(json){

    var result = [];

    json.map((item) => {
        return new Object({
            month: String(item.date).substring(0, 7),
            cases: parseInt(item.cases),
            deaths: parseInt(item.deaths)
        });
    })
    .reduce(function (res, value) {

        if (!res[value.month]) {
            res[value.month] = { month: value.month, cases: 0, deaths: 0 };
            result.push(res[value.month])
        }
        res[value.month].cases += value.cases;
        res[value.month].deaths += value.deaths;
        return res;
    }, {});

    return result;
}

module.exports = { generateJsonOutput }